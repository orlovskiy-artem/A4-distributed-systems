package com.orlovsky.mooc_platform.educational_material_service.services.impl;


import com.orlovsky.mooc_platform.educational_material_service.dto.*;
import com.orlovsky.mooc_platform.educational_material_service.mapper.CourseMapper;
import com.orlovsky.mooc_platform.educational_material_service.mapper.EducationalStepMapper;
import com.orlovsky.mooc_platform.educational_material_service.mapper.TestStepMapper;
import com.orlovsky.mooc_platform.educational_material_service.mapper.TestStepOptionRequestMapper;
import com.orlovsky.mooc_platform.educational_material_service.model.*;
import com.orlovsky.mooc_platform.educational_material_service.repository.*;
import com.orlovsky.mooc_platform.educational_material_service.services.EducationalMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.MissingResourceException;
import java.util.UUID;

@Service
public class EducationalMaterialServiceImpl implements EducationalMaterialService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseAuthorRepository courseAuthorRepository;
    @Autowired
    private EducationalStepRepository educationalStepRepository;
    @Autowired
    private TestStepRepository testStepRepository;
    @Autowired
    private TestStepOptionRepository testStepOptionRepository;
//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private  authorRepository;

    RestTemplate restTemplate = new RestTemplate();
    private static final String accountServiceUrl = "http://account-service:8090";

    // CRUD
    // Create
    @Override
    public Course createEmptyCourse(CourseDTO courseDTO) {
        Course course = CourseMapper.INSTANCE.toEntity(courseDTO);
        course.setStatus(CourseStatus.IN_DEVELOPMENT);
        System.out.println(course);
        return courseRepository.save(course);
    }

    // Read
    @Override
    public Course getCourseById(UUID courseId) throws MissingResourceException{
        return courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new MissingResourceException("Course not found",
                                "Course",courseId.toString()));
    }

    @Override
    public TestStep getTestStepById(UUID testStepId) throws MissingResourceException {
        return testStepRepository.findById(testStepId)
                .orElseThrow(()->
                        new MissingResourceException("Test step not found",
                                "TestStep",
                                testStepId.toString()));
    }

    @Override
    public EducationalStep getEducationalStepById(UUID educationalStepId) throws MissingResourceException {
        return educationalStepRepository.findById(educationalStepId)
                .orElseThrow(() ->
                        new MissingResourceException("Educational step not found",
                                "EducationalStep",
                                educationalStepId.toString()));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    // Update
    @Override
    public void updateCourseInfo(UUID courseId,CourseDTO courseDTO) throws MissingResourceException{
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        Course course = courseRepository.getOne(courseId);
        course.setTitle(courseDTO.getTitle());
        course.setPrice(courseDTO.getPrice());
        course.setDuration(courseDTO.getDuration());
        course.setDescription(courseDTO.getDescription());
        if(courseDTO.getStatus()!=null){
            course.setStatus(courseDTO.getStatus());
        }
        courseRepository.save(course);
    }

    @Override
    public void activateCourse(UUID courseId) throws MissingResourceException {
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        Course course = courseRepository.getOne(courseId);
        course.setStatus(CourseStatus.ACTIVE);
        courseRepository.save(course);
    }

    @Override
    public void deactivateCourse(UUID courseId) throws MissingResourceException {
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        Course course = courseRepository.getOne(courseId);
        course.setStatus(CourseStatus.DEPRECATED);
        courseRepository.save(course);
    }

    @Override
    public void setCourseStatus(UUID courseId, CourseStatus courseStatus) throws MissingResourceException {
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        Course course = courseRepository.getOne(courseId);
        course.setStatus(courseStatus);
    }

    @Override
    public EducationalStep addEducationalStep(UUID courseId, EducationalStepDTO educationalStepDTO) {
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        Course course = courseRepository.getOne(courseId);
        int numberOfSteps = course.getNumberOfSteps();
        EducationalStep educationalStep = EducationalStepMapper.INSTANCE.toEntity(educationalStepDTO);
        educationalStep.setPosition(numberOfSteps+1);
        educationalStep.setCourse(course);
        EducationalStep educationalStepSaved = educationalStepRepository.save(educationalStep);
        course.setNumberOfSteps(course.getNumberOfSteps()+1);
        course.getEducationalSteps().add(educationalStep);
        courseRepository.save(course);
        return educationalStepSaved;
    }

    @Override
    public TestStep addTestStep(UUID courseId,
                                TestStepDTO testStepDTO) {
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        Course course = courseRepository.getOne(courseId);
        int numberOfSteps = course.getNumberOfSteps();
        TestStep testStep = TestStepMapper.INSTANCE.toEntity(testStepDTO);
        testStep.setPosition(numberOfSteps+1);
        testStep.setCourse(course);
        TestStep testStepSaved = testStepRepository.save(testStep);
        course.setNumberOfSteps(course.getNumberOfSteps()+1);
        course.getTestSteps().add(testStepSaved);
        courseRepository.save(course);
        return testStepSaved;
    }
    @Override
    public TestStepOption addTestStepOption(UUID courseId,
                                            UUID testStepId,
                                            TestStepOptionRequestDTO testStepOptionRequestDTO) throws MissingResourceException{
        if(!testStepRepository.existsById(testStepId)){
            throw new MissingResourceException("Test step not found",
                    "Test step",
                    testStepId.toString());
        }
        TestStep testStep = testStepRepository.getOne(testStepId);
        TestStepOption testStepOption = TestStepOptionRequestMapper.INSTANCE.toEntity(testStepOptionRequestDTO);
        testStepOption.setTestStep(testStep);
        testStep.getAnswers().add(testStepOption);
        TestStepOption testStepOptionSaved = testStepOptionRepository.save(testStepOption);
        testStepRepository.save(testStep);
        return testStepOptionSaved;
    }

    @Override
    public TestStepOption getTestStepOption(UUID testStepId, UUID testAnswerId) {
        if(!testStepRepository.existsById(testStepId)){
            throw new MissingResourceException("Test step not found",
                    "Test step",
                    testStepId.toString());
        }
        if(!testStepOptionRepository.existsById(testAnswerId)){
            throw new MissingResourceException("Test step option not found",
                    "Test step option",
                    testAnswerId.toString());
        }
        return testStepOptionRepository.getOne(testAnswerId);
    }


    @Override
    public void addAuthor(UUID courseId, UUID authorId) {
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        ResponseEntity<String> responseIfAuthorExists =
                restTemplate.getForEntity(accountServiceUrl + "/authors/"+authorId.toString(),String.class);
        if(!responseIfAuthorExists.getStatusCode().is2xxSuccessful()){
            throw new MissingResourceException("Author not found",
                    "Author",
                    authorId.toString());
        }
        Course course = courseRepository.getOne(courseId);
        CourseAuthor courseAuthorRelation = new CourseAuthor(course,authorId);
        courseAuthorRepository.save(courseAuthorRelation);
        course.getCourseAuthor().add(courseAuthorRelation);
        courseRepository.save(course);
    }

    // Delete
    @Override
    public void deleteCourse(UUID courseId) {
        courseRepository.deleteById(courseId);
    }


    @Override
    public void deleteTestStep(UUID courseId,
                               UUID testStepId) throws MissingResourceException{
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        if(!testStepRepository.existsById(testStepId)){
            return;
        }
        Course course = courseRepository.getOne(courseId);
        TestStep testStep = testStepRepository.getOne(testStepId);
        course.getTestSteps().remove(testStep);
        course.setNumberOfSteps(course.getNumberOfSteps()-1);
        courseRepository.save(course);
        testStepRepository.deleteById(testStepId);
    }

    @Override
    public void deleteTestAnswer(UUID testStepId, UUID testAnswerId)
            throws MissingResourceException {
        if(!testStepRepository.existsById(testStepId)){
            throw new MissingResourceException("Test step not found",
                    "TestStep",
                    testStepId.toString());
        }
        if(!testStepOptionRepository.existsById(testAnswerId)){
            throw new MissingResourceException("Test answer not found",
                    "TestAnswer",
                    testAnswerId.toString());
        }
        TestStep testStep = testStepRepository.getOne(testStepId);
        TestStepOption testAnswer = testStepOptionRepository.getOne(testAnswerId);
        testStep.getAnswers().remove(testAnswer);
        testStepOptionRepository.delete(testAnswer);
    }

    @Override
    public void deleteAuthorById(UUID courseId, UUID authorId) throws MissingResourceException {
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        ResponseEntity<String> responseIfAuthorExists =
                restTemplate.getForEntity(accountServiceUrl + "/authors/"+authorId.toString(),String.class);
        if(!responseIfAuthorExists.getStatusCode().is2xxSuccessful()){
            throw new MissingResourceException("Author not found",
                    "Author",
                    authorId.toString());
        }
        Course course = courseRepository.getOne(courseId);
        courseAuthorRepository.deleteByCourseAndAuthorId(course,authorId);
    }

    @Override
    public void deleteEducationalStep(UUID courseId,
                                      UUID educationalStepId) throws MissingResourceException {
        if(!courseRepository.existsById(courseId)){
            throw new MissingResourceException("Course not found",
                    "Course",
                    courseId.toString());
        }
        if(!educationalStepRepository.existsById(educationalStepId)){
            return;
        }
        Course course = courseRepository.getOne(courseId);
        EducationalStep educationalStep = educationalStepRepository.getOne(educationalStepId);
        course.getTestSteps().remove(educationalStep);
        course.setNumberOfSteps(course.getNumberOfSteps()-1);
        courseRepository.save(course);
        educationalStepRepository.deleteById(educationalStepId);


    }
}
