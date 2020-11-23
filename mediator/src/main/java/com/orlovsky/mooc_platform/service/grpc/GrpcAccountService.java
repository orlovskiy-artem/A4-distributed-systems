package com.orlovsky.mooc_platform.service.grpc;

import com.google.protobuf.Empty;
import com.orlovsky.mooc_platform.dto.AuthorDTO;
import com.orlovsky.mooc_platform.dto.StudentDTO;
import com.orlovsky.mooc_platform.grpc.*;
import com.orlovsky.mooc_platform.service.AccountService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GRpcService
@Service
public class GrpcAccountService extends AccountServiceGrpc.AccountServiceImplBase {

    @Autowired
    private AccountService accountService;
    private final GrpcMapper grpcMapper = new GrpcMapper();
//    CRUD
//    Create
    @Override
    public void signUpStudent(StudentDto request, StreamObserver<Student> responseObserver) {
        StudentDTO studentDTO = grpcMapper.grpcToJavaStudentDto(request);
        com.orlovsky.mooc_platform.model.Student student = accountService.signUpStudent(studentDTO);
        Student response = grpcMapper.javaStudentToGrpcStudentBuilder(student).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void signUpAuthor(AuthorDto request, StreamObserver<Author> responseObserver) {
        AuthorDTO authorDTO = grpcMapper.grpcToJavaAuthorDto(request);
        com.orlovsky.mooc_platform.model.Author author = accountService.signUpAuthor(authorDTO);
        Author response = grpcMapper.javaAuthorToGrpcAuthorBuilder(author).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


//    Read
    @Override
    public void getAuthorById(UUID request, StreamObserver<Author> responseObserver) {
        com.orlovsky.mooc_platform.model.Author author = accountService.getAuthorById(
                grpcMapper.grpcToJavaUuid(request));
        Author response = grpcMapper.javaAuthorToGrpcAuthorBuilder(author).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentById(UUID request, StreamObserver<Student> responseObserver) {
        com.orlovsky.mooc_platform.model.Student student = accountService.getStudentById(
                grpcMapper.grpcToJavaUuid(request));
        Student response = grpcMapper.javaStudentToGrpcStudentBuilder(student).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAuthors(Empty request, StreamObserver<ListOfAuthors> responseObserver) {
        ListOfAuthors.Builder responseBuilder = ListOfAuthors.newBuilder();
        for (com.orlovsky.mooc_platform.model.Author author : accountService.getAllAuthors()) {
            responseBuilder.addValues(
                    grpcMapper.javaAuthorToGrpcAuthorBuilder(author).build()
            );
        }
        ListOfAuthors response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllStudents(Empty request, StreamObserver<ListOfStudents> responseObserver) {

        ListOfStudents.Builder responseBuilder = ListOfStudents.newBuilder();
        for (com.orlovsky.mooc_platform.model.Student student : accountService.getAllStudents()) {
            responseBuilder.addValues(
                    grpcMapper.javaStudentToGrpcStudentBuilder(student).build()
            );
        }
        ListOfStudents response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

//    Update
    @Override
    public void updateAuthor(AuthorUpdate request, StreamObserver<Empty> responseObserver) {
        accountService.updateAuthor(
                grpcMapper.grpcToJavaUuid(request.getAuthorId()),
                grpcMapper.grpcToJavaAuthorDto(request.getUpdateData()));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateStudent(StudentUpdate request, StreamObserver<Empty> responseObserver) {
        accountService.updateStudent(
                grpcMapper.grpcToJavaUuid(request.getStudentId()),
                grpcMapper.grpcToJavaStudentDto(request.getUpdateData()));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }


//    Delete
    @Override
    public void deleteAuthorById(UUID request, StreamObserver<Empty> responseObserver) {
        accountService.deleteAuthorById(grpcMapper.grpcToJavaUuid(request));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteStudentById(UUID request, StreamObserver<Empty> responseObserver) {
        accountService.deleteStudentById(grpcMapper.grpcToJavaUuid(request));
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}
