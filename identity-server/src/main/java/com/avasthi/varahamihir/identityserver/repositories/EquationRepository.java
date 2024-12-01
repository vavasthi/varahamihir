package com.avasthi.varahamihir.identityserver.repositories;

import com.avasthi.varahamihir.identityserver.entities.Equation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EquationRepository extends MongoRepository<Equation, UUID> {
}
