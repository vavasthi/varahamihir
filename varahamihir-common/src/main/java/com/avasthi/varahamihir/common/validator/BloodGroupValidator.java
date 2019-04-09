/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.validator;

import com.avasthi.varahamihir.common.annotations.BloodGroups;
import com.avasthi.varahamihir.common.constants.SanjnanConstants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by nikhilvs on 16/06/16.
 */
public class BloodGroupValidator implements ConstraintValidator<BloodGroups, String> {

  private BloodGroups bloodGroups;
  @Override
  public void initialize(final BloodGroups genderType) {
    this.bloodGroups = bloodGroups;
  }

  @Override
  public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
      try {
          if(s!=null) {
              if (SanjnanConstants.BloodGroups.contains(s))
                  return true;
              else
                  return false;
          }
          return true;
      }
      catch (IllegalArgumentException ex){
          return false;
      }
  }


}
