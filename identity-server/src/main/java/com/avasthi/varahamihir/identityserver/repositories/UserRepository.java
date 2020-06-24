package com.avasthi.varahamihir.identityserver.repositories;

import com.avasthi.varahamihir.identityserver.entities.Tenant;
import com.avasthi.varahamihir.identityserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u where u.email=:email")
    Optional<User> findUserByEmail(@Param("email") String email);
    @Query("SELECT u FROM User u where u.email=:emailOrUsername or u.username = :emailOrUsername")
    Optional<User> findUserByEmailOrUsername(@Param("emailOrUsername") String emailOrUsername);
    @Query("SELECT u FROM User u where u.username=:username and u.tenant = :tenant")
    Optional<User> findUserByUsernameAndTenant(@Param("tenant") Tenant tenant,
                                               @Param("username") String username);
    @Query("SELECT u FROM User u where u.authToken=:authToken")
    Optional<User> findUserByAuthToken(@Param("authToken") String AuthToken);
    @Query("SELECT u FROM User u where u.tenant=:tenant and (u.username = :emailOrUsername or u.email = :emailOrUsername)")
    Optional<User> findUserByTenantAndUsername(@Param("tenant") Tenant tenant,
                                               @Param("emailOrUsername") String emailOrUsername);
}
