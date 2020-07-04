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
@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
public class MicroservicesPeerUnavailableException extends VarahamihirBaseException {

  public MicroservicesPeerUnavailableException() {
    super(HttpStatus.FAILED_DEPENDENCY);
  }

  public MicroservicesPeerUnavailableException(String reason) {
    super(HttpStatus.FAILED_DEPENDENCY, reason);
  }

  public MicroservicesPeerUnavailableException(String reason, Throwable cause) {
    super(HttpStatus.FAILED_DEPENDENCY, reason, cause);
  }
}
