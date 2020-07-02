
package com.avasthi.varahamihir.common.constants;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vinay on 1/4/16.
 */
public class VarahamihirConstants {

  public static final String EXCEPTION_URL = "http://www.varahamihir.com/errors/%s";
  public static final String MSG_SUCCESS = "Success!";

  public static final String AUTOCONFIG_ENDPOINT = "/manage/autoconfig";
  public static final String BEANS_ENDPOINT = "/manage/beans";
  public static final String CONFIGPROPS_ENDPOINT = "/manage/configprops";
  public static final String ENV_ENDPOINT = "/manage/env";
  public static final String MAPPINGS_ENDPOINT = "/manage/mappings";
  public static final String METRICS_ENDPOINT = "/manage/metrics";
  public static final String SHUTDOWN_ENDPOINT = "/manage/shutdown";
  public static final String HEALTH_ENDPOINT = "/manage/health";

  public static final String BASE_ENDPOINT = "/{tenant}";
  public static final String V1_BASE_ENDPOINT = BASE_ENDPOINT;
  public static final String V1_REGISTRATION_ENDPOINT = V1_BASE_ENDPOINT + "/registration";
  public static final String V1_USER_ENDPOINT = V1_BASE_ENDPOINT + "/user";
  public static final String V1_SERVICES_ENDPOINT = V1_BASE_ENDPOINT + "/services";

  public static final String V1_GUARDIAN_ENDPOINT = V1_BASE_ENDPOINT + "/guardian";
  public static final String V1_STUDENT_ENDPOINT = V1_BASE_ENDPOINT + "/student";

  public static final String V1_TOKEN_ENDPOINT = BASE_ENDPOINT + "/oauth/token";

  public static final String UNAUTHENTICATED_USER = "UnAuthenticated";
  public static final int AUTHORIZATION_HEADER_PRECEDENCE = SecurityWebFiltersOrder.REACTOR_CONTEXT.getOrder() - 1;
  public static final int TENANT_HEADER_PRECEDENCE = AUTHORIZATION_HEADER_PRECEDENCE - 1;
  public static final int TENANT_PRECEDENCE = TENANT_HEADER_PRECEDENCE - 1;
  public static final int USER_PRECEDENCE = Ordered.LOWEST_PRECEDENCE;

  public static final String DEFAULT_CLIENT = "supersecretclient";
  public static final String DEFAULT_SECRET = "supersecretclient123";
  public static final int DEFAULT_ACCESS_TOKEN_VALIDITY = 500;
  public static final int DEFAULT_REFRESH_TOKEN_VALIDITY = 1000;

  public static final String DEFAULT_TENANT = "default";
  public static final String TOKEN_TYPE_CLAIM = "token_type_claim";
  public static final String TOKEN_SUBJECT_TYPE = "token_subject_claim";
  public static final String TOKEN_TENANT_ID = "token_tenant_id";
  public static final String TOKEN_ROLE_CLAIM = "roles";
  public static final int DEFAULT_EXPIRY = 100;
  public static final int DEFAULT_REFRESH_EXPIRY = 200;
  public static final String TENANT_DISCRIMINATOR_IN_CONTEXT = "TENANT_DISCRIMINATOR_IN_CONTEXT";
  public static final String TENANT_IN_CONTEXT = "TENANT_IN_CONTEXT";
  public static final String USER_IN_CONTEXT = "USER_IN_CONTEXT";
  public static final String AUTHORIZATION_HEADER_IN_CONTEXT = "AUTHORIZATION_HEADER_IN_CONTEXT";
  public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
}
