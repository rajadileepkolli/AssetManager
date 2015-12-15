package com.vz.dam.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vz.dam.model.DAMAssetsDetails;

public interface AssetWrapperRepository
        extends MongoRepository<DAMAssetsDetails, String> {
    
    DAMAssetsDetails findByMasterAssetName(String assetName);
    
}
