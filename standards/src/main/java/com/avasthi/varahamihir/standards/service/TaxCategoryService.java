package com.avasthi.varahamihir.standards.service;

import com.avasthi.varahamihir.standards.couchbase.TaxCategoryRepository;
import com.avasthi.varahamihir.common.exception.EntityAlreadyExistsException;
import com.avasthi.varahamihir.common.exception.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.pojos.TaxCategory;
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
public class TaxCategoryService {

  @Autowired
  private TaxCategoryRepository taxCategoryRepository;


  Logger logger = Logger.getLogger(TaxCategoryService.class);

  public Optional<TaxCategory> getTaxCategory(UUID id) {

    Optional<TaxCategory> taxCategoryOptional = taxCategoryRepository.findById(id.toString());
    if (taxCategoryOptional.isPresent()) {
      return taxCategoryOptional;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.TAX_CATEGORY_NOT_FOUND, id.toString()));
  }

  public TaxCategory createTaxCategory(TaxCategory taxCategory) {
    if (taxCategoryRepository.findByName(taxCategory.getName()).isPresent()) {
      throw new EntityAlreadyExistsException(String.format(SanjnanMessages.TAX_CATEGORY_ALREADY_EXISTS, taxCategory.getName()));
    }
    Account account = VarahamihirAuthenticationThreadLocal.INSTANCE.getAccountThreadLocal().get();
    taxCategory.setId(UUID.randomUUID().toString());
    taxCategory.setCreatedBy(account.getId());
    taxCategory.setUpdatedBy(account.getId());
    taxCategory = taxCategoryRepository.save(taxCategory);
    return taxCategory;
  }
  public TaxCategory updateTaxCategory(UUID id, TaxCategory taxCategory) throws InvocationTargetException, IllegalAccessException {

    Optional<TaxCategory> taxCategoryOptional = taxCategoryRepository.findById(id.toString());
    if (taxCategoryOptional.isPresent()) {
      TaxCategory storedTaxCategory = taxCategoryOptional.get();
      ObjectPatcher.diffAndPatch(TaxCategory.class, storedTaxCategory, TaxCategory.class, taxCategory);
      storedTaxCategory.setUpdatedAt(DateTime.now());
      Account account = VarahamihirAuthenticationThreadLocal.INSTANCE.getAccountThreadLocal().get();
      storedTaxCategory.setUpdatedBy(account.getId());
      storedTaxCategory= taxCategoryRepository.save(storedTaxCategory);
      return storedTaxCategory;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.TAX_CATEGORY_NOT_FOUND, taxCategory.getId()));
  }
  public TaxCategory deleteTaxCategory(UUID id) {
    Optional<TaxCategory> taxCategoryOptional = taxCategoryRepository.findById(id.toString());
    if (taxCategoryOptional.isPresent()) {
      TaxCategory storedTaxCategory = taxCategoryOptional.get();
      taxCategoryRepository.deleteById(id.toString());
      return storedTaxCategory;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.TAX_CATEGORY_NOT_FOUND, id.toString()));
  }

}
