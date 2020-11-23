package com.orlovsky.mooc_platform.acccount_service.mapper;

import com.orlovsky.mooc_platform.acccount_service.dto.StudentDTO;
import com.orlovsky.mooc_platform.acccount_service.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "description", target = "description")
    StudentDTO toDto(Student student);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "description", target = "description")
    Student toEntity(StudentDTO studentDTO);

    List<StudentDTO> toDtos(List<Student> students);
    List<Student> toEntities(List<StudentDTO> studentDTOs);
}
