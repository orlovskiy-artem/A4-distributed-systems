package com.orlovsky.mooc_platform.service.grpc;

import com.orlovsky.mooc_platform.dto.*;
import com.orlovsky.mooc_platform.grpc.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Custom class to convert grpc types to java models and vice versa
 * Just handmade utilities.
 */
public class GrpcMapper {
//    UUID
    public java.util.UUID grpcToJavaUuid(UUID uuid){
        System.out.println(uuid.getValue());
        return java.util.UUID.fromString(uuid.getValue());
    }

    public UUID javaToGrpcUuid(java.util.UUID uuid) {
        if(uuid == null){
            return UUID.newBuilder().setValue("null").build();
        }
        return UUID.newBuilder().setValue(uuid.toString()).build();
    }

//    Student
    public Student.Builder javaStudentToGrpcStudentBuilder(com.orlovsky.mooc_platform.model.Student student){
        return Student.newBuilder()
                .setId(UUID.newBuilder().setValue(student.getId().toString()))
                .setFirstName(student.getFirstName())
                .setLastName(student.getLastName())
                .setDescription(student.getDescription());
    }

    public StudentDTO grpcToJavaStudentDto(StudentDto studentDto) {
        return new StudentDTO(
                null,
                studentDto.getFirstName(),
                studentDto.getLastName(),
                studentDto.getDescription()
        );
    }

//    Author
    public Author.Builder javaAuthorToGrpcAuthorBuilder(com.orlovsky.mooc_platform.model.Author author){
        return Author.newBuilder()
                .setId(UUID.newBuilder().setValue(author.getId().toString()).build())
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName())
                .setDescription(author.getDescription());
    }

    public AuthorDTO grpcToJavaAuthorDto(AuthorDto authorDto) {
        return new AuthorDTO(
                null,
                authorDto.getFirstName(),
                authorDto.getLastName(),
                authorDto.getDescription()
        );
    }

    public com.orlovsky.mooc_platform.model.Author grpcToJavaAuthor(Author author){
        return new com.orlovsky.mooc_platform.model.Author(
                java.util.UUID.fromString(author.getId().getValue()),
                author.getFirstName(),
                author.getLastName(),
                author.getDescription(),
                new ArrayList<com.orlovsky.mooc_platform.model.Course>()
        );
    }

//    CourseStatus
    public com.orlovsky.mooc_platform.model.CourseStatus grpcToJavaCourseStatus(CourseStatus courseStatus){
        return com.orlovsky.mooc_platform.model.CourseStatus.values()[courseStatus.getNumber()];
    }

    public CourseStatus javaToGrpcCourseStatus(com.orlovsky.mooc_platform.model.CourseStatus courseStatus){
        return CourseStatus.forNumber(courseStatus.ordinal());
    }

//    Course
    public Course.Builder javaCourseToGrpcCourseBuilder(com.orlovsky.mooc_platform.model.Course course){
        return Course.newBuilder()
                .setId(javaToGrpcUuid(course.getId()))
                .setTitle(course.getTitle())
                .setDescription(course.getDescription())
                .setDuration(course.getDuration())
                .setStatus(javaToGrpcCourseStatus(course.getStatus()))
                .setNumberOfSteps(course.getNumberOfSteps())
                .setPrice(course.getPrice())
                .addAllAuthors(course.getAuthors().stream()
                        .map(author ->
                                javaAuthorToGrpcAuthorBuilder(author).build())
                        .collect(Collectors.toList()))
                .addAllEducationalSteps(
                        course.getEducationalSteps()
                                .stream()
                                .map(educationalStep -> javaEducationalStepToGrpcEducationalStepBuilder(educationalStep).build())
                                .collect(Collectors.toList())
                ).addAllTestSteps(
                        course.getTestSteps()
                                .stream()
                                .map(testStep -> javaTestStepToGrpcTestStepBuilder(testStep).build())
                                .collect(Collectors.toList())
                );
    }

    public CourseDTO grpcToJavaCourseDtoCreateAction(CourseDto courseDto){
        return new CourseDTO(
                null,
                courseDto.getTitle(),
                courseDto.getDescription(),
                courseDto.getAuthorsList().stream()
                        .map(this::grpcToJavaAuthor)
                        .collect(Collectors.toList()),
                courseDto.getDuration(),
                grpcToJavaCourseStatus(courseDto.getStatus()),
                courseDto.getPrice(),
                new ArrayList<com.orlovsky.mooc_platform.model.EducationalStep>(),
                new ArrayList<com.orlovsky.mooc_platform.model.TestStep>(),
                0);
    }

    public CourseDTO grpcToJavaCourseDtoUpdateAction(CourseDto courseDto) {
        return new CourseDTO(
                null,
                courseDto.getTitle(),
                courseDto.getDescription(),
                courseDto.getAuthorsList().stream()
                        .map(this::grpcToJavaAuthor)
                        .collect(Collectors.toList()),
                courseDto.getDuration(),
                grpcToJavaCourseStatus(courseDto.getStatus()),
                courseDto.getPrice(),
                courseDto.getEducationalStepsList()
                        .stream()
                        .map(this::grpcToJavaEducationalStep)
                        .collect(Collectors.toList()),
                courseDto.getTestStepsList()
                        .stream()
                        .map(this::grpcToJavaTestStep)
                        .collect(Collectors.toList()),
                courseDto.getNumberOfSteps());
    }

