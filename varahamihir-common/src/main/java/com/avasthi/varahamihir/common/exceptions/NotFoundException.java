package com.avasthi.varahamihir.common.exceptions;

import com.avasthi.varahamihir.common.utils.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by vavasthi on 28/01/16.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends VarahamihirBaseException {


    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

    public NotFoundException(String reason, Throwable cause) {
        super(HttpStatus.NOT_FOUND, reason, cause);
    }
}