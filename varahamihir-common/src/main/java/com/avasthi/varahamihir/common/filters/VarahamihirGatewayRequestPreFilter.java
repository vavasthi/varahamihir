package com.avasthi.varahamihir.common.filters;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class VarahamihirGatewayRequestPreFilter extends AbstractGatewayFilterFactory<VarahamihirGatewayRequestPreFilter.Config> {

  public VarahamihirGatewayRequestPreFilter() {
    super(Config.class);
  }
  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest().mutate().header("scgw-pre-header", Math.random()*10+"").build();
      ServerHttpResponse response = exchange.getResponse();
      request.getHeaders().forEach((k,v)->{
        log.info(String.format("Request headers : %s: %s", k, v));
      });
      HttpHeaders headers = response.getHeaders();
      headers.forEach((k,v)->{
        log.info(String.format("Response headers : %s: %s", k, v));
      });
      return chain.filter(exchange.mutate().request(request).build());
    };
  }

  public static class Config {
    private String name;

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
