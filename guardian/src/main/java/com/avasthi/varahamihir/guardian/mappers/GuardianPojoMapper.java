package com.avasthi.varahamihir.guardian.mappers;

import com.avasthi.varahamihir.common.pojos.GuardianPojo;
import com.avasthi.varahamihir.guardian.entities.Guardian;
import org.springframework.stereotype.Component;

@Component
public class GuardianPojoMapper {

  public GuardianPojo convert(Guardian guardian) {

      return GuardianPojo.builder()
              .userId(guardian.getUserId())
              .tenantId(guardian.getTenantId())
              .createdAt(guardian.getCreatedAt())
              .createdBy(guardian.getCreatedBy())
              .updatedBy(guardian.getUpdatedBy())
              .children(guardian.getChildren())
              .build(); // Not copying password into pojo
  }
  public Guardian convert(GuardianPojo guardianPojo) {

    return Guardian.builder()
            .userId(guardianPojo.getUserId())
            .createdAt(guardianPojo.getCreatedAt())
            .createdBy(guardianPojo.getCreatedBy())
            .tenantId(guardianPojo.getTenantId())
            .updatedAt(guardianPojo.getUpdatedAt())
            .updatedBy(guardianPojo.getUpdatedBy())
            .children(guardianPojo.getChildren())
            .build();
  }
}
