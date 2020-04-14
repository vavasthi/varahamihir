/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.identityserver.test;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.pojos.VarahamihirTokenResponse;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.eclipse.jetty.util.B64Code;

import static io.restassured.RestAssured.given;

/**
 * Created by vinay on 2/10/16.
 */
@Log4j2
public class TestCaseBase {

  protected static String authenticateUrl = "http://localhost:8080/v1/internal/authenticate";
  protected static String baseUrl = "http://localhost:8080/v1/";
  protected static String username = "Hubble";
  protected static String password = "Hobble";
  protected static String internalTenant = "internal";
  protected String authToken;
  protected String deviceToken;
  protected String tempAuthToken;
  protected String accountAuthToken;
  protected void initialization() {

    {

      String envVal  = System.getenv("TEST_AUTH_URL");
      if (envVal != null) {
        authenticateUrl = envVal;
      }
    }
    {

      String envVal  = System.getenv("TEST_AUTH_USERNAME");
      if (envVal != null) {
        username = envVal;
      }
    }
    {

      String envVal  = System.getenv("TEST_AUTH_PASSWORD");
      if (envVal != null) {
        password = envVal;
      }
    }
    {

      String envVal  = System.getenv("TEST_AUTH_TENANT");
      if (envVal != null) {
        internalTenant = envVal;
      }
    }
    Response r = given().get(baseUrl + "setup").andReturn();
    log.info(r.prettyPrint());
    VarahamihirTokenResponse authResponse =  given().
            header(VarahamihirConstants.AUTH_AUTHORIZATION_HEADER, "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(VarahamihirConstants.AUTH_USERNAME_HEADER, username).
            header(VarahamihirConstants.AUTH_PASSWORD_HEADER, password).
            header(VarahamihirConstants.AUTH_TENANT_HEADER, internalTenant).
            header(VarahamihirConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(VarahamihirConstants.AUTH_CLIENT_ID_HEADER, "MyRestAssuredClient").
        when().post(authenticateUrl).body().as(VarahamihirTokenResponse.class);

    log.info(String.format("Authenticating %s, received response %s", username, authResponse));
    authToken = authResponse.getAuthToken();
  }
  protected void cleanup() {

/*    Response response =  given().
        header(VarahamihirConstants.AUTH_AUTHORIZATION_HEADER, "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
        header(VarahamihirConstants.AUTH_USERNAME_HEADER, username).
        header(VarahamihirConstants.AUTH_PASSWORD_HEADER, password).
        header(VarahamihirConstants.AUTH_TENANT_HEADER, internalTenant).
        header(VarahamihirConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
        header(VarahamihirConstants.AUTH_CLIENT_ID_HEADER, "MyRestAssuredClient").
        when().delete(VarahamihirConstants.V1_SETUP_ENDPOINT).andReturn();*/
  }
}
