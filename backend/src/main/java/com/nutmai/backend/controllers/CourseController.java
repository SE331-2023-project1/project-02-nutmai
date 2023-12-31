package com.nutmai.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutmai.backend.model.request.CourseRequest;
import com.nutmai.backend.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @PostMapping("/admin/courses/create")
  public ResponseEntity<?> createCourse(@RequestBody CourseRequest request) {
    try {
      courseService.createCourse(request);
      return ResponseEntity.ok("Course created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Course creation failed: " + e.getMessage());
    }
  }

  @PutMapping("/admin/courses/update")
  public ResponseEntity<?> updateCourse(@RequestBody CourseRequest request) {
    try {
      courseService.updateCourse(request);
      return ResponseEntity.ok("Course updated successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Course update failed: " + e.getMessage());
    }
  }

  @DeleteMapping("/admin/courses/delete")
  public ResponseEntity<?> deleteCourse(@RequestParam String id) {
    try {
      courseService.deleteCourse(id);
      return ResponseEntity.ok("Course deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Course deletion failed: " + e.getMessage());
    }
  }

  @GetMapping("/courses/get")
  public ResponseEntity<?> getCourse(@RequestParam String id) {
    try {
      var course = courseService.getCourse(id);
      return ResponseEntity.ok(course);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Course retrieval failed: " + e.getMessage());
    }
  }
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
  }
}
