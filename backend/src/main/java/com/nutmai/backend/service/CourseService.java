package com.nutmai.backend.service;

import org.springframework.stereotype.Service;

import com.nutmai.backend.model.Course;
import com.nutmai.backend.model.request.CourseRequest;
import com.nutmai.backend.repository.CourseRepository;
import com.nutmai.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;

  public void createCourse(CourseRequest request) {
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

  public void updateCourse(CourseRequest request) {
    var course = courseRepository.findById(request.getId()).orElse(null);
    var teacher = userRepository.findById(request.getTeacherId()).orElse(null);
    var students = userRepository.findAllById(request.getStudentIds());

    course.setName(request.getName());
    course.setDescription(request.getDescription());
    course.setTeacher(teacher);
    course.setStudents(students);

    courseRepository.save(course);
  }

  public void deleteCourse(String id) {
    courseRepository.deleteById(id);
  }

  public Course getCourse(String id) {
    return courseRepository.findById(id).orElse(null);
  }

}
