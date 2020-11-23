package com.orlovsky.mooc_platform.educational_material_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(value= FetchMode.SELECT)
    @JsonManagedReference
    private List<CourseAuthor> courseAuthor;

    @Column(name = "duration")
    private int duration;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EducationalStep> educationalSteps = new ArrayList<>();

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL,   fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TestStep> testSteps = new ArrayList<>();

    @Column
    private int numberOfSteps;

    @Column(name = "price")
    private long price;
}
