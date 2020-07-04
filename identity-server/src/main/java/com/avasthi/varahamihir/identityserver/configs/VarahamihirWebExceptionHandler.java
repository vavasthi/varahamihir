package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.common.exceptions.ExceptionResponse;
import com.avasthi.varahamihir.common.exceptions.VarahamihirBaseException;
import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class VarahamihirWebExceptionHandler implements WebExceptionHandler  {
  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {

    if (throwable instanceof VarahamihirBaseException) {

      Gson gson = new Gson();
      VarahamihirBaseException vbe = (VarahamihirBaseException) throwable;
      ExceptionResponse er
              = ExceptionResponse.builder()
              .messge(vbe.getMessage())
              .path(exchange.getRequest().getPath().toString())
              .status(vbe.getStatus().value())
              .error(vbe.getStatus().toString())
              .timestamp(vbe.getTimestamp())
              .reason(vbe.getReason())
              .requestId(exchange.getRequest().getId())
              .build();
      String json = gson.toJson(er);
      exchange.getResponse().setStatusCode(vbe.getStatus());
      exchange.getResponse().getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
      return exchange.getResponse()
              .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(json.getBytes())));
    }
    return Mono.error(throwable);
  };
}

