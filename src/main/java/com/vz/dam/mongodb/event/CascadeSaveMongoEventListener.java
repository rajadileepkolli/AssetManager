package com.vz.dam.mongodb.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.util.ReflectionUtils;

/**
 * <p>
 * CascadeSaveMongoEventListener class.
 * </p>
 *
 * @author rajakolli
 * @version 1 : 0
 */
public class CascadeSaveMongoEventListener extends AbstractMongoEventListener<Object> {

  @Autowired private MongoOperations mongoOperations;

  /** {@inheritDoc} */
  @Override
  public void onBeforeConvert(final Object source) {
    ReflectionUtils.doWithFields(source.getClass(), new CascadeCallback(source, mongoOperations));
  }
}