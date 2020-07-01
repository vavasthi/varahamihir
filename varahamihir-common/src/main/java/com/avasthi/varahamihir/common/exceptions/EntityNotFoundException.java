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
@ResponseStatus()
public class EntityNotFoundException extends VarahamihirBaseException {

  public EntityNotFoundException() {
    super(HttpStatus.NOT_FOUND);
  }

  public EntityNotFoundException(String reason) {
    super(HttpStatus.NOT_FOUND, reason);
  }

  public EntityNotFoundException(String reason, Throwable cause) {
    super(HttpStatus.NOT_FOUND, reason, cause);
  }
}
