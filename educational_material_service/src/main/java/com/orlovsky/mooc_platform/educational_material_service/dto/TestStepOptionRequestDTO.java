package com.orlovsky.mooc_platform.educational_material_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestStepOptionRequestDTO {
    private UUID id;
    private String optionText;
    private boolean isCorrect;
}