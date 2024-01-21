package com.avasthi.varahamihir.identityserver.advice;

import com.avasthi.varahamihir.common.exceptions.EntityAlreadyExistsException;
import com.avasthi.varahamihir.common.exceptions.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IdentityExceptionHandler {

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleEntityAlreadyExistsException(EntityAlreadyExistsException eaee,
                                                                HttpServletRequest request) {
        return ExceptionResponse.builder()
                .error(eaee.getError())
                .message(eaee.getMessage())
                .timestamp(eaee.getTimestamp())
                .path(request.getRequestURI())
                .requestId(request.getRequestId())
                .status(HttpStatus.CONFLICT.value())
                .build();
    }
}
