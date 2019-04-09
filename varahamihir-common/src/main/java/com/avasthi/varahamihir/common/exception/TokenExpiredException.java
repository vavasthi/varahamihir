/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.exception;

import com.avasthi.varahamihir.common.constants.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExpiredException extends SanjnanBaseException {

    /**
     * Instantiates a new Token expired exception.
     *
     * @param errorCode the error code
     * @param Message   the message
     */
    public TokenExpiredException(int errorCode, String Message) {

    super(errorCode, ErrorCodes.TOKEN_EXPIRED_ERROR_CODE + ": " + Message);
  }

}
