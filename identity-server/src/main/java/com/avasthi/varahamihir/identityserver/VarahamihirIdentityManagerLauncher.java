package com.avasthi.varahamihir.identityserver;

import com.avasthi.varahamihir.identityserver.factories.IdentityPasswordEncoderFactories;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;


/**
 * Created by vinay on 1/8/16.
 */
@SpringBootApplication(scanBasePackages = {"com.avasthi.varahamihir"})
@Log4j2
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, proxyTargetClass = true)
public class VarahamihirIdentityManagerLauncher  {


  public static void main(String[] args) throws Exception {

    SpringApplication.run(VarahamihirIdentityManagerLauncher.class, args);
  }

  @PostConstruct
  public void initialize() {
  }
  @Bean(name = "passwordEncoder")
  public PasswordEncoder passwordEncoder() {
    PasswordEncoder passwordEncoder = IdentityPasswordEncoderFactories.createDelegatingPasswordEncoder();
    return passwordEncoder;
  }
}