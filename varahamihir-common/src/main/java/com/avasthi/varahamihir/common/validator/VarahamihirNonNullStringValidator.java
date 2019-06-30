/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.validator;


import com.avasthi.varahamihir.common.annotations.VarahamihirNonNullString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by vinay on 3/15/16.
 */
public class VarahamihirNonNullStringValidator implements ConstraintValidator<VarahamihirNonNullString, String> {

  private VarahamihirNonNullString varahamihirNonNullString;
  @Override
  public void initialize(final VarahamihirNonNullString varahamihirNonNullString) {
    this.varahamihirNonNullString = varahamihirNonNullString;
  }

  @Override
  public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {

    if (s == null) {
      return varahamihirNonNullString.nullAllowed();
    }
    if (s.contains("<") || s.contains("<")) {
      return false;
    }
    if (s.length() >= varahamihirNonNullString.min() && s.length() <= varahamihirNonNullString.max()) {
      return true;
    }
    return false;
  }
}
