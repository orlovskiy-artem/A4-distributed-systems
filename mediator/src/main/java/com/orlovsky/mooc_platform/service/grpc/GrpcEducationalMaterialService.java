package com.orlovsky.mooc_platform.service.grpc;

import com.google.protobuf.Empty;
import com.orlovsky.mooc_platform.dto.CourseDTO;
import com.orlovsky.mooc_platform.dto.EducationalStepDTO;
import com.orlovsky.mooc_platform.dto.TestStepDTO;
import com.orlovsky.mooc_platform.dto.TestStepOptionRequestDTO;
import com.orlovsky.mooc_platform.grpc.*;
import com.orlovsky.mooc_platform.service.EducationalMaterialService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@GRpcService
@Service
public class GrpcEducationalMaterialService extends EducationalMaterialServiceGrpc.EducationalMaterialServiceImplBase {
    @Autowired
    private EducationalMaterialService educationalMaterialService;

    private final GrpcMapper grpcMapper = new GrpcMapper();

//    Create
    @Override
    public void createEmptyCourse(CourseDto request, StreamObserver<Course> responseObserver) {
        CourseDTO courseDTO = grpcMapper.grpcToJavaCourseDtoCreateAction(request);
        com.orlovsky.mooc_platform.model.Course course =
                educationalMaterialService.createEmptyCourse(courseDTO);
        Course response = grpcMapper.javaCourseToGrpcCourseBuilder(course).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

//    Read
    @Override
    public void getCourseById(UUID request, StreamObserver<Course> responseObserver) {
        java.util.UUID courseUuid = grpcMapper.grpcToJavaUuid(request);
        com.orlovsky.mooc_platform.model.Course course =
                educationalMaterialService.getCourseById(courseUuid);
        Course response = grpcMapper.javaCourseToGrpcCourseBuilder(course).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllCourses(Empty request, StreamObserver<ListOfCourses> responseObserver) {
        List<Course> courses = educationalMaterialService.
                getAllCourses()
                .stream()
                .map(course -> grpcMapper.javaCourseToGrpcCourseBuilder(course).build())
                .collect(Collectors.toList());
        ListOfCourses response = ListOfCourses
                .newBuilder()
                .addAllCourses(courses)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getEducationalStepById(GetEducationalStepByIdRequest request, StreamObserver<EducationalStep> responseObserver) {
        java.util.UUID educationalStepUuid = grpcMapper.grpcToJavaUuid(request.getEducationalStepId());
        com.orlovsky.mooc_platform.model.EducationalStep educationalStep =
                educationalMaterialService.getEducationalStepById(educationalStepUuid);
        EducationalStep response =
                grpcMapper.javaEducationalStepToGrpcEducationalStepBuilder(educationalStep)
                        .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTestStepById(GetTestStepByIdRequest request, StreamObserver<TestStep> responseObserver) {
        java.util.UUID testStepUuid = grpcMapper.grpcToJavaUuid(request.getTestStepId());
        com.orlovsky.mooc_platform.model.TestStep testStep =
                educationalMaterialService.getTestStepById(testStepUuid);
        TestStep response =
                grpcMapper.javaTestStepToGrpcTestStepBuilder(testStep)
                        .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


//    Update

    @Override
    public void updateCourseInfo(CourseUpdate request, StreamObserver<Empty> responseObserver) {
        CourseDTO courseDTO = grpcMapper.grpcToJavaCourseDtoUpdateAction(request.getCourseDto());
        java.util.UUID courseUuid = grpcMapper.grpcToJavaUuid(request.getCourseId());
        educationalMaterialService.updateCourseInfo(courseUuid,courseDTO);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void addAuthor(AddAuthorOnCourseRequest request, StreamObserver<Empty> responseObserver) {
        java.util.UUID authorId = grpcMapper.grpcToJavaUuid(request.getAuthorId());
        java.util.UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        educationalMaterialService.addAuthor(courseId,authorId);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void addEducationalStep(AddEducationalStepOnCourseRequest request, StreamObserver<EducationalStep> responseObserver) {
        java.util.UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        EducationalStepDTO educationalStepDTO = grpcMapper.grpcToJavaEducationalStepDtoAdd(request.getEducationalStepDto());
        com.orlovsky.mooc_platform.model.EducationalStep educationalStep = educationalMaterialService.addEducationalStep(courseId,educationalStepDTO);
        EducationalStep response = grpcMapper
                .javaEducationalStepToGrpcEducationalStepBuilder(educationalStep)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addTestStep(AddTestStepOnCourseRequest request, StreamObserver<TestStep> responseObserver) {
        java.util.UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        TestStepDTO testStepDTO = grpcMapper.grpcToJavaTestStepDtoAdd(
                request.getTestStepDto()
        );
        com.orlovsky.mooc_platform.model.TestStep testStep = educationalMaterialService
                .addTestStep(courseId,testStepDTO);
        TestStep response = grpcMapper
                .javaTestStepToGrpcTestStepBuilder(testStep)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void addTestStepOption(AddTestStepOptionOnCourseRequest request, StreamObserver<TestStepOption> responseObserver) {
        java.util.UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        java.util.UUID testStepId =grpcMapper.grpcToJavaUuid(request.getTestStepId());
        TestStepOptionRequestDTO testStepOptionRequestDTO =
                grpcMapper.grpcToJavaTestStepOptionRequestDtoAdd(request.getTestStepOptionRequestDto());
        com.orlovsky.mooc_platform.model.TestStepOption testStepOption =
                educationalMaterialService.addTestStepOption(courseId,testStepId,testStepOptionRequestDTO);
        TestStepOption response = grpcMapper.javaTestStepOptionToGrpcTestStepOptionBuilder(testStepOption).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
//    Delete

    @Override
    public void deleteAuthorById(DeleteAuthorOnCourseRequest request, StreamObserver<Empty> responseObserver) {
        java.util.UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        java.util.UUID authorId = grpcMapper.grpcToJavaUuid(request.getAuthorId());
        educationalMaterialService.deleteAuthorById(courseId,authorId);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCourse(DeleteCourseRequest request, StreamObserver<Empty> responseObserver) {
        java.util.UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        educationalMaterialService.deleteCourse(courseId);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }


    @Override
    public void deleteEducationalStep(DeleteEducationalStepOnCourseRequest request, StreamObserver<Empty> responseObserver) {
        java.util.UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        java.util.UUID educationalStepId = grpcMapper.grpcToJavaUuid(request.getEducationalStepId());
        educationalMaterialService.deleteEducationalStep(courseId,educationalStepId);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTestStep(DeleteTestStepOnCourseRequest request, StreamObserver<Empty> responseObserver) {
        java.util.UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        java.util.UUID testStepId = grpcMapper.grpcToJavaUuid(request.getTestStepId());
        educationalMaterialService.deleteTestStep(courseId,testStepId);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTestAnswer(DeleteTestStepOptionOnCourseRequest request, StreamObserver<Empty> responseObserver) {
        java.util.UUID testStepId = grpcMapper.grpcToJavaUuid(request.getTestStepId());
        java.util.UUID testStepOptionId = grpcMapper.grpcToJavaUuid(request.getTestStepOptionId());
        educationalMaterialService.deleteTestAnswer(testStepId,testStepOptionId);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

}
