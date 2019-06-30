package com.avasthi.varahamihir.common.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.avasthi.varahamihir.common.serializers.VarahamihirDateTimeDeserializer;
import com.avasthi.varahamihir.common.serializers.VarahamihirDateTimeSerializer;
import org.joda.time.DateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by vinay on 2/3/16.
 */
public class VarahamihirTokenResponse implements Serializable {

  @JsonProperty
  private String authToken;
  private String refreshToken;
  private ComputeRegion computeRegion;
  @JsonSerialize(using = VarahamihirDateTimeSerializer.class)
  @JsonDeserialize(using = VarahamihirDateTimeDeserializer.class)
  private DateTime expiry;
  private Collection<VarahamihirRole> varahamihirRoles;

  public VarahamihirTokenResponse() {
  }

  public VarahamihirTokenResponse(String authToken,
                                  String refreshToken,
                                  ComputeRegion computeRegion,
                                  DateTime expiry,
                                  Collection<VarahamihirRole> varahamihirRoles) throws DatatypeConfigurationException {
    this.authToken = authToken;
    this.refreshToken = refreshToken;
    this.computeRegion = computeRegion;
    this.varahamihirRoles = varahamihirRoles;
    this.expiry = expiry;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public DateTime getExpiry() {
    return expiry;
  }

  public void setExpiry(DateTime expiry) {
    this.expiry = expiry;
  }

  public Collection<VarahamihirRole> getVarahamihirRoles() {
    return varahamihirRoles;
  }

  public void setVarahamihirRoles(Collection<VarahamihirRole> varahamihirRoles) {
    this.varahamihirRoles = varahamihirRoles;
  }

  public ComputeRegion getComputeRegion() {
    return computeRegion;
  }

  public void setComputeRegion(ComputeRegion computeRegion) {
    this.computeRegion = computeRegion;
  }
}