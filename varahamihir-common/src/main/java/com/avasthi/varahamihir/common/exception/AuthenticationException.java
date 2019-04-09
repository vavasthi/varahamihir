/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.exception;

/**
 * The type Authentication exception.
 *
 * @author nikhilvs
 */
public class AuthenticationException extends SanjnanBaseException {

  /**
   * The Code.
   */
  Integer code;

  /**
   * Instantiates a new Authentication exception.
   *
   * @param message the message
   */
  public AuthenticationException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Authentication exception.
   *
   * @param message the message
   * @param code    the code
   */
  public AuthenticationException(String message, Integer code) {
    super(message);
    this.code = code;
  }

  /**
   * Gets code.
   *
   * @return the code
   */
  public Integer getCode() {
    return code;
  }


}
