/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.student.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.security.VarahamihirAuthenticationThreadLocal;
import com.avasthi.varahamihir.student.service.StudentService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RestController
@RequestMapping(VarahamihirConstants.V1_CUSTOMER_ENDPOINT)
@Api(value="Student endpoint", description="This endpoint provides Student lifescycle operations")
public class StudentEndpoint extends BaseEndpoint {


  @Autowired
  private StudentService studentService;

  @Transactional
  @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account registerAsFulfiller() throws InvocationTargetException, IllegalAccessException {
    Account account = VarahamihirAuthenticationThreadLocal.INSTANCE.getAccountThreadLocal().get();
    return studentService.createFulfiller(account);
  }

}
