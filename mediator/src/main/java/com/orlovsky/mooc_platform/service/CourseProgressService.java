package com.orlovsky.mooc_platform.service;

import com.orlovsky.mooc_platform.dto.TestStepOptionDTO;
import com.orlovsky.mooc_platform.model.Course;
import com.orlovsky.mooc_platform.model.Student;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public interface CourseProgressService {
    void signUpUser(UUID courseId,
                    UUID studentId) throws ResponseStatusException;

    void makePassedEducationalStep(UUID courseId,
                                   UUID studentId,
                                   UUID educationalStepId) throws ResponseStatusException;

    Boolean makeProcessedTestStep(UUID courseId,
                               UUID studentId,
                               UUID testStepId,
                               TestStepOptionDTO chosenAnswer) throws ResponseStatusException;


}
