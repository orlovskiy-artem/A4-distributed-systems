package com.orlovsky.mooc_platform.educational_material_service.mapper;

import com.orlovsky.mooc_platform.educational_material_service.dto.TestStepOptionRequestDTO;
import com.orlovsky.mooc_platform.educational_material_service.model.TestStepOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface TestStepOptionRequestMapper {
    TestStepOptionRequestMapper INSTANCE = Mappers.getMapper(TestStepOptionRequestMapper.class);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "optionText",target = "optionText")
    @Mapping(source = "correct",target = "correct")
    TestStepOptionRequestDTO toDto(TestStepOption testStepOption);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "optionText",target = "optionText")
    @Mapping(source = "correct",target = "correct")
    TestStepOption toEntity(TestStepOptionRequestDTO testStepOptionRequestDTO);

    Collection<TestStepOption> toEntities(List<TestStepOptionRequestDTO> testStepOptionDTOs);
    List<TestStepOptionRequestDTO> toDtos(Collection<TestStepOption> testStepOptions);
}
