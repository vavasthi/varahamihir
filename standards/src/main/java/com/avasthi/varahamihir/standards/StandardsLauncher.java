package com.avasthi.varahamihir.standards;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.avasthi.varahamihir.common.security.provider.VarahamihirAuditingDateTimeProvider;
import com.avasthi.varahamihir.common.services.DateTimeService;
import com.avasthi.varahamihir.standards.couchbase.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@EnableWebMvc
@SpringBootApplication(scanBasePackages = {"com.avasthi.varahamihir"}, exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
)
@Configuration
@EnableJpaRepositories("com.avasthi.varahamihir")
public class StandardsLauncher extends SpringBootServletInitializer {

  @Autowired
  private DateTimeService dateTimeService;

  @Autowired
  private ProductRepository productRepository;


  public static void main(String[] args) {

    StandardsLauncher launcher = new StandardsLauncher();
    launcher
            .configure(new SpringApplicationBuilder(StandardsLauncher.class))
            .run(args);
  }
  @PostConstruct
  public void initialize() {

  }
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(StandardsLauncher.class);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  DateTimeProvider dateTimeProvider() {
    return new VarahamihirAuditingDateTimeProvider(dateTimeService);
  }

  @Bean
  public Module jodaModule() {
    return new JodaModule();
  }
}
