package com.orlovsky.mooc_platform.service.impl;


import com.orlovsky.mooc_platform.dto.TestStepOptionDTO;
import com.orlovsky.mooc_platform.service.CourseProgressService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class CourseProgressServiceImpl implements CourseProgressService {
    RestTemplate restTemplate = new RestTemplate();
    private static final String courseProgressServiceUrl = "http://course-progress-service:8110";

    @Override
    public void signUpUser(UUID courseId,
                           UUID studentId) throws ResponseStatusException {
        HttpEntity<String> request = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.postForEntity(courseProgressServiceUrl+"/progress/"+courseId.toString()+
                "/students/"+studentId.toString(),request,Void.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Course progress service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
    }

    @Override
    public void makePassedEducationalStep(UUID courseId,
                                          UUID studentId,
                                          UUID educationalStepId) throws ResponseStatusException {
        HttpEntity<String> request = new HttpEntity<>(null);
        ResponseEntity<Void> response = restTemplate.postForEntity(courseProgressServiceUrl+"/progress/"+courseId.toString()+
                "/students/"+studentId.toString()+"/steps/educational-steps/"+educationalStepId.toString(),request,Void.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Course progress service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
    }

    @Override
    public Boolean makeProcessedTestStep(UUID courseId,
                                      UUID studentId,
                                      UUID testStepId,
                                      TestStepOptionDTO chosenAnswer) throws ResponseStatusException {
        HttpEntity<TestStepOptionDTO> request = new HttpEntity<>(chosenAnswer);
        ResponseEntity<Boolean> response = restTemplate.postForEntity(courseProgressServiceUrl+"/progress/"+courseId.toString()+
                "/students/"+studentId.toString()+"/steps/test-steps/"+testStepId.toString(),request,Boolean.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Course progress service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }


}
