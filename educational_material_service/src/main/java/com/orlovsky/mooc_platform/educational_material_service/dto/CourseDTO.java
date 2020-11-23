package com.orlovsky.mooc_platform.educational_material_service.dto;

import com.orlovsky.mooc_platform.educational_material_service.model.CourseStatus;
import com.orlovsky.mooc_platform.educational_material_service.model.EducationalStep;
import com.orlovsky.mooc_platform.educational_material_service.model.TestStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private UUID id;
    private String title;
    private String description;
    private int duration;
    private CourseStatus status;
    private long price;
    private List<EducationalStep> educationalSteps;
    private List<TestStep> testSteps;
    private int numberOfSteps;
}