//    Educational Step
    public EducationalStep.Builder javaEducationalStepToGrpcEducationalStepBuilder(com.orlovsky.mooc_platform.model.EducationalStep educationalStep) {
        return EducationalStep.newBuilder()
                .setId(javaToGrpcUuid(educationalStep.getId()))
                .setEduMaterialUri(educationalStep.getEduMaterialUri().toString())
                .setPosition(educationalStep.getPosition());
    }

    public com.orlovsky.mooc_platform.model.EducationalStep grpcToJavaEducationalStep(EducationalStep educationalStep){
        return new com.orlovsky.mooc_platform.model.EducationalStep(
                grpcToJavaUuid(educationalStep.getId()),
                null,
                URI.create(educationalStep.getEduMaterialUri()),
                educationalStep.getPosition(),
                null
        );
    }

    public EducationalStepDTO grpcToJavaEducationalStepDto(EducationalStepDto educationalStepDto) {
        return new EducationalStepDTO(
                grpcToJavaUuid(educationalStepDto.getId()),
                grpcToJavaUuid(educationalStepDto.getCourseId()),
                URI.create(educationalStepDto.getEduMaterialUri()),
                educationalStepDto.getPosition()
        );
    }

    public EducationalStepDTO grpcToJavaEducationalStepDtoAdd(EducationalStepDto educationalStepDto) {
        return new EducationalStepDTO(
                null,
                null,
                URI.create(educationalStepDto.getEduMaterialUri()),
                educationalStepDto.getPosition()
        );
    }

    //    TestStep
    public TestStep.Builder javaTestStepToGrpcTestStepBuilder(com.orlovsky.mooc_platform.model.TestStep testStep){
        return TestStep.newBuilder()
                .setId(javaToGrpcUuid(testStep.getId()))
                .setDescriptionUri(testStep.getDescriptionUri().toString())
                .setPosition(testStep.getPosition())
                .setScore(testStep.getScore())
                .addAllAnswers(testStep.getAnswers()
                        .stream()
                        .map(
                        testStepOption ->
                                javaTestStepOptionToGrpcTestStepOptionBuilder(testStepOption).build())
                        .collect(Collectors.toList()));
    }

    private UUID javaToGrpcUuidCourse(com.orlovsky.mooc_platform.model.Course course) {
        if(course==null){return null;}
        return javaToGrpcUuid(course.getId());

    }

    public com.orlovsky.mooc_platform.model.TestStep grpcToJavaTestStep(TestStep testStep) {
        return new com.orlovsky.mooc_platform.model.TestStep(
                grpcToJavaUuid(testStep.getId()),
                null,
                URI.create(testStep.getDescriptionUri()),
                testStep.getAnswersList()
                        .stream()
                        .map(this::grpcToJavaTestStepOption)
                        .collect(Collectors.toList()),
                testStep.getScore(),
                testStep.getPosition(),
                null
        );
    }

    public TestStepDTO grpcToJavaTestStepDtoAdd(TestStepDto testStepDto) {
        return new TestStepDTO(
                null,
                null,
                URI.create(testStepDto.getDescriptionUri()),
                new ArrayList<TestStepOptionDTO>(),
                testStepDto.getScore(),
                testStepDto.getPosition()
        );
    }

    public TestStepDTO grpcToJavaTestStepDto(TestStepDto testStepDto) {
        return new TestStepDTO(
                grpcToJavaUuid(testStepDto.getId()),
                grpcToJavaUuid(testStepDto.getCourseId()),
                URI.create(testStepDto.getDescriptionUri()),
                testStepDto.getAnswersList()
                        .stream()
                        .map(this::grpcToJavaTestStepOptionDto)
                        .collect(Collectors.toList()),
                testStepDto.getScore(),
                testStepDto.getPosition()
        );
    }

