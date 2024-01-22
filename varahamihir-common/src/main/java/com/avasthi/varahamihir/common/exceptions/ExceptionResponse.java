package com.avasthi.varahamihir.common.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ExceptionResponse {

  private Date timestamp;
  private String path;
  private int status;
  private String error;
  private String message;
  private String requestId;
  private List<String> stackTrace;
}
