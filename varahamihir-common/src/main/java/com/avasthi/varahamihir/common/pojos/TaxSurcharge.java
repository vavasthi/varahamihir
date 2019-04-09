package com.avasthi.varahamihir.common.pojos;

import org.joda.time.DateTime;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

/**
 * Created by vinay on 1/28/16.
 */
@Document
public class TaxSurcharge extends Base {
  public TaxSurcharge() {
    super(UUID.randomUUID().toString(), DateTime.now(), DateTime.now(),null, null, "");
    setCreatedBy(getId());
    setCreatedBy(getId());
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


  private float rate;
  private String description;
}
