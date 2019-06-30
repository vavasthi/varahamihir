package com.avasthi.varahamihir.common.pojos;

import com.avasthi.varahamihir.common.annotations.VarahamihirNonNullString;
import org.joda.time.DateTime;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

/**
 * Created by vinay on 1/28/16.
 */
@Document
public class ProductUnit extends Base {
  public ProductUnit() {
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }



  @VarahamihirNonNullString(min = 1, max = 10, nullAllowed = false)
  private String acronym;
  private String description;
}
