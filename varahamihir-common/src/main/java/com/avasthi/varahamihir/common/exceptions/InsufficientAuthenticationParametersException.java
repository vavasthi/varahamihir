package com.avasthi.varahamihir.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InsufficientAuthenticationParametersException extends VarahamihirBaseException {

    public InsufficientAuthenticationParametersException() {
        super(HttpStatus.UNAUTHORIZED);
    }

    public InsufficientAuthenticationParametersException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

    public InsufficientAuthenticationParametersException(String reason, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, reason, cause);
    }
}
