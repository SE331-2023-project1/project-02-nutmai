package com.nutmai.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutmai.backend.model.Course;

public interface CourseRepository extends JpaRepository<Course, String> {
  
}
