package com.avasthi.varahamihir.identityserver.repositories;

import com.avasthi.varahamihir.identityserver.entities.Content;
import com.avasthi.varahamihir.identityserver.entities.Embed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EmbedRepository extends MongoRepository<Embed, UUID> {

}
