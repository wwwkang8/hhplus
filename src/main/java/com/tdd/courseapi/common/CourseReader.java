package com.tdd.courseapi.common;

import com.tdd.courseapi.domain.CourseEntity;
import com.tdd.courseapi.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseReader {

  private final CourseRepository courseRepository;

  public CourseEntity getCourse(long courseId) {
    return courseRepository.findCourseEntityByCourseId(courseId);
  }

}
