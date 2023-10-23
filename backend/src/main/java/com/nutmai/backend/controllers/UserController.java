package com.nutmai.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutmai.backend.model.request.RegisterRequest;
import com.nutmai.backend.service.AuthenticationService;
import com.nutmai.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
  private final AuthenticationService authenticationService;
  private final UserService userService;

  @GetMapping("/users/getUserById")
  public ResponseEntity<?> getUserById(@RequestParam String id) {
    try {
      var user = userService.getUserById(id);
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("User not found: " + e.getMessage());
    }
  }

  @GetMapping("users/getStudents")
  public ResponseEntity<?> getStudents(@RequestParam int page, @RequestParam int pageSize) {
    try {
      var students = userService.getStudents(page, pageSize);
      return ResponseEntity.ok(students);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Students not found: " + e.getMessage());
    }
  }

  @GetMapping("users/getTeachers")
  public ResponseEntity<?> getTeachers(@RequestParam int page, @RequestParam int pageSize) {
    try {
      var teachers = userService.getTeachers(page, pageSize);
      return ResponseEntity.ok(teachers);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Teachers not found: " + e.getMessage());
    }
  }

  @PostMapping("admin/users/create")
  public ResponseEntity<?> createUser(@RequestParam RegisterRequest request) {
    try {
      authenticationService.register(request);
      return ResponseEntity.ok("User created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("User creation failed: " + e.getMessage());
    }
  }
}
