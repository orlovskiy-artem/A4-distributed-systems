package com.orlovsky.mooc_platform.course_progress_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestStepOptionDTO {
    private UUID id;
    private String optionText;
}
