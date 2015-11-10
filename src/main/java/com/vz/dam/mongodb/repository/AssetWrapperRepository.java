package com.vz.dam.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vz.dam.domain.DAMAssetsDetails;

public interface AssetWrapperRepository extends MongoRepository<DAMAssetsDetails, String>{

}
