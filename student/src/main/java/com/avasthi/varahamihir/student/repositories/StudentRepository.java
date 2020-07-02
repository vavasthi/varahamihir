package com.avasthi.varahamihir.student.repositories;


import com.avasthi.varahamihir.student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
