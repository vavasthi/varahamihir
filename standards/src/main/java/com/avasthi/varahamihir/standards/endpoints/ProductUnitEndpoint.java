/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.standards.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.standards.service.ProductUnitService;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.pojos.ProductUnit;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RestController
@RequestMapping(VarahamihirConstants.V1_PRODUCT_UNIT_ENDPOINT)
@Api(value="Product unit endpoint", description="This endpoint provides Product unit lifecycle operations")
public class ProductUnitEndpoint extends BaseEndpoint {


  @Autowired
  private ProductUnitService productUnitService;

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostAuthorize(VarahamihirConstants.ANNOTATION_ROLE_USER)
  public Optional<ProductUnit> getProductUnit(@PathVariable("id") UUID id) throws IOException {

    Optional<ProductUnit> productUnitOptional = productUnitService.getProductUnit(id);
    return productUnitOptional;
  }

  @Transactional
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_ADMIN_OR_SUPERADMIN)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ProductUnit createProductUnit(@RequestBody @Valid ProductUnit productUnit) {
    return productUnitService.createProductUnit(productUnit);
  }

  @Transactional
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_USER_ADMIN_AND_TENANT_ADMIN)
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ProductUnit updateProductUnit(@PathVariable("id") UUID id,
                                       @RequestBody @Valid ProductUnit productUnit) throws InvocationTargetException, IllegalAccessException {
    return productUnitService.updateProductUnit(id, productUnit);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_SUPERADMIN)
  public ProductUnit deleteProductUnit(@PathVariable("id") UUID id) throws InvalidParameterSpecException {

    return productUnitService.deleteProductUnit(id);
  }
}
