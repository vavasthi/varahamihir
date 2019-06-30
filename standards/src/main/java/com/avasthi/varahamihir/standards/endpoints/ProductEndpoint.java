/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.standards.endpoints;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.standards.service.ProductService;
import com.avasthi.varahamihir.common.endpoints.v1.BaseEndpoint;
import com.avasthi.varahamihir.common.pojos.Product;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
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
import java.util.Set;
import java.util.UUID;

/**
 * Created by vinay on 1/4/16.
 */
@RestController
@RequestMapping(VarahamihirConstants.V1_PRODUCT_ENDPOINT)
@Api(value="Product endpoint", description="This endpoint provides Product lifecycle operations")
public class ProductEndpoint extends BaseEndpoint {

  Logger logger = Logger.getLogger(ProductEndpoint.class);

  @Autowired
  private ProductService productService;

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @PostAuthorize(VarahamihirConstants.ANNOTATION_ROLE_USER)
  public Optional<Product> getProduct(@PathVariable("id") UUID id) throws IOException {

    Optional<Product> optionalProduct = productService.getProduct(id);
    return optionalProduct;
  }

  @Transactional
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_ADMIN_OR_SUPERADMIN)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Product createProduct(@RequestBody @Valid Product product) {
    return productService.createProduct(product);
  }

  @Transactional
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_USER_ADMIN_AND_TENANT_ADMIN)
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public Product updateProduct(@PathVariable("id") UUID id,
                               @RequestBody @Valid Product product) throws InvocationTargetException, IllegalAccessException {
    return productService.updateProduct(id, product);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_SUPERADMIN)
  public Product deleteProduct(@PathVariable("id") UUID id) throws InvalidParameterSpecException {

    return productService.deleteProduct(id);
  }

  @Transactional
  @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Set<Product> searchProduct(@RequestParam("string") String searchString) throws InvalidParameterSpecException {

    return productService.search(searchString);
  }
  @Transactional
  @RequestMapping(value = "/search/rebuild", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(VarahamihirConstants.ANNOTATION_ROLE_SUPERADMIN)
  public void searchRebuild() throws InvalidParameterSpecException {

    productService.searchRebuild();
  }
}
