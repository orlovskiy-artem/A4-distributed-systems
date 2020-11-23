package com.orlovsky.mooc_platform.educational_material_service.repository;

import com.orlovsky.mooc_platform.educational_material_service.model.TestStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestStepRepository extends JpaRepository<TestStep, UUID> {
}
