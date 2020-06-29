package com.avasthi.varahamihir.identityserver.services;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;
import com.avasthi.varahamihir.common.exceptions.UnauthorizedException;
import com.avasthi.varahamihir.common.exceptions.EntityAlreadyExistsException;
import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import com.avasthi.varahamihir.identityserver.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class ClientService {
  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  @Qualifier(value = "passwordEncoder")
  private PasswordEncoder passwordEncoder;

  public Flux<VarahamihirClientDetails> findAll() {
    return Flux.defer(() -> Flux.fromIterable(clientRepository.findAll()));
  }

  public Mono<VarahamihirClientDetails> findByClientId(String clientId) {
    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              Tenant tenant
                      = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              if (tenant != null) {
                Optional<VarahamihirClientDetails> optionalVarahamihirClientDetails
                        = clientRepository.findByTenantAndClientId(tenant.getId(),clientId);
                if (optionalVarahamihirClientDetails.isPresent()) {

                  return Mono.defer(()->Mono.just(optionalVarahamihirClientDetails.get()));
                }
                return Mono.error(new UnauthorizedException(String.format("Client %s does not exist", clientId)));
              }
              return Mono.error(new UnauthorizedException(String.format("Tenant %s does not exist", clientId)));
            });

  }

  public Mono<VarahamihirClientDetails> create(VarahamihirClientDetails clientDetails) {

    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              Tenant tenant
                      = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              Optional<VarahamihirClientDetails> optionalVarahamihirClientDetails
                      = clientRepository.findByTenantAndClientId(tenant.getId(),
                      clientDetails.getClientId());
              if (optionalVarahamihirClientDetails.isPresent()) {

                return Mono.error(new EntityAlreadyExistsException(String.format("%s is not a valid client id.", clientDetails.getClientId())));
              }
              else {
                VarahamihirClientDetails storedClientDetails = optionalVarahamihirClientDetails.get();
                storedClientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
                storedClientDetails = clientRepository.save(storedClientDetails);
                return Mono.just(storedClientDetails);
              }
            });
  }

  public Mono<VarahamihirClientDetails> update(String clientId, VarahamihirClientDetails clientDetails) {

    return Mono.subscriberContext()
            .flatMap(tenantDiscriminatorContext -> {
              Tenant tenant
                      = tenantDiscriminatorContext.<Tenant>get(VarahamihirConstants.TENANT_IN_CONTEXT);
              if (tenant == null) {

                return Mono.error(new UnauthorizedException(String.format("Tenant %s does not exist", clientId)));
              }
              Optional<VarahamihirClientDetails> optionalOauthClientDetails
                      = clientRepository.findByTenantAndClientId(tenant.getId(), clientId);
              if (optionalOauthClientDetails.isPresent()) {

                VarahamihirClientDetails storedClientDetails = optionalOauthClientDetails.get();
                storedClientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
                storedClientDetails = clientRepository.save(storedClientDetails);
                return Mono.just(storedClientDetails);
              }
              return Mono.error(new UnauthorizedException(String.format("Client %s does not exist", clientId)));
            });
  }


  public long count() {
    return clientRepository.count();
  }

  public void save(VarahamihirClientDetails clientDetails) {
    clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
    clientRepository.save(clientDetails);
  }
}
