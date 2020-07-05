package com.avasthi.varahamihir.client.services;

import com.avasthi.varahamihir.common.exceptions.*;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
public class VarahamihirAbstractFeignClientService {
  protected VarahamihirBaseException raiseAppropriateException(Throwable e) {
    log.error("Exception logged.",e);
    return new MicroservicesPeerUnavailableException("Identity Server replica not available."+ e.toString(), e);
  }
  protected VarahamihirBaseException convertFeignException(FeignException fex, String userId, String tenant) {

    HttpStatus status = HttpStatus.resolve(fex.status());
    if (status != null) {

      switch(status) {
        case UNAUTHORIZED:
          return new UnauthorizedException("Authentication failed.", fex);
        case NOT_FOUND:
          return new EntityNotFoundException(String.format("User %s for tenant %s doesn't exist.", userId, tenant));
        case FORBIDDEN:
          return new UnauthorizedException(String.format("This request is forbidden."));
        case GATEWAY_TIMEOUT:
          return new MicroservicesPeerUnavailableException("The server timed out.");
        default:
          return new UnknownException("There is an unknown error in calling server.");
      }
    }
    return new UnknownException("Unknown exception", fex);
  }
}
