package com.nutmai.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nutmai.backend.model.request.LoginRequest;
import com.nutmai.backend.model.request.RegisterRequest;
import com.nutmai.backend.model.response.AuthenticationResponse;
import com.nutmai.backend.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    try {
      AuthenticationResponse response = authenticationService.register(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Registration failed: " + e.getMessage(), e);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticate(@RequestBody LoginRequest request) {
    if (request.getUsername() == null || request.getPassword() == null) {
      return ResponseEntity.badRequest().body("Both username and password are required.");
    }

    try {
      AuthenticationResponse response = authenticationService.login(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed: " + e.getMessage(), e);
    }
  }

  // Global exception handler for unhandled exceptions
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
  }
}

