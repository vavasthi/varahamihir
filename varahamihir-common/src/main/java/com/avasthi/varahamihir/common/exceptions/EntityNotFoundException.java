package com.avasthi.varahamihir.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends VarahamihirBaseException {

    public EntityNotFoundException(String error, String reason) {
        super(HttpStatus.NOT_FOUND, error, reason);
    }

    public EntityNotFoundException(String error, String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, error, reason, cause);
    }
}
