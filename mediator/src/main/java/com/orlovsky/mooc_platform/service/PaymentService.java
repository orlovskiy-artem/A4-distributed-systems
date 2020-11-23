package com.orlovsky.mooc_platform.service;

import com.orlovsky.mooc_platform.model.Course;
import com.orlovsky.mooc_platform.model.Student;

public interface PaymentService {
    void payForCourse(Student student,
                      Course course);

    // it is simplification of real process
    boolean checkIfCoursePaid();
}

