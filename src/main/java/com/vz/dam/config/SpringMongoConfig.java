package com.vz.dam.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.vz.dam.auditor.MongoAuditorProvider;

/**
 * <p>SpringMongoConfig class.</p>
 *
 * @author rajakolli
 * @version 1:0
 */
@Configuration
@EnableMongoRepositories(basePackages ="com.vz.dam.repository")
@EnableMongoAuditing(modifyOnCreate = false)
public class SpringMongoConfig extends AbstractMongoConfiguration {
    
    private static final String DATABASE = "digitalbridge";
    private static final int PRIMARYPORT = 27017;

    /** {@inheritDoc} */
    @Override
    public String getDatabaseName() {
        return DATABASE;
    }

    /** {@inheritDoc} */
    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return mongoClient();
    }
    
    /** {@inheritDoc} */
    @Override
    protected String getMappingBasePackage() {
        return "com.vz.dam.model";
    }

    /**
     * <p>localMongoClient.</p>
     *
     * @return a {@link com.mongodb.MongoClient} object.
     */
    @Bean
    public MongoClient mongoClient() {
        /*List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
        credentialsList.add(MongoCredential.createCredential("digitalbridgeAdmin",
                DATABASE, "password".toCharArray()));
        ServerAddress primary = new ServerAddress(
                new InetSocketAddress("localhost", 27017));
        ServerAddress secondary = new ServerAddress(
                new InetSocketAddress("localhost", 27018));
        ServerAddress teritory = new ServerAddress(
                new InetSocketAddress("localhost", 27019));
        List<ServerAddress> seeds = Arrays.asList(primary, secondary, teritory);
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
                .requiredReplicaSetName("digitalBridgeReplica").build();
        return new MongoClient(seeds, credentialsList, mongoClientOptions);*/
        
        List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
        credentialsList.add(MongoCredential.createCredential("digitalbridgeAdmin", DATABASE, "fD4Krim9".toCharArray()));
        ServerAddress primary = new ServerAddress("152.190.138.70", PRIMARYPORT);
        ServerAddress secondary = new ServerAddress("152.190.138.62", PRIMARYPORT);
        ServerAddress teritory = new ServerAddress("152.190.138.63", PRIMARYPORT);
        List<ServerAddress> seeds = Arrays.asList(primary, secondary, teritory);
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().requiredReplicaSetName("demo1").build();
        return new MongoClient(seeds, credentialsList, mongoClientOptions);
        
    }

    /**
     * <p>mongoTemplate.</p>
     *
     * @return a {@link org.springframework.data.mongodb.core.MongoTemplate} object.
     * @throws java.lang.Exception if any.
     */
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(),
                mongoConverter());
        mongoTemplate.setWriteConcern(WriteConcern.JOURNALED);
        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        return mongoTemplate;
    }

    /**
     * <p>mongoConverter.</p>
     *
     * @return a {@link org.springframework.data.mongodb.core.convert.MongoConverter} object.
     * @throws java.lang.Exception if any.
     */
    @Bean
    public MongoConverter mongoConverter() throws Exception {
        MappingMongoConverter converter = super.mappingMongoConverter();
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
    
    /**
     * <p>mongoDbFactory.</p>
     *
     * @return a {@link org.springframework.data.mongodb.MongoDbFactory} object.
     */
    @Bean
    public MongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
    }
    
    @Bean
    public MongoExceptionTranslator exceptionTranslator() {
        return new MongoExceptionTranslator();
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new MongoAuditorProvider<String>();
    }
}
