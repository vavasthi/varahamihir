
package com.avasthi.varahamihir.identityserver;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.avasthi.varahamihir.common.services.DateTimeService;
import lombok.extern.log4j.Log4j2;
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
import java.util.logging.Logger;

/**
 * Created by vinay on 1/8/16.
 */
@EnableWebMvc
@SpringBootApplication(scanBasePackages = {"com.avasthi.vartahamihir"}, exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}
)
@Configuration
@EnableJpaRepositories("com.avasthi.vartahamihir")
//@EnableSwagger2
@Log4j2
public class VarahamihirIdentityManagerLauncher extends SpringBootServletInitializer {



  public static void main(String[] args) throws Exception {

    VarahamihirIdentityManagerLauncher launcher = new VarahamihirIdentityManagerLauncher();
    launcher
            .configure(new SpringApplicationBuilder(VarahamihirIdentityManagerLauncher.class))
            .run(args);
  }

  @PostConstruct
  public void initialize() {
    //setupService.setup();
  }
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(VarahamihirIdentityManagerLauncher.class);
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