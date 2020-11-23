package com.orlovsky.mooc_platform.controller;

import com.orlovsky.mooc_platform.dto.AuthorDTO;
import com.orlovsky.mooc_platform.dto.StudentDTO;
import com.orlovsky.mooc_platform.mapper.AuthorMapper;
import com.orlovsky.mooc_platform.mapper.StudentMapper;
import com.orlovsky.mooc_platform.model.Author;
import com.orlovsky.mooc_platform.model.Student;
import com.orlovsky.mooc_platform.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.MissingResourceException;
import java.util.UUID;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    // CRUD for students
    @PostMapping(value = "/students")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDTO body){
        try {
            Student student = accountService.signUpStudent(body);
            return new ResponseEntity<>(student,HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus());
        }
    }

    @GetMapping(value = "/students")
    public ResponseEntity<?> getAllStudents(){
        try{
//            List<StudentDTO> studentDTOs = StudentMapper.INSTANCE.
//                   toDtos();
            return new ResponseEntity<>(accountService.getAllStudents(),HttpStatus.OK);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus());
        }
    }

    @GetMapping(value = "/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable(name = "id") UUID id){
        try{
           Student student = accountService.getStudentById(id);
           StudentDTO body = StudentMapper.INSTANCE.toDto(student);
           return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus());
        }
    }

    @PutMapping(value = "/students/{id}")
    public ResponseEntity<?> updateStudentById(@PathVariable(name = "id") UUID id,
                                               @RequestBody StudentDTO body) {
        try{
            accountService.updateStudent(id,body);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus());
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
        try{
            Author author = accountService.signUpAuthor(body);
            return new ResponseEntity<>(author,HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus());
        }
    }

    @GetMapping(value = "/authors")
    public ResponseEntity<?> getAllAuthors(){
        try{
//            List<AuthorDTO> authorDTOs = AuthorMapper.INSTANCE.
//                    toDtos();
            return new ResponseEntity<>(accountService.getAllAuthors(),HttpStatus.OK);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus());
        }
    }

    @GetMapping(value = "/authors/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable(name = "id") UUID id){
        try{
            Author author = accountService.getAuthorById(id);
            AuthorDTO body = AuthorMapper.INSTANCE.toDto(author);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus());
        }
    }

    @PutMapping(value = "/authors/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable(name = "id") UUID id,
                                           @RequestBody AuthorDTO body){
        try{
            accountService.updateAuthor(id,body);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ResponseStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getStatus());
        }
    }

    @DeleteMapping(value = "/authors/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable(name = "id") UUID id){
        accountService.deleteAuthorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
