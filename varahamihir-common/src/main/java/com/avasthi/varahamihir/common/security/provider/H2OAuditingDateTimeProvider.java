package com.avasthi.varahamihir.common.security.provider;

import com.avasthi.varahamihir.common.services.DateTimeService;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

/**
 * Created by vinay on 1/28/16.
 */
public class H2OAuditingDateTimeProvider implements DateTimeProvider {

  private final DateTimeService dateTimeService;

  public H2OAuditingDateTimeProvider(DateTimeService dateTimeService) {
    this.dateTimeService = dateTimeService;
  }

  @Override
  public Optional<TemporalAccessor> getNow() {

    return Optional.of(dateTimeService.getCurrentDateAndTime());
  }
}