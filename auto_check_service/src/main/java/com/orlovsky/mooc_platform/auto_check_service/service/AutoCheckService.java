package com.orlovsky.mooc_platform.auto_check_service.service;

import java.util.UUID;

public interface AutoCheckService {
    Boolean checkTestStepTask(UUID testStepId,
                             UUID chosenTestAnswerId);
}
