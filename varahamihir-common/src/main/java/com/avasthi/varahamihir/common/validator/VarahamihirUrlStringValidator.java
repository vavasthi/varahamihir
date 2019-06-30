/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.validator;

import com.avasthi.varahamihir.common.annotations.VarahamihirUrlString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vinay on 3/15/16.
 */
public class VarahamihirUrlStringValidator implements ConstraintValidator<VarahamihirUrlString, String> {

  private VarahamihirUrlString urlString;
  @Override
  public void initialize(final VarahamihirUrlString urlString) {
    this.urlString = urlString;
  }

  @Override
  public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {

    if (s == null) {
      return true;
    }
    try {
      URL url = new URL(s);
      return true;
    } catch (MalformedURLException e) {
      return false;
    }
  }
}
