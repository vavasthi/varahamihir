/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.pojos;

import java.io.Serializable;

/**
 * Created by vinay on 2/11/16.
 */
public class VarahamihirUsernameAndTokenResponse implements Serializable {
  public VarahamihirUsernameAndTokenResponse(String username, VarahamihirTokenResponse response) {
    this.username = username;
    this.response = response;
  }

  public VarahamihirUsernameAndTokenResponse() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public VarahamihirTokenResponse getResponse() {
    return response;
  }

  public void setResponse(VarahamihirTokenResponse response) {
    this.response = response;
  }

  private String username;
  private VarahamihirTokenResponse response;

}
