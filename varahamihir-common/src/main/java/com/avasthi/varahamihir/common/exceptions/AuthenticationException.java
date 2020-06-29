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
 * The type Authentication exception.
 *
 * @author nikhilvs
 */
public class AuthenticationException extends VarahamihirBaseException {

  public AuthenticationException() {
    super(HttpStatus.UNAUTHORIZED);
  }

  public AuthenticationException(String reason) {
    super(HttpStatus.UNAUTHORIZED, reason);
  }

  public AuthenticationException(String reason, Throwable cause) {
    super(HttpStatus.UNAUTHORIZED, reason, cause);
  }
}
