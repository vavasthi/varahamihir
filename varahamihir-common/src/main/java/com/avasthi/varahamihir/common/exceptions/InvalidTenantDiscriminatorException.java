package com.avasthi.varahamihir.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidTenantDiscriminatorException extends VarahamihirBaseException {

    public InvalidTenantDiscriminatorException() {
        super(HttpStatus.NOT_FOUND);
    }

    public InvalidTenantDiscriminatorException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public InvalidTenantDiscriminatorException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}
