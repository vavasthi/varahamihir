package com.avasthi.varahamihir.identityserver.repositories;

import com.avasthi.varahamihir.identityserver.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, UUID> {
    @Query("{'username':?0}")
    Optional<UserDetails> findByUsername(String username);
    @Query("{'$or':[{'username':?0}, {'email':?0}]}")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
}
