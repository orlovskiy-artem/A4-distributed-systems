package com.orlovsky.mooc_platform.service.grpc;

//import io.github.lognet

import com.google.protobuf.Descriptors;
import com.google.protobuf.Empty;
import com.orlovsky.mooc_platform.dto.AuthorDTO;
import com.orlovsky.mooc_platform.dto.StudentDTO;
import com.orlovsky.mooc_platform.grpc.*;
import com.orlovsky.mooc_platform.service.AccountService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@GRpcService
@Service
public class GrpcAccountService extends AccountServiceGrpc.AccountServiceImplBase {

    @Autowired
    private AccountService accountService;

//    CRUD
//    Create
    @Override
    public void signUpStudent(StudentDto request, StreamObserver<Student> responseObserver) {
        StudentDTO studentDTO = grpcToJavaStudentDto(request);
        com.orlovsky.mooc_platform.model.Student student = accountService.signUpStudent(studentDTO);
        Student.Builder response = javaStudentToGrpcStudentBuilder(student);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void signUpAuthor(AuthorDto request, StreamObserver<Author> responseObserver) {
        AuthorDTO authorDTO = grpcToJavaAuthorDto(request);
        com.orlovsky.mooc_platform.model.Author author = accountService.signUpAuthor(authorDTO);
        Author response = javaToGrpcAuthor(author);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


//    Read
    @Override
    public void getAuthorById(UUID request, StreamObserver<Author> responseObserver) {
        com.orlovsky.mooc_platform.model.Author author = accountService.getAuthorById(grpcToJavaUuid(request));
        Author response = javaToGrpcAuthor(author);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentById(UUID request, StreamObserver<Student> responseObserver) {
        com.orlovsky.mooc_platform.model.Student student = accountService.getStudentById(grpcToJavaUuid(request));
        Student.Builder response = javaStudentToGrpcStudentBuilder(student);
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAuthors(Empty request, StreamObserver<ListOfAuthors> responseObserver) {
        ListOfAuthors.Builder responseBuilder = ListOfAuthors.newBuilder();
        for (com.orlovsky.mooc_platform.model.Author author : accountService.getAllAuthors()) {
            responseBuilder.addValues(javaToGrpcAuthor(author));
        }
        ListOfAuthors response = responseBuilder.build();
//        ListOfAuthors response = ListOfAuthors
//                .newBuilder()
//                .addAllValues(
//                        .stream()
//                        .map(k->javaToGrpcAuthor(k))
//                        .collect(Collectors.toList()))
//                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllStudents(Empty request, StreamObserver<ListOfStudents> responseObserver) {

        ListOfStudents.Builder responseBuilder = ListOfStudents.newBuilder();
        for (com.orlovsky.mooc_platform.model.Student student : accountService.getAllStudents()) {
            System.out.println(student.toString());
            System.out.println(student.getClass().toString());
            responseBuilder.addValues(
                    Student.newBuilder()
                            .setId(UUID.newBuilder().setValue(student.getId().toString()))
                            .setFirstName(student.getFirstName())
                            .setLastName(student.getLastName())
                            .setDescription(student.getDescription())
            );
        }
//        for
//        ListOfStudents listOfStudents = ListOfStudents.newBuilder().addAllValues(studentList);

//        ListOfStudents response = ListOfStudents
//                .newBuilder()
//                .addAllValues(accountService
//                        .getAllStudents()
//                        .stream()
//                        .map(k->javaStudentToGrpcStudentBuilder(k).build())
//                        .collect(Collectors.toList()))
//                .build();
        ListOfStudents response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

//    Update


    @Override
    public void updateAuthor(AuthorUpdate request, StreamObserver<Empty> responseObserver) {
        accountService.updateAuthor(
                    grpcToJavaUuid(request.getAuthorId()),
                    grpcToJavaAuthorDto(request.getUpdateData()));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateStudent(StudentUpdate request, StreamObserver<Empty> responseObserver) {
        accountService.updateStudent(
                grpcToJavaUuid(request.getStudentId()),
                grpcToJavaStudentDto(request.getUpdateData()));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }


//    Delete
    @Override
    public void deleteAuthorById(UUID request, StreamObserver<Empty> responseObserver) {
        accountService.deleteAuthorById(grpcToJavaUuid(request));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteStudentById(UUID request, StreamObserver<Empty> responseObserver) {
        accountService.deleteStudentById(grpcToJavaUuid(request));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    //    Mappers (convertors)
    public java.util.UUID grpcToJavaUuid(UUID uuid){
        return java.util.UUID.fromString(uuid.getValue());
    }
    public UUID javaToGrpcUuid(java.util.UUID uuid) {
        return UUID.newBuilder().setValue(uuid.toString()).build();
    }

    public Student.Builder javaStudentToGrpcStudentBuilder(com.orlovsky.mooc_platform.model.Student student){
        return Student.newBuilder()
                .setId(UUID.newBuilder().setValue(student.getId().toString()))
                .setFirstName(student.getFirstName())
                .setLastName(student.getLastName())
                .setDescription(student.getDescription());
    }
    public Author javaToGrpcAuthor(com.orlovsky.mooc_platform.model.Author author){
        return Author.newBuilder()
                .setId(UUID.newBuilder().setValue(author.getId().toString()).build())
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName())
                .setDescription(author.getDescription())
                .build();
    }

    public StudentDTO grpcToJavaStudentDto(StudentDto studentDto) {
        return new StudentDTO(
                null,
                studentDto.getFirstName(),
                studentDto.getLastName(),
                studentDto.getDescription()
        );
    }
    public AuthorDTO grpcToJavaAuthorDto(AuthorDto authorDto) {
        return new AuthorDTO(
                null,
                authorDto.getFirstName(),
                authorDto.getLastName(),
                authorDto.getDescription()
        );
    }

}
