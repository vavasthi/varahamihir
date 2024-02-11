package com.avasthi.varahamihir.identityserver.repositories;

import com.avasthi.varahamihir.identityserver.entities.Nutrition;
import com.avasthi.varahamihir.identityserver.entities.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UnitRepository extends MongoRepository<Unit, UUID> {
}
