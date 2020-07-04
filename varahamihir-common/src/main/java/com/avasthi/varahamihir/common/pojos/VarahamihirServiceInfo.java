package com.avasthi.varahamihir.common.pojos;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

@Data
@Builder
public class VarahamihirServiceInfo {
  private String name;
  private List<ServiceInstance> instances;
}
