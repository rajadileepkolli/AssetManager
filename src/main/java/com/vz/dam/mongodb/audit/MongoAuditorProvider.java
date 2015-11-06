package com.vz.dam.mongodb.audit;

import org.springframework.data.domain.AuditorAware;

public class MongoAuditorProvider<T> implements AuditorAware<String> {

  @Override
  public String getCurrentAuditor() {
    return "Admin";
  }

}
