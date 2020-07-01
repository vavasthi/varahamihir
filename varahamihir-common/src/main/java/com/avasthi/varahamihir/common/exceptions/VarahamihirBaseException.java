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
public class VarahamihirBaseException extends ResponseStatusException {

  private Date timestamp = new Date();

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public VarahamihirBaseException(HttpStatus status) {
    super(status);
  }

  public VarahamihirBaseException(HttpStatus status, String reason) {
    super(status, reason);
  }

  public VarahamihirBaseException(HttpStatus status, String reason, Throwable cause) {
    super(status, reason, cause);
  }
}
