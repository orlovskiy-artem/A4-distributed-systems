package com.orlovsky.mooc_platform.service.impl;


import com.orlovsky.mooc_platform.model.TestStep;
import com.orlovsky.mooc_platform.model.TestStepOption;
import com.orlovsky.mooc_platform.service.AutoCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AutoCheckServiceImpl implements AutoCheckService {
    RestTemplate restTemplate = new RestTemplate();
    private static final String autoCheckerUrl = "http://auto-check-service:8120";


    @Override
    public Boolean checkTestTask(TestStep testStep, TestStepOption chosenTestAnswer) throws ResponseStatusException {
        ResponseEntity<Boolean> response = restTemplate.getForEntity(autoCheckerUrl + "/test-step/"+
                testStep.getId().toString()+"/test-answer/"+chosenTestAnswer.getId().toString(), Boolean.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Auto-checker error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }
}
