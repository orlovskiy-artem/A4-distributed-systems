package com.orlovsky.mooc_platform.service.grpc;

import com.google.protobuf.Empty;
import com.orlovsky.mooc_platform.dto.TestStepOptionDTO;
import com.orlovsky.mooc_platform.grpc.*;
import com.orlovsky.mooc_platform.service.CourseProgressService;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@GRpcService
@Service
public class GrpcCourseProgressService extends CourseProgressServiceGrpc.CourseProgressServiceImplBase {


    @Autowired
    CourseProgressService courseProgressService;
    private final GrpcMapper grpcMapper = new GrpcMapper();

    @Override
    public void signUpUser(SignUpUserRequest request, StreamObserver<Empty> responseObserver) {
        UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        UUID studentId = grpcMapper.grpcToJavaUuid(request.getStudentId());
        courseProgressService.signUpUser(courseId,studentId);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void makePassedEducationalStep(MakePassedEducationalStepRequest request, StreamObserver<Empty> responseObserver) {
        UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        UUID studentId = grpcMapper.grpcToJavaUuid(request.getStudentId());
        UUID educationalStepId = grpcMapper.grpcToJavaUuid(request.getEducationalStepId());
        courseProgressService.makePassedEducationalStep(
                courseId,
                studentId,
                educationalStepId);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void makeProcessedTestStep(MakeProcessedTestStepRequest request, StreamObserver<MakeProcessedTestStepResponse> responseObserver) {
        UUID courseId = grpcMapper.grpcToJavaUuid(request.getCourseId());
        UUID studentId = grpcMapper.grpcToJavaUuid(request.getStudentId());
        UUID testStepId = grpcMapper.grpcToJavaUuid(request.getTestStepId());
        TestStepOptionDTO testStepOptionDTO = grpcMapper.grpcToJavaTestStepOptionDto(request.getChosenAnswer());
        Boolean correct = courseProgressService.makeProcessedTestStep(
                courseId,
                studentId,
                testStepId,
                testStepOptionDTO);
        MakeProcessedTestStepResponse response = grpcMapper.convertToGetMakeProcessedTestStepResponse(correct);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



}
