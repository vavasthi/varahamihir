package com.avasthi.varahamihir.common.pojos;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class StudentPojo {

  private UUID userId;
  private UUID tenantId;
  private String createdBy;
  private String updatedBy;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date createdAt;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date updatedAt;
}
