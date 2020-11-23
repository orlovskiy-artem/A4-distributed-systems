package com.orlovsky.mooc_platform.acccount_service.services;

import com.orlovsky.mooc_platform.acccount_service.dto.AuthorDTO;
import com.orlovsky.mooc_platform.acccount_service.dto.StudentDTO;
import com.orlovsky.mooc_platform.acccount_service.model.Author;
import com.orlovsky.mooc_platform.acccount_service.model.Student;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    Student signUpStudent(StudentDTO studentDTO);

    Author signUpAuthor(AuthorDTO authorDTO);

    Student getStudentById(UUID studentId);

    Author getAuthorById(UUID authorId);

    List<Student> getAllStudents();

    List<Author> getAllAuthors();

    void updateStudent(UUID studentId,StudentDTO studentDTO);

    void updateAuthor(UUID authorId, AuthorDTO authorDTO);

    void deleteStudentById(UUID studentId);

    void deleteAuthorById(UUID authorId);


}
