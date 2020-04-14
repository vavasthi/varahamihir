/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.standards.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.pojos.TaxSurcharge;
import com.avasthi.varahamihir.standards.service.TaxSurchargeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by vinay on 1/4/16.
 */
@RestController
@RequestMapping(VarahamihirConstants.V1_TAX_SURCHARGE_ENDPOINT)
@Api(value="Tax surcharge endpoint", description="This endpoint provides tax surcharge lifecycle operations")
public class TaxSurchargeEndpoint extends BaseEndpoint {


  @Autowired
  private TaxSurchargeService taxSurchargeService;

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostAuthorize(VarahamihirConstants.ANNOTATION_ROLE_USER)
  public Optional<TaxSurcharge> getProductUnit(@PathVariable("id") UUID id) throws IOException {

    Optional<TaxSurcharge> taxSurchargeOptional = taxSurchargeService.getTaxSurcharge(id);
    return taxSurchargeOptional;
  }

  @Transactional
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_ADMIN_OR_SUPERADMIN)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public TaxSurcharge createProductUnit(@RequestBody @Valid TaxSurcharge taxSurcharge) {
    return taxSurchargeService.createTaxSurcharge(taxSurcharge);
  }

  @Transactional
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_USER_ADMIN_AND_TENANT_ADMIN)
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public TaxSurcharge updateTaxSurcharge(@PathVariable("id") UUID id,
                                       @RequestBody @Valid TaxSurcharge taxSurcharge) throws InvocationTargetException, IllegalAccessException {
    return taxSurchargeService.updateTaxSurcharge(id, taxSurcharge);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_SUPERADMIN)
  public TaxSurcharge deleteTaxSurcharge(@PathVariable("id") UUID id) throws InvalidParameterSpecException {

    return taxSurchargeService.deleteTaxSurcharge(id);
  }
}
