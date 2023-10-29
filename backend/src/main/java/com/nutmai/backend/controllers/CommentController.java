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

import com.nutmai.backend.model.request.CommentRequest;
import com.nutmai.backend.model.request.CourseRequest;
import com.nutmai.backend.model.request.ReplyRequest;
import com.nutmai.backend.service.CommentService;
import com.nutmai.backend.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/teacher/comments/create")
  public ResponseEntity<?> createComment(@RequestBody CommentRequest request) {
    try {
      commentService.createComment(request);
      return ResponseEntity.ok("Comment created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Comment creation failed: " + e.getMessage());
    }
  }

  @PutMapping("/teacher/comments/update")
  public ResponseEntity<?> updateCourse(@RequestBody CommentRequest request) {
    try {
      commentService.updateComment(request);
      return ResponseEntity.ok("Comment updated successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Comment update failed: " + e.getMessage());
    }
  }

  @DeleteMapping("/teacher/comments/delete")
  public ResponseEntity<?> deleteCourse(@RequestParam String id) {
    try {
      commentService.deleteComment(id);
      return ResponseEntity.ok("Comment deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Comment deletion failed: " + e.getMessage());
    }
  }

  @GetMapping("/comments/get")
  public ResponseEntity<?> getComment(@RequestParam String id) {
    try {
      var comment = commentService.getComment(id);
      return ResponseEntity.ok(comment);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Comment retrieval failed: " + e.getMessage());
    }
  }

  @PostMapping("/student/replies/create")
  public ResponseEntity<?> createReply(@RequestBody ReplyRequest request) {
    try {
      commentService.createReply(request);
      return ResponseEntity.ok("Reply created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Reply creation failed: " + e.getMessage());
    }
  }

  @GetMapping("/replies/get")
  public ResponseEntity<?> getReply(@RequestParam String id) {
    try {
      var reply = commentService.getReply(id);
      return ResponseEntity.ok(reply);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Reply retrieval failed: " + e.getMessage());
    }
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
  }
}
