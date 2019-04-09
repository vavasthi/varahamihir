/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.student.endpoints;

import com.avasthi.varahamihir.common.constants.SanjnanConstants;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.security.SanjnanAuthenticationThreadLocal;
import com.avasthi.varahamihir.student.service.FulfillerService;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by vinay on 1/4/16.
 */
@RestController
@RequestMapping(SanjnanConstants.V1_FULFILLER_ENDPOINT)
@Api(value="Fulfiller endpoint", description="This endpoint provides Fulfiller lifescycle operations")
public class FulfillerEndpoint extends BaseEndpoint {

  Logger logger = Logger.getLogger(FulfillerEndpoint.class);

  @Autowired
  private FulfillerService fulfillerService;

  @Transactional
  @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account registerAsFulfiller() throws InvocationTargetException, IllegalAccessException {
    Account account = SanjnanAuthenticationThreadLocal.INSTANCE.getAccountThreadLocal().get();
    return fulfillerService.createFulfiller(account);
  }

}
