
package com.avasthi.varahamihir.identityserver;

import com.avasthi.varahamihir.common.constants.MyConstants;
import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.enums.Role;
import com.avasthi.varahamihir.common.pojos.VarahamihirGrantedAuthority;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.identityserver.services.ClientService;
import com.avasthi.varahamihir.identityserver.services.TenantService;
import com.avasthi.varahamihir.identityserver.utils.VarahamihirRequestContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by vinay on 1/8/16.
 */
@EnableWebMvc
@SpringBootApplication(scanBasePackages = {"com.avasthi.varahamihir"})
@Configuration
@EnableResourceServer
//@EntityScan(basePackages = {"com.avasthi.varahamihir"})
//@EnableJpaRepositories("com.avasthi.varahamihir")
//@EnableSwagger2
@Log4j2
public class VarahamihirIdentityManagerLauncher extends SpringBootServletInitializer {


  @Autowired
  private ClientService clientService;
  @Autowired
  private TenantService tenantService;

  public static void main(String[] args) throws Exception {

    VarahamihirIdentityManagerLauncher launcher = new VarahamihirIdentityManagerLauncher();
    launcher
            .configure(new SpringApplicationBuilder(VarahamihirIdentityManagerLauncher.class))
            .run(args);
  }

  @PostConstruct
  public void initialize() {
    if (tenantService.count() == 0) {

      Tenant tenant = new Tenant();
      tenant.setDescription("Default tenant for the system");
      tenant.setDiscriminator(VarahamihirConstants.DEFAULT_TENANT);
      tenant.setName("Default Tenant");
      tenant.setDefaultValue(true);
      tenantService.save(tenant);
    }

    if (clientService.count() == 0) {
      VarahamihirClientDetails clientDetails = new VarahamihirClientDetails();
      clientDetails.setClientId(VarahamihirConstants.DEFAULT_CLIENT);
      clientDetails.setClientSecret(VarahamihirConstants.DEFAULT_SECRET);
      clientDetails.setAccessTokenValidity(VarahamihirConstants.DEFAULT_ACCESS_TOKEN_VALIDITY);
      clientDetails.setRefreshTokenValidity(VarahamihirConstants.DEFAULT_REFRESH_TOKEN_VALIDITY);
      clientDetails.setAuthorities(Arrays.asList(Role.ADMIN.name(), Role.GUARDIAN.name(), Role.STUDENT.name()).stream().map(e -> new VarahamihirGrantedAuthority(e)).collect(Collectors.toSet()));
      clientDetails.setScope(Arrays.asList("read", "write").stream().collect(Collectors.toSet()));
      clientDetails.setAutoApprove(true);
      clientDetails.setAuthorizedGrantTypes(MyConstants.AUTHORIZED_GRANT_TYPES);
      clientService.create(clientDetails);
    }
//setupService.setup();
  }
}