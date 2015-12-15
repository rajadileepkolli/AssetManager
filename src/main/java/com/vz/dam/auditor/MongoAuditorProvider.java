package com.vz.dam.auditor;

import org.springframework.data.domain.AuditorAware;

public class MongoAuditorProvider<T> implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        return "Admin";
    }

}
