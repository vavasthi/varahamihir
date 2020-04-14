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
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.B64Code;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

/**
 * Created by vinay on 2/8/16.
 */
@Log4j2
public class ITTestIdentityServerAuthenticateWorkflow extends TestCaseBase {

  private VarahamihirTokenResponse authResponse;

  @BeforeClass
  public void setup() {
    initialization();
    authResponse =  given().
            header(VarahamihirConstants.AUTH_AUTHORIZATION_HEADER, "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(VarahamihirConstants.AUTH_USERNAME_HEADER, username).
            header(VarahamihirConstants.AUTH_PASSWORD_HEADER, password).
            header(VarahamihirConstants.AUTH_TENANT_HEADER, internalTenant).
            header(VarahamihirConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(VarahamihirConstants.AUTH_CLIENT_ID_HEADER, "MyRestAssuredClient").
        when().post(authenticateUrl).body().as(VarahamihirTokenResponse.class);
    log.info(String.format("Authenticating %s, received response %s", username, authResponse));
  }


  @Test(threadPoolSize = 100, invocationCount = 400, successPercentage = 98)
  public void testAuthenticate() {

    Response r  =  given().
        header("Content-Type", "application/json").
            header(VarahamihirConstants.AUTH_TENANT_HEADER, internalTenant).
            header(VarahamihirConstants.AUTH_TOKEN_HEADER, authResponse.getAuthToken()).
            header(VarahamihirConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(VarahamihirConstants.AUTH_CLIENT_ID_HEADER, "MyRestAssuredClient").
        when().post(authenticateUrl + "/validate").andReturn();
    log.info("Authenticating using token.\n" + r.prettyPrint());
    assertEquals(r.getStatusCode(), HttpStatus.OK_200);
    VarahamihirTokenResponse response = r.as(VarahamihirTokenResponse.class);
    log.info(response.toString());
  }

}
