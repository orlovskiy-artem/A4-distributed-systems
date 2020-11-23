package com.orlovsky.mooc_platform.acccount_service.repository;

import com.orlovsky.mooc_platform.acccount_service.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
}
