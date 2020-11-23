package com.orlovsky.mooc_platform.auto_check_service.service.impl;

import com.orlovsky.mooc_platform.auto_check_service.dto.TestStepOptionDTO;
import com.orlovsky.mooc_platform.auto_check_service.service.AutoCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.MissingResourceException;
import java.util.UUID;

@Service
public class AutoCheckServiceImpl implements AutoCheckService {

    RestTemplate restTemplate = new RestTemplate();
    private static final String educationalMaterialServiceUrl = "http://educational-material-service:8100";

    @Override
    public Boolean checkTestStepTask(UUID testStepId,
                                     UUID chosenTestAnswerId) {
        ResponseEntity<TestStepOptionDTO> response =
                restTemplate.getForEntity(
            educationalMaterialServiceUrl + "/test-steps/"+
                    testStepId.toString()+"/answers/"+chosenTestAnswerId.toString(),
                        TestStepOptionDTO.class);
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new MissingResourceException("Test step option not found",
                    "Test step option",
                    chosenTestAnswerId.toString());
        }
        TestStepOptionDTO testStepOptionWithIsCorrectFlag = response.getBody();
        return testStepOptionWithIsCorrectFlag.isCorrect();
    }
}
