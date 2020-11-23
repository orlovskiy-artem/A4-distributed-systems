package com.orlovsky.mooc_platform.mapper;

import com.orlovsky.mooc_platform.dto.EducationalStepDTO;
import com.orlovsky.mooc_platform.model.EducationalStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface StudentAnswerRequestMapper {
    StudentAnswerRequestMapper INSTANCE = Mappers.getMapper(StudentAnswerRequestMapper.class);

    @Mapping(source = "eduMaterialUri", target = "eduMaterialUri")
    @Mapping(source = "position",target = "position")
    EducationalStepDTO toDto(EducationalStep educationalStep);

    @Mapping(source = "eduMaterialUri", target = "eduMaterialUri")
    @Mapping(source = "position",target = "position")
    EducationalStep toEntity(EducationalStepDTO educationalStepDTO);

    List<EducationalStepDTO> toDtos(Collection<EducationalStep> educationalStepList);
    Collection<EducationalStep> toEntities(List<EducationalStepDTO> educationalStepDTOList);

}
