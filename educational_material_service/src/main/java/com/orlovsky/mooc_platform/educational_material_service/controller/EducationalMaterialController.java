package com.orlovsky.mooc_platform.educational_material_service.controller;


import com.orlovsky.mooc_platform.educational_material_service.dto.CourseDTO;
import com.orlovsky.mooc_platform.educational_material_service.dto.EducationalStepDTO;
import com.orlovsky.mooc_platform.educational_material_service.dto.TestStepDTO;
import com.orlovsky.mooc_platform.educational_material_service.dto.TestStepOptionRequestDTO;
import com.orlovsky.mooc_platform.educational_material_service.mapper.CourseMapper;
import com.orlovsky.mooc_platform.educational_material_service.model.*;
import com.orlovsky.mooc_platform.educational_material_service.services.EducationalMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;

@RestController
public class EducationalMaterialController {
//    @Autowired
//    private DiscoveryClient discoveryClient;


    @Autowired
    private EducationalMaterialService educationalMaterialService;

    // CRUD
    // Create
    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO body){
        Course course = educationalMaterialService.createEmptyCourse(body);
        return new ResponseEntity<>(course,HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCourse(@PathVariable UUID id){
        try{
            Course course = educationalMaterialService.getCourseById(id);
            CourseDTO response = CourseMapper.INSTANCE.toDto(course);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getAllCourses(){
        List<Course> courses = educationalMaterialService.getAllCourses();
        return new ResponseEntity<>(courses,HttpStatus.OK);
    }

    @GetMapping("/courses/{id}/steps/educational-steps")
    public ResponseEntity<?> getAllEducationalSteps(@PathVariable(name = "id") UUID id) {
        try{
            Course course = educationalMaterialService.getCourseById(id);
            return new ResponseEntity<>(course.getEducationalSteps(),HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courses/{id}/steps/test-steps")
    public ResponseEntity<?> getAllTestSteps(@PathVariable(name = "id") UUID id){
        try{
            Course course = educationalMaterialService.getCourseById(id);
            return new ResponseEntity<>(course.getTestSteps(),HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courses/{courseId}/steps/test-steps/{testStepId}")
    public ResponseEntity<?> getTestStepById(@PathVariable(name = "courseId") UUID courseId,
                                             @PathVariable(name = "testStepId") UUID testStepId){
        try{
            TestStep testStep = educationalMaterialService.getTestStepById(testStepId);
            return new ResponseEntity<>(testStep,HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/test-steps/{testStepId}")
    public ResponseEntity<?> getTestStepById(@PathVariable(name = "testStepId") UUID testStepId){
        try{
            TestStep testStep = educationalMaterialService.getTestStepById(testStepId);
            return new ResponseEntity<>(testStep,HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/courses/{courseId}/steps/educational-steps/{educationalStepId}")
    public ResponseEntity<?> getEducationalStepById(@PathVariable(name = "courseId") UUID courseId,
                                                    @PathVariable(name = "educationalStepId") UUID educationalStepId){
        try{
            EducationalStep educationalStep = educationalMaterialService.getEducationalStepById(educationalStepId);
            return new ResponseEntity<>(educationalStep,HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/educational-steps/{educationalStepId}")
    public ResponseEntity<?> getEducationalStepById(@PathVariable(name = "educationalStepId") UUID educationalStepId){
        try{
            EducationalStep educationalStep = educationalMaterialService.getEducationalStepById(educationalStepId);
            return new ResponseEntity<>(educationalStep,HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update
    @PutMapping("/courses/{id}")
    public ResponseEntity<?> updateCourseInfo(@PathVariable(name = "id") UUID id,
                                              @RequestBody CourseDTO body){
        try {
            educationalMaterialService.updateCourseInfo(id,body);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }
    @PatchMapping(value = "/courses/{id}/status",consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatusCourse(@PathVariable(name = "id") UUID id,
                                                @RequestBody Map<String, String> patch){
        try{
            Course course = educationalMaterialService.getCourseById(id);
            course.setStatus(CourseStatus.valueOf(patch.get("value")));
            educationalMaterialService.updateCourseInfo(id,CourseMapper.INSTANCE.toDto(course));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/courses/{id}/steps/test-steps")
    public ResponseEntity<TestStep> addTestStep(@PathVariable(name = "id") UUID courseId,
                                                @RequestBody TestStepDTO body){
        try{
            TestStep testStep = educationalMaterialService.addTestStep(courseId, body);
            return new ResponseEntity<>(testStep,HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/courses/{courseId}/steps/test-steps/{testStepId}/answers",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TestStepOption> addTestStepOption(@PathVariable(name = "courseId") UUID courseId,
                                                            @PathVariable(name = "testStepId") UUID testStepId,
                                                            @RequestBody TestStepOptionRequestDTO body){
        try{
            TestStepOption testStepOption = educationalMaterialService.addTestStepOption(courseId, testStepId, body);
            return new ResponseEntity<>(testStepOption,HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping(value = "/test-steps/{testStepId}/answer/{testAnswerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TestStepOption> getTestStepOption(@PathVariable(name = "testStepId") UUID testStepId,
                                                            @PathVariable(name = "testAnswerId") UUID testAnswerId){
        try{
            TestStepOption testStepOption = educationalMaterialService.getTestStepOption(testStepId, testAnswerId);
            return new ResponseEntity<>(testStepOption,HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping(value = "/courses/{id}/steps/educational-steps")
    public ResponseEntity<EducationalStep> addEducationalStep(@PathVariable(name = "id") UUID courseId,
                                                              @RequestBody EducationalStepDTO body){
        try{
            EducationalStep educationalStep = educationalMaterialService.addEducationalStep(courseId, body);
            return new ResponseEntity<>(educationalStep,HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value = "/courses/{courseId}/authors/{authorId}")
    public ResponseEntity<?> addAuthor(@PathVariable(name = "courseId") UUID courseId,
                                       @PathVariable(name = "authorId") UUID authorId){
        try{
            educationalMaterialService.addAuthor(courseId,authorId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    //Delete
    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable(name = "id") UUID id){
        try {
            educationalMaterialService.deleteCourse(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/courses/{courseId}/authors/{authorId}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable(name = "courseId") UUID courseId,
                                              @PathVariable(name = "authorId") UUID authorId){
        try {
            educationalMaterialService.deleteAuthorById(courseId,authorId);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/courses/{courseId}/steps/educational-steps/{educationalStepId}")
    public ResponseEntity<?> deleteEducationalStep(@PathVariable(name="courseId") UUID courseId,
                                                   @PathVariable(name="educationalStepId") UUID educationalStepId
    ){
        try{
            educationalMaterialService.deleteEducationalStep(courseId,educationalStepId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/courses/{courseId}/steps/test-steps/{testStepId}")
    public ResponseEntity<?> deleteTestStep(@PathVariable(name="courseId") UUID courseId,
                                            @PathVariable(name="testStepId") UUID testStepId
    ){
        try{
            educationalMaterialService.deleteTestStep(courseId,testStepId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/courses/{courseId}/steps/test-steps/{testStepId}/answers/{testAnswerId}")
    public ResponseEntity<?> deleteTestAnswer(@PathVariable(name = "courseId") UUID courseId,
                                              @PathVariable(name = "testStepId") UUID testStepId,
                                              @PathVariable(name = "testAnswerId") UUID testAnswerId){
        try{
            educationalMaterialService.deleteTestAnswer(testStepId,testAnswerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping(value = "/test-steps/{testStepId}/answers/{testAnswerId}")
    public ResponseEntity<?> deleteTestAnswer(@PathVariable(name = "testStepId") UUID testStepId,
                                              @PathVariable(name = "testAnswerId") UUID testAnswerId){
        try{
            educationalMaterialService.deleteTestAnswer(testStepId,testAnswerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
