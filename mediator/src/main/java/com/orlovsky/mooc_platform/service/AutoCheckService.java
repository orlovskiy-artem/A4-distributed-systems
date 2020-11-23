package com.orlovsky.mooc_platform.service;

import com.orlovsky.mooc_platform.model.TestStep;
import com.orlovsky.mooc_platform.model.TestStepOption;
import org.springframework.web.server.ResponseStatusException;

public interface AutoCheckService {
    Boolean checkTestTask(TestStep testStep,
                             TestStepOption chosenTestAnswer) throws ResponseStatusException;
}
