package com.orlovsky.mooc_platform.mapper;

import com.orlovsky.mooc_platform.dto.AuthorDTO;
import com.orlovsky.mooc_platform.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "description", target = "description")
    AuthorDTO toDto(Author author);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "description", target = "description")
    Author toEntity(AuthorDTO authorDTO);

    List<AuthorDTO> toDtos(List<Author> authors);
    List<Author> toEntities(List<AuthorDTO> authorDTOs);
}
