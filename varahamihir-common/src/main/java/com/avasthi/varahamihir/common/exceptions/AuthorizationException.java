/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.exceptions;

import org.springframework.http.HttpStatus;

/**
 * The type Authorization exception.
 *
 * @author nikhilvs
 */
public class AuthorizationException extends VarahamihirBaseException {

  public AuthorizationException() {
    super(HttpStatus.UNAUTHORIZED);
  }

  public AuthorizationException(String reason) {
    super(HttpStatus.UNAUTHORIZED, reason);
  }

  public AuthorizationException(String reason, Throwable cause) {
    super(HttpStatus.UNAUTHORIZED, reason, cause);
  }
}
