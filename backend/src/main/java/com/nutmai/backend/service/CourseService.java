package com.nutmai.backend.service;

import org.springframework.stereotype.Service;

import com.nutmai.backend.model.Course;
import com.nutmai.backend.model.request.CreateCourseRequest;
import com.nutmai.backend.repository.CourseRepository;
import com.nutmai.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;

  public void createCourse(CreateCourseRequest request) {
    var teacher = userRepository.findById(request.getTeacherId()).orElse(null);
    var students = userRepository.findAllById(request.getStudentIds());

    var course = Course.builder()
      .id(request.getId())
      .name(request.getName())
      .description(request.getDescription())
      .teacher(teacher)
      .students(students)
      .build();

    courseRepository.save(course);
  }
}
