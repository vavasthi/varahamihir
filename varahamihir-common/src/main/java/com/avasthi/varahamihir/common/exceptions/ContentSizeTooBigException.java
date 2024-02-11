package com.avasthi.varahamihir.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by vavasthi on 27/1/16.
 */
@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
public class ContentSizeTooBigException extends VarahamihirBaseException {

    public ContentSizeTooBigException(String error, String reason) {
        super(HttpStatus.FAILED_DEPENDENCY, error, reason);
    }

    public ContentSizeTooBigException(String error, String reason, Throwable cause) {
        super(HttpStatus.FAILED_DEPENDENCY, error, reason, cause);
    }
}
