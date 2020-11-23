package com.orlovsky.mooc_platform.service.impl;

import com.orlovsky.mooc_platform.model.Course;
import com.orlovsky.mooc_platform.model.Student;
import com.orlovsky.mooc_platform.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public void payForCourse(Student student,
                             Course course) {
        System.out.println(student.getFirstName() + " " + student.getLastName()+ " paid " + course.getPrice() + " for course " + course.getTitle());
    }

    // it is simplification of real process
    @Override
    public boolean checkIfCoursePaid() {
        return true;
    }
}
