package com.orlovsky.mooc_platform.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentProgressItem {
    private UUID id;
    private Student student;
    private Course course;
    private EducationalStep passedEducationalStep;
    private TestStep passedTestStep;
    private TestStepOption chosenOption;
}