package com.avasthi.varahamihir.identityserver.repositories;

import com.avasthi.varahamihir.identityserver.entities.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;


public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    @Query("SELECT t FROM Tenant t where t.discriminator = :discriminator")
    Optional<Tenant> findTenantsByDiscriminator(@Param("discriminator") String discriminator);
}
