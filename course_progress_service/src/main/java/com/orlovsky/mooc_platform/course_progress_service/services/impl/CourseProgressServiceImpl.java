package com.orlovsky.mooc_platform.course_progress_service.services.impl;

import com.orlovsky.mooc_platform.course_progress_service.dto.TestStepOptionDTO;
import com.orlovsky.mooc_platform.course_progress_service.model.CourseStudent;
import com.orlovsky.mooc_platform.course_progress_service.model.StudentProgressItem;
import com.orlovsky.mooc_platform.course_progress_service.repository.CourseStudentRepository;
import com.orlovsky.mooc_platform.course_progress_service.repository.StudentProgressItemRepository;
import com.orlovsky.mooc_platform.course_progress_service.services.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.MissingResourceException;
import java.util.UUID;

@Service
public class CourseProgressServiceImpl implements CourseProgressService {

    @Autowired
    private StudentProgressItemRepository studentProgressItemRepository;
    @Autowired
    private CourseStudentRepository courseStudentRepository;

    RestTemplate restTemplate = new RestTemplate();
    private static final String accountServiceUrl = "http://account-service:8090";
    private static final String educationalMaterialServiceUrl = "http://educational-material-service:8100";
    private static final String autoCheckServiceUrl = "http://auto-check-service:8120";

    @Override
    public void signUpUser(UUID courseId,
                           UUID studentId) throws MissingResourceException {
        checkIfCourseExists(courseId); // throws exception
        checkIfStudentExists(studentId); // throws exception
        courseStudentRepository.save(new CourseStudent(courseId,studentId));
    }

    @Override
    public void makePassedEducationalStep(UUID courseId,
                                          UUID studentId,
                                          UUID educationalStepId) throws MissingResourceException {
        checkIfCourseExists(courseId);
        checkIfStudentExists(studentId);
        checkIfEducationalStepExists(courseId,educationalStepId);
        StudentProgressItem studentProgressItem = StudentProgressItem.builder()
                .studentId(studentId)
                .courseId(courseId)
                .passedEducationalStepId(educationalStepId)
                .passedTestStepId(null)
                .chosenOptionId(null).build();
        studentProgressItemRepository.save(studentProgressItem);
    }



    @Override
    public Boolean makeProcessedTestStep(UUID courseId,
                                                            UUID studentId,
                                                            UUID testStepId,
                                                            TestStepOptionDTO chosenAnswer) throws Exception {

        checkIfCourseExists(courseId);
        checkIfStudentExists(studentId);
        checkIfTestStepExists(courseId,testStepId);
        checkIfTestStepOptionExists(testStepId,chosenAnswer.getId());
        ResponseEntity<Boolean> autoCheckerResponse = restTemplate.getForEntity(autoCheckServiceUrl+
                "/test-step/"+testStepId.toString()+"/test-answer/"+chosenAnswer.getId(),Boolean.class);
        if(autoCheckerResponse.getStatusCode().is5xxServerError()){
            throw new Exception("Auto-checker is not available now.");
        }
        if(!autoCheckerResponse.getStatusCode().is2xxSuccessful()){
            throw new Exception("Something went wrong, sorry.");
        }
        if(autoCheckerResponse.getBody().booleanValue()){
            StudentProgressItem studentProgressItem = StudentProgressItem.builder()
                    .studentId(studentId)
                    .courseId(courseId)
                    .passedEducationalStepId(null)
                    .passedTestStepId(testStepId)
                    .chosenOptionId(chosenAnswer.getId()).build();
            studentProgressItemRepository.save(studentProgressItem);
        }
        return autoCheckerResponse.getBody();
    }

    @Override
    public void checkIfStudentExists(UUID studentId) throws MissingResourceException {
        ResponseEntity<String> responseIfStudentExists = restTemplate.getForEntity(accountServiceUrl + "/students" + "/" + studentId.toString(),String.class);
        if(!responseIfStudentExists.getStatusCode().is2xxSuccessful()){
            throw new MissingResourceException("Student not found",
                    "Student",
                    studentId.toString());
        }
    }

    @Override
    public void checkIfCourseExists(UUID courseId) throws MissingResourceException {
        ResponseEntity<String> responseIfCourseExists = restTemplate.getForEntity(educationalMaterialServiceUrl +"/courses" + "/" + courseId.toString(),String.class);
        if(!responseIfCourseExists.getStatusCode().is2xxSuccessful()){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
    }

    @Override
    public void checkIfTestStepExists(UUID courseId, UUID testStepId) throws MissingResourceException {
        ResponseEntity<String> responseIfTestStepExists =
                restTemplate.getForEntity(educationalMaterialServiceUrl +"/courses/" + courseId.toString()+
                        "/steps/test-steps/" + testStepId.toString(),String.class);
        if(!responseIfTestStepExists.getStatusCode().is2xxSuccessful()){
            throw new MissingResourceException("Test step not found",
                    "Test step",
                    testStepId.toString());
        }
    }

    @Override
    public void checkIfTestStepOptionExists(UUID testStepId, UUID testStepOptionId) throws MissingResourceException {
        ResponseEntity<String> responseIfTestStepExists =
                restTemplate.getForEntity(educationalMaterialServiceUrl+
                "/test-steps/" + testStepId.toString()+"/answer/"+testStepOptionId.toString(),String.class);
        if(!responseIfTestStepExists.getStatusCode().is2xxSuccessful()){
            throw new MissingResourceException("Test step option not found",
                    "Test step option",
                    testStepOptionId.toString());
        }
    }

    @Override
    public void checkIfEducationalStepExists(UUID courseId, UUID educationalStepId) throws MissingResourceException {
        ResponseEntity<String> responseIfEducationalStepExists =
                restTemplate.getForEntity(educationalMaterialServiceUrl +"/courses/" + courseId.toString()+
                        "/steps/educational-steps/" + educationalStepId.toString(),String.class);
        if(!responseIfEducationalStepExists.getStatusCode().is2xxSuccessful()){
            throw new MissingResourceException("Educational step not found",
                    "Educational step",
                    educationalStepId.toString());
        }
    }



}
