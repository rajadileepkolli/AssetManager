package com.vz.dam.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vz.dam.domain.DAMAssetWrapper;

public interface AssetWrapperRepository extends MongoRepository<DAMAssetWrapper, String>{

}
