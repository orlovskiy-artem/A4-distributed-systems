package com.orlovsky.mooc_platform.acccount_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String description;
}
