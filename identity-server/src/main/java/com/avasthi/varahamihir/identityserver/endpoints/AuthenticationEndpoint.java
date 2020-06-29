package com.avasthi.varahamihir.identityserver.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.BadRequestException;
import com.avasthi.varahamihir.common.pojos.VarahamihirAuthRequest;
import com.avasthi.varahamihir.common.pojos.VarahamihirAuthResponse;
import com.avasthi.varahamihir.identityserver.services.UserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Log4j2
@RestController
public class AuthenticationEndpoint {

  @Autowired
  private UserService userService;

  @RequestMapping(value = VarahamihirConstants.BASE_ENDPOINT + "/login",
          method = RequestMethod.POST,
          consumes = MediaType.APPLICATION_JSON_VALUE)
  public Mono<VarahamihirAuthResponse> login(@RequestBody VarahamihirAuthRequest ar)
          throws ParseException, JOSEException, BadJOSEException {
        return userService.getLoginResponse(ar);
  }
}


