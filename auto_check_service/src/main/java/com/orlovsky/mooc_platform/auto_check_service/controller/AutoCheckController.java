package com.orlovsky.mooc_platform.auto_check_service.controller;

import com.orlovsky.mooc_platform.auto_check_service.service.AutoCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AutoCheckController {
    @Autowired
    AutoCheckService autoCheckService;

    @PostMapping(value = "/test-step/{testStepId}/test-answer/{testAnswerId}")
    public ResponseEntity<Boolean> checkAnswer(@PathVariable(name = "testStepId") UUID testStepId,
                                               @PathVariable(name = "testAnswerId") UUID testAnswerId){
        Boolean isComplete = autoCheckService.checkTestStepTask(testStepId,testAnswerId);
        return new ResponseEntity<>(isComplete, HttpStatus.CREATED);
    }
}
