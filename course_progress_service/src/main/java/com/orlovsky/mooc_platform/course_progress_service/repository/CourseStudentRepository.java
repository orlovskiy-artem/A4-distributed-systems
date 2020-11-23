package com.orlovsky.mooc_platform.course_progress_service.repository;

import com.orlovsky.mooc_platform.course_progress_service.model.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, UUID> {
}
