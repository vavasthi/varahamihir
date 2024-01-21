/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

/**
 * Created by vinay on 1/11/16.
 */
public class VarahamihirBaseException extends RuntimeException {

  
  private Date timestamp = new Date();
  private String error;
  private HttpStatus status;

  public String getError() {
    return error;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public VarahamihirBaseException(HttpStatus status,
                                  String error) {
    this.status = status;
    this.error = error;
  }

  public VarahamihirBaseException(HttpStatus status, String error, String reason) {
    super(reason);
    this.error = error;
    this.status = status;
  }

  public VarahamihirBaseException(HttpStatus status, String error, String reason, Throwable cause) {
    super(reason, cause);
    this.error = error;
    this.status = status;

  }


  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

}
