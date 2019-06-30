/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.annotations;


import com.avasthi.varahamihir.common.validator.VarahamihirUrlStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by vinay on 3/15/16.
 */
@Constraint(validatedBy = VarahamihirUrlStringValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface VarahamihirUrlString {

  String message() default "Invalid URL.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
