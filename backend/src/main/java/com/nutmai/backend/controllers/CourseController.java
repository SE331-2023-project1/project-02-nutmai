package com.nutmai.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutmai.backend.model.request.CreateCourseRequest;
import com.nutmai.backend.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
  
  private final CourseService courseService;

  @PostMapping("/create")
  public ResponseEntity<?> createCourse(@RequestBody CreateCourseRequest request) {
    try{
      courseService.createCourse(request);
      return ResponseEntity.ok("Course created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Course creation failed: " + e.getMessage());
    }
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
  }
}
