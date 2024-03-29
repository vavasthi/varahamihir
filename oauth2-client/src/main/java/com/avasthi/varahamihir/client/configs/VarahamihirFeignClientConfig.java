package com.avasthi.varahamihir.client.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Request;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.concurrent.TimeUnit;

@Configuration
public class VarahamihirFeignClientConfig {
  @Value("${feign.connectTimeout:10000}")
  private int connectTimeout;

  @Value("${feign.readTimeOut:300000}")
  private int readTimeout;

  @Bean
  public Decoder feignDecoder() {
    return new ResponseEntityDecoder(new SpringDecoder(httpMessageConvertersObjectFactory()));
  }

  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public Request.Options options() {
    return new Request.Options(connectTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, true);
  }
  public ObjectMapper customObjectMapper(){
    ObjectMapper objectMapper = new ObjectMapper();
    //Customize as much as you want
    return objectMapper;
  }
  @Bean
  public ObjectFactory<HttpMessageConverters> httpMessageConvertersObjectFactory() {
    HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
    ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
    return objectFactory;
  }
  @Bean
  public HttpMessageConverters messageConverters() {
    return httpMessageConvertersObjectFactory().getObject();
  }
}
