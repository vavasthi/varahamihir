package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;
import com.avasthi.varahamihir.identityserver.repositories.TenantRepository;
import com.avasthi.varahamihir.identityserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TenantRepository tenantRepository;

  public Flux<User> findAll() {
    return Flux.fromIterable(userRepository.findAll());
  }
  public Mono<User> findByUsername(String username) {
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              Tenant tenant
                      = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              return Mono.just(userRepository.findUserByTenantAndUsername(tenant.getId(),
                      username).get());
            }).switchIfEmpty(Mono.empty());
  }
  public Mono<User> save(User user) {
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              Tenant tenant
                      = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              user.setTenantId(tenant.getId());
              return Mono.just(userRepository.save(user));
            }).switchIfEmpty(Mono.empty());
  }

  public void delete(UUID id) {
    userRepository.deleteById(id);
  }
}