//    TestStepOption
    public TestStepOption.Builder javaTestStepOptionToGrpcTestStepOptionBuilderAdd(com.orlovsky.mooc_platform.model.TestStepOption testStepOption) {
        return TestStepOption.newBuilder()
                .setId(javaToGrpcUuid(null))
                .setOptionText(testStepOption.getOptionText())
                .setIsCorrect(testStepOption.isCorrect());
    }
    public TestStepOption.Builder javaTestStepOptionToGrpcTestStepOptionBuilder(com.orlovsky.mooc_platform.model.TestStepOption testStepOption) {
        return TestStepOption.newBuilder()
                .setId(javaToGrpcUuid(testStepOption.getId()))
                .setOptionText(testStepOption.getOptionText())
                .setIsCorrect(testStepOption.isCorrect());
    }

    public com.orlovsky.mooc_platform.model.TestStepOption grpcToJavaTestStepOption(TestStepOption testStepOption) {
        return new com.orlovsky.mooc_platform.model.TestStepOption(
                grpcToJavaUuid(testStepOption.getId()),
                testStepOption.getOptionText(),
                testStepOption.getIsCorrect(),
                null);
    }

    public TestStepOptionDTO grpcToJavaTestStepOptionDto(TestStepOptionDto testStepOptionDto) {
        return new TestStepOptionDTO(
                grpcToJavaUuid(testStepOptionDto.getId()),
                testStepOptionDto.getOptionText()
        );
    }

    public TestStepOptionDTO grpcToJavaTestStepOptionDtoAdd(TestStepOptionDto testStepOptionDto) {
        return new TestStepOptionDTO(
                null,
                testStepOptionDto.getOptionText()
        );
    }

    public TestStepOptionRequestDTO grpcToJavaTestStepOptionRequestDto(TestStepOptionRequestDto testStepOptionRequestDto) {
        return new TestStepOptionRequestDTO(
                grpcToJavaUuid(testStepOptionRequestDto.getId()),
                testStepOptionRequestDto.getOptionText(),
                testStepOptionRequestDto.getIsCorrect()
        );
    }

    public TestStepOptionRequestDTO grpcToJavaTestStepOptionRequestDtoAdd(TestStepOptionRequestDto testStepOptionRequestDto) {
        return new TestStepOptionRequestDTO(
                null,
                testStepOptionRequestDto.getOptionText(),
                testStepOptionRequestDto.getIsCorrect()
        );
    }
    
}
//    //    Mappers (convertors)
//    public java.util.UUID grpcToJavaUuid(UUID uuid){
//        return java.util.UUID.fromString(uuid.getValue());
//    }
//    public UUID javaToGrpcUuid(java.util.UUID uuid) {
//        return UUID.newBuilder().setValue(uuid.toString()).build();
//    }
//
//    public Student.Builder javaStudentToGrpcStudentBuilder(com.orlovsky.mooc_platform.model.Student student){
//        return Student.newBuilder()
//                .setId(UUID.newBuilder().setValue(student.getId().toString()))
//                .setFirstName(student.getFirstName())
//                .setLastName(student.getLastName())
//                .setDescription(student.getDescription());
//    }
//    public Author.Builder javaAuthorToGrpcAuthorBuilder(com.orlovsky.mooc_platform.model.Author author){
//        return Author.newBuilder()
//                .setId(UUID.newBuilder().setValue(author.getId().toString()).build())
//                .setFirstName(author.getFirstName())
//                .setLastName(author.getLastName())
//                .setDescription(author.getDescription());
//    }
//
//    public StudentDTO grpcToJavaStudentDto(StudentDto studentDto) {
//        return new StudentDTO(
//                null,
//                studentDto.getFirstName(),
//                studentDto.getLastName(),
//                studentDto.getDescription()
//        );
//    }
//    public AuthorDTO grpcToJavaAuthorDto(AuthorDto authorDto) {
//        return new AuthorDTO(
//                null,
//                authorDto.getFirstName(),
//                authorDto.getLastName(),
//                authorDto.getDescription()
//        );
//    }