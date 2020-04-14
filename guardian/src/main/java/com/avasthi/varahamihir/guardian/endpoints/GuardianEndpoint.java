/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.guardian.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.security.VarahamihirAuthenticationThreadLocal;
import com.avasthi.varahamihir.guardian.service.GuardianService;
import io.swagger.annotations.Api;
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
@RequestMapping(VarahamihirConstants.V1_GUARDIAN_ENDPOINT)
@Api(value="Guardian endpoint", description="This endpoint provides Customer lifecycle operations")
public class GuardianEndpoint extends BaseEndpoint {

  @Autowired
  private GuardianService guardianService;

  @Transactional
  @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account registerAsFulfiller() throws InvocationTargetException, IllegalAccessException {
    Account account = VarahamihirAuthenticationThreadLocal.INSTANCE.getAccountThreadLocal().get();
    return guardianService.createGuardian(account);
  }

}
