package com.avasthi.varahamihir.standards.service;

import com.avasthi.varahamihir.standards.couchbase.TaxSurchargeRepository;
import com.avasthi.varahamihir.common.exception.EntityAlreadyExistsException;
import com.avasthi.varahamihir.common.exception.EntityNotFoundException;
import com.avasthi.varahamihir.common.pojos.Account;
import com.avasthi.varahamihir.common.pojos.TaxSurcharge;
import com.avasthi.varahamihir.common.security.SanjnanAuthenticationThreadLocal;
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
public class TaxSurchargeService {

  @Autowired
  private TaxSurchargeRepository taxSurchargeRepository;


  Logger logger = Logger.getLogger(TaxSurchargeService.class);

  public Optional<TaxSurcharge> getTaxSurcharge(UUID id) {

    Optional<TaxSurcharge> taxSurchargeOptional = taxSurchargeRepository.findById(id.toString());
    if (taxSurchargeOptional.isPresent()) {
      return taxSurchargeOptional;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.TAX_SURCHARGE_NOT_FOUND, id.toString()));
  }

  public TaxSurcharge createTaxSurcharge(TaxSurcharge taxSurcharge) {
    if (taxSurchargeRepository.findByName(taxSurcharge.getName()).isPresent()) {
      throw new EntityAlreadyExistsException(String.format(SanjnanMessages.TAX_SURCHARGE_ALREADY_EXISTS, taxSurcharge.getName()));
    }
    Account account = SanjnanAuthenticationThreadLocal.INSTANCE.getAccountThreadLocal().get();
    taxSurcharge.setId(UUID.randomUUID().toString());
    taxSurcharge.setCreatedBy(account.getId());
    taxSurcharge.setUpdatedBy(account.getId());
    taxSurcharge = taxSurchargeRepository.save(taxSurcharge);
    return taxSurcharge;
  }
  public TaxSurcharge updateTaxSurcharge(UUID id, TaxSurcharge taxSurcharge) throws InvocationTargetException, IllegalAccessException {

    Optional<TaxSurcharge> taxSurchargeOptional = taxSurchargeRepository.findById(id.toString());
    if (taxSurchargeOptional.isPresent()) {
      TaxSurcharge storedTaxSurcharge = taxSurchargeOptional.get();
      ObjectPatcher.diffAndPatch(TaxSurcharge.class, storedTaxSurcharge, TaxSurcharge.class, taxSurcharge);
      storedTaxSurcharge.setUpdatedAt(DateTime.now());
      Account account = SanjnanAuthenticationThreadLocal.INSTANCE.getAccountThreadLocal().get();
      storedTaxSurcharge.setUpdatedBy(account.getId());
      storedTaxSurcharge= taxSurchargeRepository.save(storedTaxSurcharge);
      return storedTaxSurcharge;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.TAX_SURCHARGE_NOT_FOUND, taxSurcharge.getId()));
  }
  public TaxSurcharge deleteTaxSurcharge(UUID id) {
    Optional<TaxSurcharge> taxSurchargeOptional = taxSurchargeRepository.findById(id.toString());
    if (taxSurchargeOptional.isPresent()) {
      TaxSurcharge storedTaxSurcharge = taxSurchargeOptional.get();
      taxSurchargeRepository.deleteById(id.toString());
      return storedTaxSurcharge;
    }
    throw new EntityNotFoundException(String.format(SanjnanMessages.TAX_SURCHARGE_NOT_FOUND, id.toString()));
  }

}
