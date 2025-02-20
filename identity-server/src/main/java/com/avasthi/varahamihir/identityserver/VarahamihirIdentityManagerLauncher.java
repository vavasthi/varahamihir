package com.avasthi.varahamihir.identityserver;

import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.factories.IdentityPasswordEncoderFactories;
import com.avasthi.varahamihir.identityserver.repositories.UserRepository;
import com.avasthi.varahamihir.identityserver.services.UserService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.crypto.spec.SecretKeySpec;
import java.awt.desktop.UserSessionEvent;
import java.io.*;
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

  @Bean(name = "passwordEncoder")
  public PasswordEncoder passwordEncoder() {
    PasswordEncoder passwordEncoder = IdentityPasswordEncoderFactories.createDelegatingPasswordEncoder();
    return passwordEncoder;
  }

}