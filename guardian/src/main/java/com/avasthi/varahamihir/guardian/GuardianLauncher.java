package com.avasthi.varahamihir.guardian;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import io.dekorate.kubernetes.annotation.ImagePullPolicy;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Probe;
import io.dekorate.kubernetes.annotation.ServiceType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = {"com.avasthi.varahamihir"})
//@Configuration
//@EnableJpaRepositories("com.avasthi.varahamihir")
@KubernetesApplication(livenessProbe = @Probe(httpActionPath = "/manage/health"),
        readinessProbe = @Probe(httpActionPath = "/manage/health"),
        serviceType = ServiceType.NodePort,
        imagePullPolicy = ImagePullPolicy.Always)
public class GuardianLauncher extends SpringBootServletInitializer {

  public static void main(String[] args) {

    GuardianLauncher launcher = new GuardianLauncher();
    launcher
            .configure(new SpringApplicationBuilder(GuardianLauncher.class))
            .run(args);
  }
  @PostConstruct
  public void initialize() {

  }
  @Bean
  public Module jodaModule() {
    return new JodaModule();
  }
}
