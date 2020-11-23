package com.orlovsky.mooc_platform.educational_material_service.repository;

import com.orlovsky.mooc_platform.educational_material_service.model.Course;
import com.orlovsky.mooc_platform.educational_material_service.model.CourseAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseAuthorRepository extends JpaRepository<CourseAuthor, UUID> {
//    @Query("select s.id, s.name from courses_authors s")
//    List<Object> getSchoolIdAndName();

//    @Query("SELECT u FROM courses_authors u WHERE u.course = ?1 and u.author_id = ?2")
//    List<CourseAuthor> findCourseAuthorRelations(UUID course_id, UUID author_id);
//
//    @Query("from courses_authors с where (cast(:userId as org.hibernate.type.UUIDCharType) IS NULL OR r.userId = :userId) AND (:status IS NULL OR r.status = :status)")
//    Page<Rental> findAllUsingCourseIdAndAuthorId(@Param("courseId") UUID userId,
//                                                 @Param("authorId") UUID authorId,
//                                                   Pageable pageable);
//    https://stackoverflow.com/questions/60001641/spring-data-query-could-not-determine-data-type-of-uuid-parameter

    UUID deleteByCourseAndAuthorId(Course course, UUID author_id);

//    List<CourseAuthor> removeBy(String lastname);
}
