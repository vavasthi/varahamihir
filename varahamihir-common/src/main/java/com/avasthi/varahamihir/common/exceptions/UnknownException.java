/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@Log4j2
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnknownException extends VarahamihirBaseException {

  public UnknownException() {
    super(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public UnknownException(String reason) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
  }

  public UnknownException(String reason, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, reason, cause);
  }
}
