package com.orlovsky.mooc_platform.acccount_service.controller;

import com.orlovsky.mooc_platform.acccount_service.dto.AuthorDTO;
import com.orlovsky.mooc_platform.acccount_service.dto.StudentDTO;
import com.orlovsky.mooc_platform.acccount_service.mapper.AuthorMapper;
import com.orlovsky.mooc_platform.acccount_service.mapper.StudentMapper;
import com.orlovsky.mooc_platform.acccount_service.model.Author;
import com.orlovsky.mooc_platform.acccount_service.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.orlovsky.mooc_platform.acccount_service.services.AccountService;

import java.util.List;
import java.util.MissingResourceException;
import java.util.UUID;

@RestController
public class AccountController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private AccountService accountService;

    // CRUD for students
    @PostMapping(value = "/students")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO body){
        Student student = accountService.signUpStudent(body);
        return new ResponseEntity<>(student,HttpStatus.CREATED);
    }

    @GetMapping(value = "/students")
    public ResponseEntity<?> getAllStudents(){
        List<StudentDTO> studentDTOs = StudentMapper.INSTANCE.
                toDtos(accountService.getAllStudents());
        return new ResponseEntity<>(studentDTOs,HttpStatus.OK);
    }

    @GetMapping(value = "/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable(name = "id") UUID id){
        try{
            Student student = accountService.getStudentById(id);
            StudentDTO body = StudentMapper.INSTANCE.toDto(student);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/students/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable(name = "id") UUID id,
                                               @RequestBody StudentDTO body) {
        try{
            accountService.updateStudent(id,body);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/students/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable(name = "id") UUID id){
        accountService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // CRUD for authors
    @PostMapping(value = "/authors")
    public ResponseEntity<?> createAuthor(@RequestBody AuthorDTO body){
        Author author = accountService.signUpAuthor(body);
        return new ResponseEntity<>(author,HttpStatus.CREATED);
    }

    @GetMapping(value = "/authors")
    public ResponseEntity<?> getAllAuthors(){
        List<AuthorDTO> authorDTOs = AuthorMapper.INSTANCE.
                toDtos(accountService.getAllAuthors());
        return new ResponseEntity<>(authorDTOs,HttpStatus.OK);
    }

    @GetMapping(value = "/authors/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable(name = "id") UUID id){
        try{
            Author author = accountService.getAuthorById(id);
            AuthorDTO body = AuthorMapper.INSTANCE.toDto(author);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/authors/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable(name = "id") UUID id,
                                          @RequestBody AuthorDTO body){
        try{
            accountService.updateAuthor(id,body);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/authors/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable(name = "id") UUID id){
        accountService.deleteAuthorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
