package com.avasthi.varahamihir.common.filters;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class VarahamihirGatewayRequestPostFilter extends AbstractGatewayFilterFactory<VarahamihirGatewayRequestPostFilter.Config> {

  public VarahamihirGatewayRequestPostFilter() {
    super(Config.class);
  }
  @Override
  public GatewayFilter apply(Config config) {
    log.info("inside SCGWPostFilter.apply method...");

    return(exchange, chain)->{
      return chain.filter(exchange).then(Mono.fromRunnable(()->{
        ServerHttpResponse response = exchange.getResponse();
        exchange.getRequest().getHeaders().forEach((k,v)->{
          log.info(String.format("Request headers : %s: %s", k, v));
        });
        HttpHeaders headers = response.getHeaders();
        headers.forEach((k,v)->{
          log.info(String.format("headers : %s: %s", k, v));
          System.out.println(k + " : " + v);
        });
      }));
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
