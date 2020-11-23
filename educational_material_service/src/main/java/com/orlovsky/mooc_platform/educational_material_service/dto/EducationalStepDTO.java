package com.orlovsky.mooc_platform.educational_material_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationalStepDTO {
    private UUID id;
    private UUID courseId;
    private URI eduMaterialUri;
    private int position;
}
