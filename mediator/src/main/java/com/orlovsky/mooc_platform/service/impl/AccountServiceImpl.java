package com.orlovsky.mooc_platform.service.impl;


import com.google.gson.Gson;
import com.orlovsky.mooc_platform.dto.AuthorDTO;
import com.orlovsky.mooc_platform.dto.StudentDTO;
import com.orlovsky.mooc_platform.model.Author;
import com.orlovsky.mooc_platform.model.Student;
import com.orlovsky.mooc_platform.service.AccountService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.MissingResourceException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    RestTemplate restTemplate = new RestTemplate();
    private static final String accountServiceUrl = "http://account-service:8090";
    //    Line for debug mode outside kubernetes
//    private static final String accountServiceUrl = "http://172.17.0.2:30163";
    // CRUD
    // Create
    @Override
    public Student signUpStudent(StudentDTO studentDTO) throws ResponseStatusException {
        HttpEntity<StudentDTO> request = new HttpEntity<>(studentDTO);
        ResponseEntity<Student> response = restTemplate.postForEntity(accountServiceUrl + "/students",request,Student.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Account service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    @Override
    public Author signUpAuthor(AuthorDTO authorDTO) throws ResponseStatusException {
        HttpEntity<AuthorDTO> request = new HttpEntity<>(authorDTO);
        ResponseEntity<Author> response = restTemplate.postForEntity(accountServiceUrl + "/authors",request,Author.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Account service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    // Read
    @Override
    public Student getStudentById(UUID studentId) throws ResponseStatusException,MissingResourceException {
        ResponseEntity<Student> response = restTemplate.getForEntity(accountServiceUrl + "/students/"+studentId.toString(),Student.class);
        if(response.getStatusCode().value()==HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Student is not found","Student",
                    studentId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Account service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    @Override
    public Author getAuthorById(UUID authorId) throws ResponseStatusException,MissingResourceException {
        ResponseEntity<Author> response = restTemplate.getForEntity(accountServiceUrl + "/authors/"+authorId.toString(),Author.class);
        if(response.getStatusCode().value()==HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Author is not found","Author",
                    authorId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Account service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return response.getBody();
    }

    @Override
    public List<com.orlovsky.mooc_platform.model.Student> getAllStudents() throws ResponseStatusException {
        ResponseEntity<List> response = restTemplate.getForEntity(accountServiceUrl + "/students",List.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Account service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return (List<Student>) response.getBody().stream().map(k->
                    {Gson g = new Gson();
                     Student student = g.fromJson(k.toString(),Student.class);
                     return student;})
                .collect(Collectors.toList());
    }

    @Override
    public List<Author> getAllAuthors() throws ResponseStatusException {
        ResponseEntity<List> response = restTemplate.getForEntity(accountServiceUrl + "/authors",List.class);
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Account service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
        return (List<Author>) response.getBody().stream().map(k->
                    {Gson g = new Gson();
                    Author author = g.fromJson(k.toString(),Author.class);
                    return author;})
                .collect(Collectors.toList());
    }

    //Update
    @Override
    public void updateStudent(UUID studentId, StudentDTO studentDTO) throws ResponseStatusException,MissingResourceException {
        HttpEntity<StudentDTO> requestUpdate = new HttpEntity<>(studentDTO);
        ResponseEntity response = restTemplate.exchange(accountServiceUrl + "/students/"+studentId.toString(),
                HttpMethod.PUT,requestUpdate, Void.class);
        if(response.getStatusCode().value()==HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Student is not found","Student",
                    studentId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Account service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
    }

    @Override
    public void updateAuthor(UUID authorId, AuthorDTO authorDTO) throws ResponseStatusException,MissingResourceException {
        HttpEntity<AuthorDTO> requestUpdate = new HttpEntity<>(authorDTO);
        ResponseEntity response = restTemplate.exchange(accountServiceUrl + "/authors/"+authorId.toString(),
                HttpMethod.PUT,requestUpdate, Void.class);
        if(response.getStatusCode().value()==HttpStatus.NOT_FOUND.value()){
            throw new MissingResourceException("Author is not found","Author",
                    authorId.toString());
        }
        if(response.getStatusCode().is5xxServerError()) throw new ResponseStatusException(response.getStatusCode(),"Account service error");
        if(response.getStatusCode().is4xxClientError()) throw new ResponseStatusException(response.getStatusCode(),"Mediator has failed");
    }

    //Delete
    @Override
    public void deleteStudentById(UUID studentId){
        restTemplate.delete(accountServiceUrl + "/students/"+studentId.toString());
    }

    @Override
    public void deleteAuthorById(UUID authorId) {
        restTemplate.delete(accountServiceUrl + "/authors/"+authorId.toString());
    }
}

