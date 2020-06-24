package com.avasthi.varahamihir.identityserver.repositories;


import com.avasthi.varahamihir.identityserver.entities.VarahamihirClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<VarahamihirClientDetails, String> {
}
