package com.orlovsky.mooc_platform.course_progress_service.controller;


import com.orlovsky.mooc_platform.course_progress_service.dto.TestStepOptionDTO;
import com.orlovsky.mooc_platform.course_progress_service.services.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.MissingResourceException;
import java.util.UUID;

@RestController
@RequestMapping("/progress/")
public class CourseProgressController {
    @Autowired
    private CourseProgressService courseProgressService;

    @PostMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<?> signUpUser(@PathVariable(name = "courseId") UUID courseId,
                                        @PathVariable(name = "studentId") UUID studentId) {
        try {
            courseProgressService.signUpUser(courseId,studentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/{courseId}/students/{studentId}/steps/educational-steps/{educationalStepId}")
    public ResponseEntity<?> passEducationalStep(@PathVariable(name = "courseId") UUID courseId,
                                                 @PathVariable(name = "studentId") UUID studentId,
                                                 @PathVariable(name = "educationalStepId") UUID educationalStepId
    ){
        try{
            courseProgressService.makePassedEducationalStep(courseId,studentId,educationalStepId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MissingResourceException e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


    @PostMapping("/{courseId}/students/{studentId}/steps/test-steps/{testStepId}")
    public ResponseEntity<?> processTestStep(@PathVariable(name = "courseId") UUID courseId,
                                             @PathVariable(name = "studentId") UUID studentId,
                                             @PathVariable(name = "testStepId") UUID testStepId,
                                             @RequestBody TestStepOptionDTO chosenAnswer){
        try{
            Boolean completed = courseProgressService.makeProcessedTestStep(courseId,
                    studentId,
                    testStepId,
                    chosenAnswer);
            return new ResponseEntity<>(completed,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


}
