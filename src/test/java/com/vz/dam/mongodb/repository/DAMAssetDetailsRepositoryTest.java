package com.vz.dam.mongodb.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.Test;
import org.springframework.data.domain.Page;

import com.vz.dam.AssetManagerConfigurationTest;
import com.vz.dam.domain.DAMAsset;
import com.vz.dam.domain.DAMAssetsDetails;
import com.vz.dam.domain.FileType;
import com.vz.dam.domain.MediainfoType;
import com.vz.dam.domain.TrackType;

public class DAMAssetDetailsRepositoryTest extends AssetManagerConfigurationTest {

  @Test
  public final void testSave() throws DatatypeConfigurationException {
    assetsDetailsRepository.deleteAll();
    DAMAssetsDetails assetDetails = new DAMAssetsDetails();
    DAMAssetsDetails dAMAssetsDetails = assetsDetailsRepository.save(assetDetails);
    
    dAMAssetsDetails.setMasterAssetId(dAMAssetsDetails.getId());
    List<DAMAsset> listDAMAsset = new ArrayList<DAMAsset>();
    DAMAsset damAsset = new DAMAsset();
    damAsset.setAssetId(dAMAssetsDetails.getMasterAssetId() + "1");
    damAsset.setAssetName("Super.mov");
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
    damAsset.setMediaInfo(mediainfoType);
    listDAMAsset.add(damAsset);

    dAMAssetsDetails.setAssetDetail(listDAMAsset);
    DAMAssetsDetails finalAssetDetails = assetsDetailsRepository.save(dAMAssetsDetails);
    System.out.println(finalAssetDetails);
    assertNotNull(finalAssetDetails);

  }

  @Test
  public final void testFindAll() {
    Page<DAMAssetsDetails> assets = assetsDetailsRepository.findAll(pageable);
    assertEquals(1, assets.getTotalElements());
    assertNotNull(assets.getContent());
  }

}
