package com.orlovsky.mooc_platform.educational_material_service.mapper;

import com.orlovsky.mooc_platform.educational_material_service.dto.TestStepOptionDTO;
import com.orlovsky.mooc_platform.educational_material_service.model.TestStepOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface TestStepOptionMapper {
    TestStepOptionMapper INSTANCE = Mappers.getMapper(TestStepOptionMapper.class);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "optionText",target = "optionText")
    TestStepOptionDTO toDto(TestStepOption testStepOption);

    @Mapping(source = "id",target = "id")
    @Mapping(source = "optionText",target = "optionText")
    TestStepOption toEntity(TestStepOptionDTO testStepOptionDTO);

    Collection<TestStepOption> toEntities(List<TestStepOptionDTO> testStepOptionDTOs);
    List<TestStepOptionDTO> toDtos(Collection<TestStepOption> testStepOptions);
}
