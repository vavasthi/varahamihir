package com.avasthi.varahamihir.identityserver.repositories;

import com.avasthi.varahamihir.identityserver.entities.Ingredient;
import com.avasthi.varahamihir.identityserver.entities.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface IngredientRepository extends MongoRepository<Ingredient, UUID> {
}
