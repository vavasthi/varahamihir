package com.avasthi.varahamihir.identityserver.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Map;

@Embeddable
@Getter
@Setter
public class VarahamihirClientDetailsAdditionalInfo {

  public VarahamihirClientDetailsAdditionalInfo() {
  }
  public VarahamihirClientDetailsAdditionalInfo(String key, String value) {
    this.k = key;
    this.v = value;
  }
  public VarahamihirClientDetailsAdditionalInfo(Map.Entry<String, String> e) {
    this.k = e.getKey();
    this.v = e.getValue();
  }
  private String k;
  private String v;
}
