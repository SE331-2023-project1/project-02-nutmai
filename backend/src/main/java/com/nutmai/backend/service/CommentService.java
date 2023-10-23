package com.nutmai.backend.service;

import org.springframework.stereotype.Service;

import com.nutmai.backend.model.Comment;
import com.nutmai.backend.model.request.CommentRequest;
import com.nutmai.backend.repository.CommentRepository;
import com.nutmai.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;

  public void createComment(CommentRequest request) {
    var user = userRepository.findById(request.getUserId()).orElse(null);
    var comment = Comment.builder()
      .text(request.getText())
      .user(user)
      .createdAt(request.getCreatedAt())
      .createdBy(user)
      .build();

    commentRepository.save(comment);
  }

  public void updateComment(CommentRequest request) {
    var comment = commentRepository.findById(request.getId()).orElse(null);
    var user = userRepository.findById(request.getUserId()).orElse(null);

    comment.setText(request.getText());
    comment.setUser(user);
    comment.setCreatedAt(request.getCreatedAt());
    comment.setCreatedBy(user);

    commentRepository.save(comment);
  }

  public void deleteComment(String id) {
    commentRepository.deleteById(id);
  }

  public Comment getComment(String id) {
    return commentRepository.findById(id).orElse(null);
  }
}
