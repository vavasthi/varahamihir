package com.avasthi.varahamihir.identityserver.handlers;

import com.avasthi.varahamihir.common.errors.ExceptionMessage;
import com.avasthi.varahamihir.common.exceptions.ExceptionResponse;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Date;

public class IdentityAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        Gson gson = new Gson();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FAILED_DEPENDENCY.value());
        response.getWriter().write(gson.toJson(ExceptionResponse.builder()
                .error(String.format(ExceptionMessage.INVALID_PASSWORD.getReason(), authException.getMessage()))
                .message(String.format(ExceptionMessage.INVALID_PASSWORD.getError(), authException.getMessage()))
                .timestamp(new Date())
                .path(request.getRequestURI())
                .requestId(request.getRequestId())
                .status(HttpStatus.FAILED_DEPENDENCY.value())
                .build()));
    }
}
