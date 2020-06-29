/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends VarahamihirBaseException {

  public UnauthorizedException() {
    super(HttpStatus.UNAUTHORIZED);
  }

  public UnauthorizedException(String reason) {
    super(HttpStatus.UNAUTHORIZED, reason);
  }

  public UnauthorizedException(String reason, Throwable cause) {
    super(HttpStatus.UNAUTHORIZED, reason, cause);
  }
}
