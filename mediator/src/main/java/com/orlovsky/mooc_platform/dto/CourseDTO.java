package com.orlovsky.mooc_platform.dto;

import com.orlovsky.mooc_platform.model.Author;
import com.orlovsky.mooc_platform.model.CourseStatus;
import com.orlovsky.mooc_platform.model.EducationalStep;
import com.orlovsky.mooc_platform.model.TestStep;
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
    private List<Author> authors;
    private int duration;
    private CourseStatus status;
    private long price;
    private List<EducationalStep> educationalSteps;
    private List<TestStep> testSteps;
    private int numberOfSteps;
}
