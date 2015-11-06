package com.vz.dam.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "damassetwrapper")
public class DAMAssetWrapper {
  
  @Id String id;

  @CreatedBy private String createdBy;
  @Temporal(TemporalType.TIMESTAMP) @DateTimeFormat(style = "M-") @CreatedDate private Date createdDate;
  
  private List<DAMAssetDetails> damAssetDetails;
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public List<DAMAssetDetails> getDamAssetDetails() {
    return damAssetDetails;
  }

  public void setDamAssetDetails(List<DAMAssetDetails> damAssetDetails) {
    this.damAssetDetails = damAssetDetails;
  }

}
