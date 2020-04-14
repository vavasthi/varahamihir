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
import com.avasthi.varahamihir.common.pojos.TaxCategory;
import com.avasthi.varahamihir.standards.service.TaxCategoryService;
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
@RequestMapping(VarahamihirConstants.V1_TAX_CATEGORY_ENDPOINT)
@Api(value="Tax category endpoint", description="This endpoint provides tax category lifecycle operations")
public class TaxCategoryEndpoint extends BaseEndpoint {


  @Autowired
  private TaxCategoryService taxCategoryService;

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostAuthorize(VarahamihirConstants.ANNOTATION_ROLE_USER)
  public Optional<TaxCategory> getProductUnit(@PathVariable("id") UUID id) throws IOException {

    Optional<TaxCategory> taxCategoryOptional = taxCategoryService.getTaxCategory(id);
    return taxCategoryOptional;
  }

  @Transactional
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_ADMIN_OR_SUPERADMIN)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public TaxCategory createProductUnit(@RequestBody @Valid TaxCategory taxCategory) {
    return taxCategoryService.createTaxCategory(taxCategory);
  }

  @Transactional
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_USER_ADMIN_AND_TENANT_ADMIN)
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public TaxCategory updateTaxCategory(@PathVariable("id") UUID id,
                                       @RequestBody @Valid TaxCategory taxCategory) throws InvocationTargetException, IllegalAccessException {
    return taxCategoryService.updateTaxCategory(id, taxCategory);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_SUPERADMIN)
  public TaxCategory deleteTaxCategory(@PathVariable("id") UUID id) throws InvalidParameterSpecException {

    return taxCategoryService.deleteTaxCategory(id);
  }
}
