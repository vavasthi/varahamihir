package com.avasthi.varahamihir.common.pojos;

import org.joda.time.DateTime;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

/**
 * Created by vinay on 1/28/16.
 */
@Document
public class TaxCategory extends Base {
  public TaxCategory() {
    super(UUID.randomUUID().toString(), DateTime.now(), DateTime.now(),null, null, "");
    setCreatedBy(getId());
    setCreatedBy(getId());
  }

  public String getAcronym() {
    return acronym;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public float getRate() {
    return rate;
  }

  public void setRate(float rate) {
    this.rate = rate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public float getCgstRate() {
    return cgstRate;
  }

  public void setCgstRate(float cgstRate) {
    this.cgstRate = cgstRate;
  }

  public float getSgstRate() {
    return sgstRate;
  }

  public void setSgstRate(float sgstRate) {
    this.sgstRate = sgstRate;
  }

  public float getIgstRate() {
    return igstRate;
  }

  public void setIgstRate(float igstRate) {
    this.igstRate = igstRate;
  }

  private String acronym;
  private float rate;
  private String description;
  private float cgstRate;
  private float sgstRate;
  private float igstRate;
}
