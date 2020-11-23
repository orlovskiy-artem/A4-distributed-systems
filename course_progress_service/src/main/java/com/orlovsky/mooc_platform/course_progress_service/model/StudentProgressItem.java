package com.orlovsky.mooc_platform.course_progress_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_progress_item")
public class StudentProgressItem {
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID studentId;
    private UUID courseId;
    private UUID passedEducationalStepId;
    private UUID passedTestStepId;
    private UUID chosenOptionId;
    private Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
}