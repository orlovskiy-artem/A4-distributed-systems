package com.orlovsky.mooc_platform.educational_material_service.mapper;

import com.orlovsky.mooc_platform.educational_material_service.dto.EducationalStepDTO;
import com.orlovsky.mooc_platform.educational_material_service.model.EducationalStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface EducationalStepMapper {
    EducationalStepMapper INSTANCE = Mappers.getMapper(EducationalStepMapper.class);

    @Mapping(source = "eduMaterialUri", target = "eduMaterialUri")
    @Mapping(source = "position",target = "position")
    EducationalStepDTO toDto(EducationalStep educationalStep);

    @Mapping(source = "eduMaterialUri", target = "eduMaterialUri")
    @Mapping(source = "position",target = "position")
    EducationalStep toEntity(EducationalStepDTO educationalStepDTO);

    List<EducationalStepDTO> toDtos(Collection<EducationalStep> educationalStepList);
    Collection<EducationalStep> toEntities(List<EducationalStepDTO> educationalStepDTOList);
}
