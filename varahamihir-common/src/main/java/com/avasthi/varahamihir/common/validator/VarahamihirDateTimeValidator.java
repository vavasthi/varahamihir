package com.avasthi.varahamihir.common.validator;

import com.avasthi.varahamihir.common.annotations.VarahamihirDateTime;


import lombok.extern.log4j.Log4j2;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
public class VarahamihirDateTimeValidator implements ConstraintValidator<VarahamihirDateTime, String> {

  private VarahamihirDateTime h2ODateTime;
  @Override
  public void initialize(final VarahamihirDateTime varahamihirDateTime) {
    this.h2ODateTime = varahamihirDateTime;
  }

  @Override
  public boolean isValid(final String s, final ConstraintValidatorContext constraintValidatorContext) {
    try {

      if(s!=null) {
          DateTimeFormatter formatter = DateTimeFormat.forPattern(h2ODateTime.dateTimePattern());
          formatter.parseDateTime(s);
      }
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
