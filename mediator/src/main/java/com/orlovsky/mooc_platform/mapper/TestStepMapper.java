package com.orlovsky.mooc_platform.mapper;

import com.orlovsky.mooc_platform.dto.TestStepDTO;
import com.orlovsky.mooc_platform.model.TestStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface TestStepMapper {
    TestStepMapper INSTANCE = Mappers.getMapper(TestStepMapper.class);

    @Mapping(source = "descriptionUri",target = "descriptionUri")
    @Mapping(source = "answers",target = "answers")
    @Mapping(source = "score",target = "score")
    @Mapping(source = "position",target = "position")
    TestStepDTO toDto(TestStep testStep);

    @Mapping(source = "descriptionUri",target = "descriptionUri")
    @Mapping(source = "answers",target = "answers")
    @Mapping(source = "score",target = "score")
    @Mapping(source = "position",target = "position")
    TestStep toEntity(TestStepDTO testStepDTO);

    Collection<TestStep> toEntities(List<TestStepDTO> testStepDTOs);
    List<TestStepDTO> toDtos(Collection<TestStep> testSteps);

}


