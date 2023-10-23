package com.nutmai.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutmai.backend.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {
  
}
