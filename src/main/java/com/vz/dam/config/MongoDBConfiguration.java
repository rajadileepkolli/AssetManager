package com.vz.dam.config;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.vz.dam.mongodb.audit.MongoAuditorProvider;
import com.vz.dam.mongodb.convert.CustomDefaultDbRefResolver;
import com.vz.dam.mongodb.convert.CustomMappingMongoConverter;
import com.vz.dam.mongodb.event.CascadeSaveMongoEventListener;
import com.vz.dam.util.Constants;

@Configuration
public class MongoDBConfiguration extends AbstractMongoConfiguration {

  private static final String DATABASE = "digitalbridge";
  @Autowired private MongoClient mongoClient;

  @Override
  protected String getDatabaseName() {
    return DATABASE;
  }

  @Override
  public Mongo mongo() throws Exception {
    return mongoClient;
  }
  
  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoDbFactory(), mongoConverter());
  }

  @Bean
  public MongoDbFactory mongoDbFactory() {
    return new SimpleMongoDbFactory(mongoClient, getDatabaseName());
  }

  @Bean
  public MongoConverter mongoConverter() {
    MongoMappingContext mappingContext = new MongoMappingContext();
    CustomDefaultDbRefResolver dbRefResolver = new CustomDefaultDbRefResolver(mongoDbFactory());
    CustomMappingMongoConverter mongoConverter = new CustomMappingMongoConverter(dbRefResolver, mappingContext);
    mongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    mongoConverter.setCustomConversions(customConversions());
    return mongoConverter;
  }
  
/*  @Override
  @Bean
  public CustomConversions customConversions() {
    List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
    converters.addAll(ObjectConverters.getConvertersToRegister());
    return new CustomConversions(converters);
  }*/

  @Profile("demo")
  @Bean(name = "mongoClient")
  public MongoClient demoMongoClient() {
    List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
    credentialsList.add(MongoCredential.createCredential("digitalbridgeAdmin", DATABASE, "fD4Krim9".toCharArray()));
    ServerAddress primary = new ServerAddress("152.190.138.70", Constants.PRIMARYPORT);
    ServerAddress secondary = new ServerAddress("152.190.138.62", Constants.PRIMARYPORT);
    ServerAddress teritory = new ServerAddress("152.190.138.63", Constants.PRIMARYPORT);
    List<ServerAddress> seeds = Arrays.asList(primary, secondary, teritory);
    MongoClientOptions mongoClientOptions = MongoClientOptions.builder().requiredReplicaSetName("demo1").build();
    return new MongoClient(seeds, credentialsList, mongoClientOptions);
  }

  @Profile("local")
  @Bean(name = "mongoClient")
  public MongoClient localMongoClient() {
    List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
    credentialsList.add(MongoCredential.createCredential("digitalbridgeAdmin", DATABASE, "password".toCharArray()));
    ServerAddress primary = new ServerAddress(new InetSocketAddress(Constants.LOCALHOST, Constants.PRIMARYPORT));
    ServerAddress secondary = new ServerAddress(new InetSocketAddress(Constants.LOCALHOST, Constants.SECONDARYPORT));
    ServerAddress teritory = new ServerAddress(new InetSocketAddress(Constants.LOCALHOST, Constants.TERITORYPORT));
    ServerAddress arbiterOnly = new ServerAddress(new InetSocketAddress(Constants.LOCALHOST, Constants.ARBITERPORT));
    List<ServerAddress> seeds = Arrays.asList(primary, secondary, teritory, arbiterOnly);
    MongoClientOptions mongoClientOptions = MongoClientOptions.builder().requiredReplicaSetName("digitalBridgeReplica")
        .build();
    return new MongoClient(seeds, credentialsList, mongoClientOptions);
  }

  @Bean
  public MongoExceptionTranslator exceptionTranslator() {
    return new MongoExceptionTranslator();
  }
  
  @Bean
  public CascadeSaveMongoEventListener cascadingMongoEventListener() {
    return new CascadeSaveMongoEventListener();
  }
  
  @Bean
  public AuditorAware<String> auditorProvider() {
    return new MongoAuditorProvider<String>();
  }
  
}
