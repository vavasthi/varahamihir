package com.avasthi.varahamihir.identityserver.repositories;

import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u where u.email=:email")
    Optional<User> findUserByEmail(@Param("email") String email);
    @Query("SELECT u FROM User u where u.email=:emailOrUsername or u.username = :emailOrUsername")
    Optional<User> findUserByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
    @Query("SELECT u FROM User u where u.tenantId=:tenantId and (u.username = :emailOrUsername or u.email = :emailOrUsername)")
    Optional<User> findUserByTenantAndUsername(@Param("tenantId") UUID tenantId,
                                               @Param("emailOrUsername") String emailOrUsername);
}
