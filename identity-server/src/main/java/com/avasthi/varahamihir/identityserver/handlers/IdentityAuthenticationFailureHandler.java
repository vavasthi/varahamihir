package com.avasthi.varahamihir.identityserver.handlers;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class IdentityAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ExceptionResponse er = ExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(exception.getMessage())
                .requestId(request.getRequestId())
                .timestamp(new Date())
                .path(request.getRequestURI())
                .message(exception.toString())
                .stackTrace(exception.getCause() == null
                        ? new ArrayList<>()
                        : Arrays.stream(exception.getCause().getStackTrace()).map(st -> st.toString()).collect(Collectors.toList()))
                .build();
        response.getOutputStream()
                .println(objectMapper.writeValueAsString(er));
    }
}
