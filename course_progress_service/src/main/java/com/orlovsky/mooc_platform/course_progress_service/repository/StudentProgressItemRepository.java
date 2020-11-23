package com.orlovsky.mooc_platform.course_progress_service.repository;


import com.orlovsky.mooc_platform.course_progress_service.model.StudentProgressItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentProgressItemRepository extends JpaRepository<StudentProgressItem, UUID> {
}
