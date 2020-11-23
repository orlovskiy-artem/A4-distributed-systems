package com.orlovsky.mooc_platform.service.impl;

import com.orlovsky.mooc_platform.dto.CourseDTO;
import com.orlovsky.mooc_platform.dto.EducationalStepDTO;
import com.orlovsky.mooc_platform.dto.TestStepDTO;
import com.orlovsky.mooc_platform.dto.TestStepOptionRequestDTO;
import com.orlovsky.mooc_platform.model.*;
import com.orlovsky.mooc_platform.service.EducationalMaterialService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.MissingResourceException;
import java.util.UUID;

@Service
public class EducationalMaterialServiceImpl implements EducationalMaterialService {

    RestTemplate restTemplate = new RestTemplate();
    private static final String educationalMaterialServiceUrl = "http://educational-material-service:8100";

    // CRUD
    // Create
    @Override
    public Course createEmptyCourse(CourseDTO courseDTO) throws ResponseStatusException{
        HttpEntity<CourseDTO> request = new HttpEntity<>(courseDTO);
        ResponseEntity<Course> response = restTemplate.postForEntity(educationalMaterialServiceUrl + "/courses",request,Course.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    // Read
    @Override
    public Course getCourseById(UUID courseId) throws ResponseStatusException,MissingResourceException {
        ResponseEntity<Course> response = restTemplate.getForEntity(educationalMaterialServiceUrl + "/courses/"+courseId.toString(),Course.class);
        if(response.getStatusCode().value()== HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Course not found","Course",
                    courseId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    @Override
    public TestStep getTestStepById(UUID testStepId) throws ResponseStatusException,MissingResourceException {
        ResponseEntity<TestStep> response = restTemplate.getForEntity(educationalMaterialServiceUrl + "/test-steps/"+testStepId.toString(),TestStep.class);
        if(response.getStatusCode().value()== HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Test step not found",
                    "TestStep",
                    testStepId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    @Override
    public EducationalStep getEducationalStepById(UUID educationalStepId) throws ResponseStatusException,MissingResourceException {
        ResponseEntity<EducationalStep> response = restTemplate.getForEntity(educationalMaterialServiceUrl + "/educational-steps/"+educationalStepId.toString(),
                EducationalStep.class);
        if(response.getStatusCode().value()== HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Educational step not found",
                    "EducationalStep",
                    educationalStepId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    @Override
    public List<Course> getAllCourses() throws ResponseStatusException {
        ResponseEntity<List> response = restTemplate.getForEntity(educationalMaterialServiceUrl + "/courses",List.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }


    // Update
    @Override
    public void updateCourseInfo(UUID courseId,CourseDTO courseDTO) throws ResponseStatusException,MissingResourceException {
        HttpEntity<CourseDTO> request = new HttpEntity<>(courseDTO);
        ResponseEntity<Void> response = restTemplate.exchange(educationalMaterialServiceUrl + "/courses/"+courseId.toString(),
                HttpMethod.PUT,request, Void.class);
        if(response.getStatusCode().value()== HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
    }

    @Override
    public EducationalStep addEducationalStep(UUID courseId, EducationalStepDTO educationalStepDTO) throws ResponseStatusException,MissingResourceException {
        HttpEntity<EducationalStepDTO> request = new HttpEntity<>(educationalStepDTO);
        ResponseEntity<EducationalStep> response = restTemplate.exchange(educationalMaterialServiceUrl + "/courses/"+courseId.toString()+
                "/steps/educational-steps",
                HttpMethod.POST,request, EducationalStep.class);
        if(response.getStatusCode().value()== HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Course has not been found","Course",courseId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    @Override
    public TestStep addTestStep(UUID courseId,
                            TestStepDTO testStepDTO) throws ResponseStatusException,MissingResourceException {
        HttpEntity<TestStepDTO> request = new HttpEntity<>(testStepDTO);
        ResponseEntity<TestStep> response = restTemplate.exchange(educationalMaterialServiceUrl + "/courses/"+courseId.toString()+
                        "/steps/test-steps",
                HttpMethod.POST,request, TestStep.class);
        if(response.getStatusCode().value()== HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Course has not been found","Course",courseId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    @Override
    public TestStepOption addTestStepOption(UUID courseId,
                                            UUID testStepId,
                                            TestStepOptionRequestDTO testStepOptionRequestDTO) throws ResponseStatusException {
        HttpEntity<TestStepOptionRequestDTO> request = new HttpEntity<>(testStepOptionRequestDTO);
        ResponseEntity<TestStepOption> response = restTemplate.exchange(educationalMaterialServiceUrl + "/courses/"+courseId.toString()+
                        "/steps/test-steps/"+testStepId.toString()+"/answers",
                HttpMethod.POST,request, TestStepOption.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    @Override
    public void addAuthor(UUID courseId, UUID authorId) throws ResponseStatusException {
        HttpEntity<String> request =
                new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.postForEntity(educationalMaterialServiceUrl+"/courses/"+courseId.toString()+
                "/authors/"+authorId.toString(),request,Void.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Educational material service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
    }

    // Delete
    @Override
    public void deleteCourse(UUID courseId) {
        restTemplate.delete(educationalMaterialServiceUrl + "/courses/"+courseId.toString());
    }

    @Override
    public void deleteTestStep(UUID courseId,
                               UUID testStepId) {
        restTemplate.delete(educationalMaterialServiceUrl + "/courses/"+courseId.toString()+"/steps/test-steps/"+testStepId.toString());
    }

    @Override
    public void deleteTestAnswer(UUID testStepId, UUID testAnswerId) {
        restTemplate.delete(educationalMaterialServiceUrl +
            "/test-steps/"+testStepId.toString()+"/answers/"+testAnswerId.toString());
    }

    @Override
    public void deleteAuthorById(UUID courseId, UUID authorId) throws MissingResourceException {
        restTemplate.delete(educationalMaterialServiceUrl + "/courses/"+courseId.toString()+"/authors/"+authorId.toString());
    }

    @Override
    public void deleteEducationalStep(UUID courseId,
                                      UUID educationalStepId) throws MissingResourceException {
        restTemplate.delete(educationalMaterialServiceUrl + "/courses/"+courseId.toString()+"/steps/educational-steps/"+educationalStepId.toString());


    }
}
