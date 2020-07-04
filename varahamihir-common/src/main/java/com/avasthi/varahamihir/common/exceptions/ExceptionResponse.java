package com.avasthi.varahamihir.common.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExceptionResponse {

  private Date timestamp;
  private String path;
  private int status;
  private String error;
  private String messge;
  private String reason;
  private String requestId;
}
