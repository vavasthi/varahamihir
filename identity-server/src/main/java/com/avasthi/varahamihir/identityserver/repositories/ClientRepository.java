package com.avasthi.varahamihir.identityserver.repositories;


import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<VarahamihirClientDetails, UUID> {
  @Query("SELECT cd FROM VarahamihirClientDetails cd where cd.tenantId = :tenantId and cd.clientId = :clientId")
  Optional<VarahamihirClientDetails> findByTenantAndClientId(@Param("tenantId") UUID tenantId,
                                                             @Param("clientId") String clientId);
}
