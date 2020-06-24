
package com.avasthi.varahamihir.common.constants;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;

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

  public static final String AUTOCONFIG_ENDPOINT = "/autoconfig";
  public static final String BEANS_ENDPOINT = "/beans";
  public static final String CONFIGPROPS_ENDPOINT = "/configprops";
  public static final String ENV_ENDPOINT = "/env";
  public static final String MAPPINGS_ENDPOINT = "/mappings";
  public static final String METRICS_ENDPOINT = "/metrics";
  public static final String SHUTDOWN_ENDPOINT = "/shutdown";

  public static final String BASE_ENDPOINT = "/{tenant}";
  public static final String V1_BASE_ENDPOINT = BASE_ENDPOINT + "/v1";
  public static final String V1_REGISTRATION_ENDPOINT = V1_BASE_ENDPOINT + "/registration";
  public static final String V1_USER_ENDPOINT = V1_BASE_ENDPOINT + "/user";

  public static final String V1_GUARDIAN_ENDPOINT = V1_BASE_ENDPOINT + "/guardian";
  public static final String V1_STUDENT_ENDPOINT = V1_BASE_ENDPOINT + "/customer";

  public static final String UNAUTHENTICATED_USER = "UnAuthenticated";
  public static final int TENANT_HEADER_PRECEDENCE = Ordered.HIGHEST_PRECEDENCE;
  public static final int SEED_DATA_PRECEDENCE = TENANT_HEADER_PRECEDENCE - 1;
  public static final int TENANT_PRECEDENCE = SEED_DATA_PRECEDENCE - 1;
  public static final int USER_PRECEDENCE = Ordered.LOWEST_PRECEDENCE;
}
