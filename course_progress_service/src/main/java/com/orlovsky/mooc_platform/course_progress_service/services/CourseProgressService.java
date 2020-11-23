package com.orlovsky.mooc_platform.course_progress_service.services;

import com.orlovsky.mooc_platform.course_progress_service.dto.TestStepOptionDTO;

import java.util.MissingResourceException;
import java.util.UUID;

public interface CourseProgressService {
    void signUpUser(UUID courseId,
                    UUID studentId);

    void makePassedEducationalStep(UUID courseId,
                                   UUID studentId,
                                   UUID educationalStepId);

    Boolean makeProcessedTestStep(UUID courseId,
                               UUID studentId,
                               UUID testStepId,
                               TestStepOptionDTO chosenAnswer) throws Exception;


    void checkIfStudentExists(UUID studentId) throws MissingResourceException;

    void checkIfCourseExists(UUID courseId) throws MissingResourceException;

    void checkIfTestStepExists(UUID courseId, UUID testStepId) throws MissingResourceException;

    void checkIfTestStepOptionExists(UUID testStepId, UUID testStepOptionId) throws MissingResourceException;

    void checkIfEducationalStepExists(UUID courseId, UUID educationalStepId) throws MissingResourceException;
}
