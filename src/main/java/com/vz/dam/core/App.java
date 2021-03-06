package com.vz.dam.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.vz.dam.config.SpringMongoConfig;
import com.vz.dam.model.DAMAsset;
import com.vz.dam.model.DAMAssetsDetails;
import com.vz.dam.model.FileType;
import com.vz.dam.model.MediainfoType;
import com.vz.dam.model.TrackType;
import com.vz.dam.repository.AssetWrapperRepository;
import com.vz.dam.repository.MediaInfoRepository;

/**
 * <p>App class.</p>
 *
 * @author rajakolli
 * @version 1:0
 */
public class App {
    

	/**
	 * <p>main.</p>
	 *
	 * @param args an array of {@link java.lang.String} objects.
	 */
    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");
        AssetWrapperRepository assetsDetailsRepository = (AssetWrapperRepository) ctx
                .getBean("assetWrapperRepository");
        MediaInfoRepository mediaInfoRepository = (MediaInfoRepository) ctx.getBean("mediaInfoRepository");

        // drop collection example
        //mongoOperations.dropCollection(DAMAssetsDetails.class);

        // save Data
        saveData(assetsDetailsRepository,mediaInfoRepository);

        // find saved asset again
        DAMAssetsDetails output = assetsDetailsRepository.findByAssetDetailAssetName("SuperMan.mov");

        // query to search master Asset
        Query searchMasterAsset = new Query(Criteria.where("_id").is(output.getId()));

        DAMAsset damAsset = new DAMAsset();
        damAsset.setAssetId(output.getMasterAssetId() + "_2");
        damAsset.setAssetName("SuperMan.mp4");
        damAsset.setAssetType("CDN");
        damAsset.setStoragePath("/vol/customer2/123/ingestIn/");
        damAsset.setCheckSum("123sdfwe23sdf");
        damAsset.setTranscoderTool("Rhozet");

        // update and find the asset
        Update update = new Update().push("assetDetail", damAsset);
        DAMAssetsDetails updatedAsset = mongoOperations.findAndModify(searchMasterAsset,
                update, new FindAndModifyOptions().returnNew(true).upsert(false),
                DAMAssetsDetails.class);

        System.out.println("3. updatedAsset : " + updatedAsset);

        // List, it should be empty now.
        List<DAMAssetsDetails> listAssets = mongoOperations
                .findAll(DAMAssetsDetails.class);
        System.out.println("4. Number of assets = " + listAssets.size());

        //Dont do in Production
        ((AbstractApplicationContext) ctx).registerShutdownHook();
    }

    private static void saveData(AssetWrapperRepository assetsDetailsRepository, MediaInfoRepository mediaInfoRepository) {
        DAMAssetsDetails assetDetails = new DAMAssetsDetails();
	    DAMAssetsDetails dAMAssetsDetails = assetsDetailsRepository.save(assetDetails);
	    
	    dAMAssetsDetails.setMasterAssetId(dAMAssetsDetails.getId());
	    dAMAssetsDetails.setMasterAssetName("Master Asset 1");
	    
	    List<DAMAsset> listDAMAsset = new ArrayList<DAMAsset>();
	    DAMAsset damAsset = new DAMAsset();
	    damAsset.setAssetId(dAMAssetsDetails.getMasterAssetId() + "_1");
	    damAsset.setAssetName("SuperMan.mov");
	    damAsset.setAssetType("Original");
	    damAsset.setStoragePath("/vol/customer2/123/ingestOut/");
	    damAsset.setCheckSum("123sdfwe23sdf");
	    damAsset.setTranscoderTool("Rhozet");
	    damAsset.setDateCreated(dAMAssetsDetails.getDateCreated());

	    MediainfoType mediainfoType = new MediainfoType();
	    FileType fileType = new FileType();
	    List<TrackType> track = new ArrayList<TrackType>();
	    TrackType trackType = new TrackType();
	    trackType.setModel("Iphone");
	    trackType.setId(Arrays.asList("1"));
	    track.add(trackType);
	    fileType.setTrack(track);
	    mediainfoType.setVersion("1");
	    mediainfoType.setFile(fileType);
	    MediainfoType insertedMediaInfoType = mediaInfoRepository.save(mediainfoType);
	    damAsset.setMediaInfo(insertedMediaInfoType);
	    listDAMAsset.add(damAsset);

	    dAMAssetsDetails.setAssetDetail(listDAMAsset);
	    DAMAssetsDetails finalAssetDetails = assetsDetailsRepository.save(dAMAssetsDetails);
	    System.out.println(finalAssetDetails);
    }

}
