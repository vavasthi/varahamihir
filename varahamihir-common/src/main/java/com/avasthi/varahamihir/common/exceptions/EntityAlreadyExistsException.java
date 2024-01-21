package com.avasthi.varahamihir.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyExistsException extends VarahamihirBaseException {

    public EntityAlreadyExistsException(String error, String reason) {
        super(HttpStatus.CONFLICT, error, reason);
    }

    public EntityAlreadyExistsException(String error, String reason, Throwable cause) {
        super(HttpStatus.CONFLICT, error, reason, cause);
    }
}
