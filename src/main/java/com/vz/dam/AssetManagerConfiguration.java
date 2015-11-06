package com.vz.dam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "com.mongodb.repository")
@EnableMongoAuditing(modifyOnCreate = false)
public class AssetManagerConfiguration extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(AssetManagerConfiguration.class, args);
  }

  /** {@inheritDoc} */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(AssetManagerConfiguration.class);
  }

}
