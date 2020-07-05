package com.avasthi.varahamihir.guardian.repositories;


import com.avasthi.varahamihir.guardian.entities.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuardianRepository extends JpaRepository<Guardian, UUID> {
}
