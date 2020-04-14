/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.validator;

import com.avasthi.varahamihir.common.annotations.Gender;
import com.avasthi.varahamihir.common.enums.GENDER;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by nikhilvs on 16/06/16.
 */
@Log4j2
public class GenderTypeValidator implements ConstraintValidator<Gender, String> {

  private Gender genderType;
  @Override
  public void initialize(final Gender genderType) {
    this.genderType = genderType;
  }

  @Override
  public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
    log.info("In gender validations genderType :"+genderType+ "  s :"+s);

    try {
      if(s!=null)
      {
        GENDER gender = GENDER.valueOf(s);
        log.info("enum parse done ");
      }
      return true;
    }
    catch (IllegalArgumentException ex){
      log.info("enum parse failed "+ex);
      return false;
    }
  }


}
