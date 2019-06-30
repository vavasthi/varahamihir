package com.avasthi.varahamihir.standards.service;

import com.avasthi.varahamihir.standards.couchbase.ProductUnitRepository;
import com.avasthi.varahamihir.common.exception.EntityAlreadyExistsException;
import com.avasthi.varahamihir.common.exception.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.pojos.ProductUnit;
import com.avasthi.varahamihir.common.security.VarahamihirAuthenticationThreadLocal;
import com.avasthi.varahamihir.common.utils.ObjectPatcher;
import com.avasthi.varahamihir.common.utils.SanjnanMessages;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductUnitService {

  @Autowired
  private ProductUnitRepository productUnitRepository;


  Logger logger = Logger.getLogger(ProductUnitService.class);

  public Optional<ProductUnit> getProductUnit(UUID id) {

    Optional<ProductUnit> productUnitOptional = productUnitRepository.findById(id.toString());
    if (productUnitOptional.isPresent()) {
      return productUnitOptional;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.PRODUCT_UNIT_NOT_FOUND, id.toString()));
  }

  public ProductUnit createProductUnit(ProductUnit productUnit) {
    if (productUnitRepository.findByAcronym(productUnit.getAcronym()).isPresent()) {
      throw new EntityAlreadyExistsException(String.format(SanjnanMessages.PRODUCT_UNIT_ALREADY_EXISTS, productUnit.getAcronym()));
    }
    Account account = VarahamihirAuthenticationThreadLocal.INSTANCE.getAccountThreadLocal().get();
    productUnit.setId(UUID.randomUUID().toString());
    productUnit.setCreatedBy(account.getId());
    productUnit.setUpdatedBy(account.getId());
    productUnit = productUnitRepository.save(productUnit);
    return productUnit;
  }
  public ProductUnit updateProductUnit(UUID id, ProductUnit productUnit) throws InvocationTargetException, IllegalAccessException {

    Optional<ProductUnit> productUnitOptional = productUnitRepository.findById(id.toString());
    if (productUnitOptional.isPresent()) {
      ProductUnit storedProduct = productUnitOptional.get();
      ObjectPatcher.diffAndPatch(ProductUnit.class, storedProduct, ProductUnit.class, productUnit);
      storedProduct.setUpdatedAt(DateTime.now());
      Account account = VarahamihirAuthenticationThreadLocal.INSTANCE.getAccountThreadLocal().get();
      storedProduct.setUpdatedBy(account.getId());
      storedProduct = productUnitRepository.save(storedProduct);
      return storedProduct;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.PRODUCT_UNIT_NOT_FOUND, productUnit.getId()));
  }
  public ProductUnit deleteProductUnit(UUID id) {
    Optional<ProductUnit> productUnitOptional = productUnitRepository.findById(id.toString());
    if (productUnitOptional.isPresent()) {
      ProductUnit storedProduct = productUnitOptional.get();
      productUnitRepository.deleteById(id.toString());
      return storedProduct;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.PRODUCT_UNIT_NOT_FOUND, id.toString()));
  }

}
