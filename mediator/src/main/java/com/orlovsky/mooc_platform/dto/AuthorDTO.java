package com.orlovsky.mooc_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String description;
}
