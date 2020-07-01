package com.avasthi.varahamihir.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyExistsException extends VarahamihirBaseException {

    public EntityAlreadyExistsException() {
        super(HttpStatus.CONFLICT);
    }

    public EntityAlreadyExistsException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }

    public EntityAlreadyExistsException(String reason, Throwable cause) {
        super(HttpStatus.CONFLICT, reason, cause);
    }
}
