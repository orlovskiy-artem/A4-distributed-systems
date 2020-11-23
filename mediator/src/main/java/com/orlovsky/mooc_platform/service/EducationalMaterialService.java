package com.orlovsky.mooc_platform.service;


import com.orlovsky.mooc_platform.dto.CourseDTO;
import com.orlovsky.mooc_platform.dto.EducationalStepDTO;
import com.orlovsky.mooc_platform.dto.TestStepDTO;
import com.orlovsky.mooc_platform.dto.TestStepOptionRequestDTO;
import com.orlovsky.mooc_platform.model.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

public interface EducationalMaterialService {
    // CRUD
    // Create
    Course createEmptyCourse(CourseDTO courseDTO) throws ResponseStatusException;

    // Read
    Course getCourseById(UUID courseId) throws ResponseStatusException;

    List<Course> getAllCourses() throws ResponseStatusException;

    TestStep getTestStepById(UUID testStepId) throws ResponseStatusException;

    EducationalStep getEducationalStepById(UUID educationalStepId) throws ResponseStatusException;

    // Update
    void updateCourseInfo(UUID courseId,
                          CourseDTO courseDTO) throws ResponseStatusException;

    void addAuthor(UUID courseId, UUID authorId) throws ResponseStatusException;

    EducationalStep addEducationalStep(UUID courseId,
                            EducationalStepDTO educationalStepDTO) throws ResponseStatusException;

    TestStep addTestStep(UUID courseId,
                     TestStepDTO testStepDTO) throws ResponseStatusException;

    TestStepOption addTestStepOption(UUID courseId, UUID testStepId, TestStepOptionRequestDTO body) throws ResponseStatusException;

    // Delete
    void deleteEducationalStep(UUID courseId,
                               UUID educationalStepId);

    void deleteTestStep(UUID courseId,
                        UUID testStepId);

    void deleteTestAnswer(UUID testStepId, UUID testAnswerId);

    void deleteAuthorById(UUID courseId, UUID authorId);

    void deleteCourse(UUID courseId);
}
