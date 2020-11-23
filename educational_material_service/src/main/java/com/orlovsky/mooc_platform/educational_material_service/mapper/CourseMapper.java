package com.orlovsky.mooc_platform.educational_material_service.mapper;


import com.orlovsky.mooc_platform.educational_material_service.dto.CourseDTO;
import com.orlovsky.mooc_platform.educational_material_service.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
//    @Mapping(source = "duration",target = "duration")

    @Mapping(source = "title",target = "title")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "status",target = "status")
    @Mapping(source = "educationalSteps", target = "educationalSteps")
    @Mapping(source = "testSteps", target = "testSteps")
    @Mapping(source = "price",target = "price")
    @Mapping(source = "numberOfSteps", target = "numberOfSteps")
    CourseDTO toDto(Course course);

    @Mapping(source = "title",target = "title")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "status",target = "status")
    @Mapping(source = "educationalSteps", target = "educationalSteps")
    @Mapping(source = "testSteps", target = "testSteps")
    @Mapping(source = "price",target = "price")
    @Mapping(source = "numberOfSteps", target = "numberOfSteps")
    Course toEntity(CourseDTO courseDTO);

    List<CourseDTO> toDtos(List<Course> courses);
    List<Course> toEntities(List<CourseDTO> courseDtos);
}
