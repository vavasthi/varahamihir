package com.avasthi.varahamihir.standards;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.avasthi.varahamihir.common.services.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"com.avasthi.varahamihir"}, exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
)
@Configuration
@EnableJpaRepositories("com.avasthi.varahamihir")
public class StandardsLauncher{


  public static void main(String[] args) {

    SpringApplication.run(StandardsLauncher.class, args);
  }
  @PostConstruct
  public void initialize() {

  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public Module jodaModule() {
    return new JodaModule();
  }
}
