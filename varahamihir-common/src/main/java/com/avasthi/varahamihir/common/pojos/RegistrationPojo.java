package com.avasthi.varahamihir.common.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationPojo {

  public enum USER_TYPE {
    STUDENT,
    GUARDIAN
  }
  private UUID id;
  private UUID tenantId;
  private String guardianName;
  private UUID guardianId;
  private USER_TYPE userType;
  private String fullname;
  private String username;
  private String password;
  private String email;
}
