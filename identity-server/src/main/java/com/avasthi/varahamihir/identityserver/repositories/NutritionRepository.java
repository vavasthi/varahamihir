package com.avasthi.varahamihir.identityserver.repositories;

import com.avasthi.varahamihir.identityserver.entities.Nutrition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface NutritionRepository extends MongoRepository<Nutrition, UUID> {
    @Query("{'itemId':?0}")
    Optional<Nutrition> findByItemId(UUID itemId);
}
