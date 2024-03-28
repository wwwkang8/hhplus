package com.tdd.courseapi.repository;

import com.tdd.courseapi.domain.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

  public CourseEntity findCourseEntityByCourseId(long courseId);

}
