package com.avasthi.varahamihir.common.pojos;

import org.joda.time.DateTime;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

/**
 * Created by vinay on 1/28/16.
 */
@Document
public class ProductLocation extends Base {
  public ProductLocation() {
    super(UUID.randomUUID().toString(), DateTime.now(), DateTime.now(),null, null, "");
    setCreatedBy(getId());
    setCreatedBy(getId());
  }

  public ProductLocation(String id,
                         String createdBy,
                         String name,
                         Geometry geometry,
                         String address,
                         String state,
                         String postCode) {

    super(id, DateTime.now(), DateTime.now(), createdBy, createdBy, name);
    this.geometry = geometry;
    this.address = address;
    this.state = state;
    this.postCode = postCode;
  }

  public Geometry getGeometry() {
    return geometry;
  }

  public void setGeometry(Geometry geometry) {
    this.geometry = geometry;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  private Geometry geometry;
  private String address;
  private String state;
  private String postCode;
}
