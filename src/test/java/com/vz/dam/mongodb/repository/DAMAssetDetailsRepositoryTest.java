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
import com.vz.dam.domain.DAMAssetDetails;
import com.vz.dam.domain.DAMAssetWrapper;
import com.vz.dam.domain.FileType;
import com.vz.dam.domain.MediainfoType;
import com.vz.dam.domain.TrackType;

public class DAMAssetDetailsRepositoryTest extends AssetManagerConfigurationTest{
  
  @Test
  public final void testSave() throws DatatypeConfigurationException{
    assetWrapperRepository.deleteAll();
    mediainfoTypeRepository.deleteAll();
    DAMAssetWrapper assetWrapper = new DAMAssetWrapper();
    DAMAssetWrapper insertedAssetWrapper =assetWrapperRepository.save(assetWrapper);
    DAMAssetDetails assetDetails = new DAMAssetDetails();
    assetDetails.setAssetId(insertedAssetWrapper.getId()+"1");
    assetDetails.setAssetName("Super.mov");
    assetDetails.setAssetType("Original");
    assetDetails.setStoragePath("/vol/customer2/123/ingestOut/");
    assetDetails.setCheckSum("123sdfwe23sdf");
    assetDetails.setTranscoderTool("Rhozet");
    assetDetails.setDateCreated(insertedAssetWrapper.getCreatedDate());
    
    MediainfoType mediainfoType = new MediainfoType();
    mediainfoType.setId(insertedAssetWrapper.getId()+"1");
    FileType fileType = new FileType();
    List<TrackType> track = new ArrayList<TrackType>();
    TrackType trackType = new TrackType();
    trackType.setModel("Iphone");
    fileType.setTrack(track);
    mediainfoType.setVersion("1");
    mediainfoType.setFile(fileType);
    assetDetails.setMediaInfo(mediainfoType);
    assetWrapper.setDamAssetDetails(Arrays.asList(assetDetails));
    DAMAssetWrapper response = assetWrapperRepository.save(assetWrapper);
    System.out.println(response);
/*    assertNotNull(response.getAssetId());
    DAMAssetDetails findResponse = dAMAssetDetailsRepository.findOne(response.getAssetId());
    assertEquals(response.getAssetId(), findResponse.getAssetId());*/
  }

/*  @Test
  public final void testSaveIterableOfS() throws DatatypeConfigurationException {
    DAMAssetDetails assetDetails = new DAMAssetDetails();
    assetDetails.setAssetName("Super.mov");
    assetDetails.setAssetType("Original");
    assetDetails.setStoragePath("/vol/customer2/123/ingestOut/");
    assetDetails.setCheckSum("123sdfwe23sdf");
    assetDetails.setTranscoderTool("Rhozet");
    assetDetails.setMediaInfo(null);
    dAMAssetDetailsRepository.deleteAll();
    DAMAssetDetails response = dAMAssetDetailsRepository.save(assetDetails);
    assertNotNull(response.getAssetId());
  }*/

  @Test
  public final void testFindAll() {
    Page<DAMAssetWrapper> assets = assetWrapperRepository.findAll(pageable);
    assertEquals(1, assets.getTotalElements());
    assertNotNull(assets.getContent());
  }

}
