package com.orlovsky.mooc_platform.acccount_service.services.impl;

import com.orlovsky.mooc_platform.acccount_service.model.Author;
import com.orlovsky.mooc_platform.acccount_service.model.Student;
import com.orlovsky.mooc_platform.acccount_service.dto.AuthorDTO;
import com.orlovsky.mooc_platform.acccount_service.dto.StudentDTO;
import com.orlovsky.mooc_platform.acccount_service.mapper.AuthorMapper;
import com.orlovsky.mooc_platform.acccount_service.mapper.StudentMapper;
import com.orlovsky.mooc_platform.acccount_service.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.orlovsky.mooc_platform.acccount_service.repository.AuthorRepository;
import com.orlovsky.mooc_platform.acccount_service.repository.StudentRepository;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AuthorRepository authorRepository;

    // CRUD
    // Create
    @Override
    public Student signUpStudent(StudentDTO studentDTO) {
        Student student = StudentMapper.INSTANCE.toEntity(studentDTO);
        System.out.println(studentDTO);
        System.out.println(student);
        return studentRepository.save(student);
    }

    @Override
    public Author signUpAuthor(AuthorDTO authorDTO) {
        Author author = AuthorMapper.INSTANCE.toEntity(authorDTO);
        return authorRepository.save(author);
    }

    // Read
    @Override
    public Student getStudentById(UUID studentId) throws MissingResourceException {
        return studentRepository.findById(studentId).orElseThrow(() ->
                new MissingResourceException("Student not found",
                        "Student",
                        studentId.toString()));
    }

    @Override
    public Author getAuthorById(UUID authorId) throws MissingResourceException{
        return authorRepository.findById(authorId).orElseThrow(() ->
                new MissingResourceException("Author not found",
                        "Author",
                        authorId.toString()));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    //Update
    @Override
    public void updateStudent(UUID studentId, StudentDTO studentDTO) throws MissingResourceException{
        if(!studentRepository.existsById(studentId)){
            throw new MissingResourceException("Student not found","Student",studentId.toString());
        }
        Student student = studentRepository.getOne(studentId);
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setDescription(studentDTO.getDescription());
        studentRepository.save(student);
    }

    @Override
    public void updateAuthor(UUID authorId, AuthorDTO authorDTO) throws MissingResourceException {
        if(!authorRepository.existsById(authorId)){
            throw new MissingResourceException("Author not found","Author",authorId.toString());
        }
        Author author = authorRepository.getOne(authorId);
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setDescription(authorDTO.getDescription());
        authorRepository.save(author);
    }

    //Delete
    @Override
    public void deleteStudentById(UUID studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public void deleteAuthorById(UUID authorId) {
        authorRepository.deleteById(authorId);
    }
}

