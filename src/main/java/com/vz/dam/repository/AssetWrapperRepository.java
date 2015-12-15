package com.vz.dam.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vz.dam.model.DAMAssetsDetails;

@Repository
public interface AssetWrapperRepository
        extends MongoRepository<DAMAssetsDetails, String> {
    
    DAMAssetsDetails findByMasterAssetName(String assetName);
    
}
