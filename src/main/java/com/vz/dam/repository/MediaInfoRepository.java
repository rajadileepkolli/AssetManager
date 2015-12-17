package com.vz.dam.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vz.dam.model.MediainfoType;


public interface MediaInfoRepository extends MongoRepository<MediainfoType, String> {

}
