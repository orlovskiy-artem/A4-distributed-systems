package com.orlovsky.mooc_platform.service;

import com.orlovsky.mooc_platform.dto.AuthorDTO;
import com.orlovsky.mooc_platform.dto.StudentDTO;
import com.orlovsky.mooc_platform.model.Student;
import com.orlovsky.mooc_platform.model.Author;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.MissingResourceException;
import java.util.UUID;

public interface AccountService {
    Student signUpStudent(StudentDTO studentDTO) throws ResponseStatusException;

    Author signUpAuthor(AuthorDTO authorDTO) throws ResponseStatusException;

    Student getStudentById(UUID studentId) throws ResponseStatusException, MissingResourceException;

    Author getAuthorById(UUID authorId) throws  ResponseStatusException, MissingResourceException;

    List<Student> getAllStudents()  throws ResponseStatusException;

    List<Author> getAllAuthors()  throws ResponseStatusException;

    void updateStudent(UUID studentId,StudentDTO studentDTO) throws ResponseStatusException, MissingResourceException;

    void updateAuthor(UUID authorId, AuthorDTO authorDTO) throws ResponseStatusException, MissingResourceException;

    void deleteStudentById(UUID studentId);

    void deleteAuthorById(UUID authorId);


}
