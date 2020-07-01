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
 * The type Bad request exception.
 *
 * @author nikhilvs
 */
/*
* Add extra attributes as per request
*/
public final class BadRequestException extends VarahamihirBaseException {


  public BadRequestException() {
    super(HttpStatus.BAD_REQUEST);
  }

  public BadRequestException(String reason) {
    super(HttpStatus.BAD_REQUEST, reason);
  }

  public BadRequestException(String reason, Throwable cause) {
    super(HttpStatus.BAD_REQUEST, reason, cause);
  }
}

