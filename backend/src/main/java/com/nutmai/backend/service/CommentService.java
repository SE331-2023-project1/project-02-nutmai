package com.nutmai.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nutmai.backend.model.Comment;
import com.nutmai.backend.model.Reply;
import com.nutmai.backend.model.request.CommentRequest;
import com.nutmai.backend.model.request.ReplyRequest;
import com.nutmai.backend.repository.CommentRepository;
import com.nutmai.backend.repository.ReplyRepository;
import com.nutmai.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final ReplyRepository replyRepository;

  public void createComment(CommentRequest request) {
    var user = userRepository.findById(request.getUserId()).orElse(null);
    var createdBy = userRepository.findById(request.getCreatedById()).orElse(null);
    var comment = Comment.builder()
      .id(request.getId())
      .text(request.getText())
      .user(user)
      .createdAt(request.getCreatedAt())
      .createdBy(createdBy)
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

  public void createReply(ReplyRequest request){
    var user = userRepository.findById(request.getCreatedByUserId()).orElse(null);
    var comment = commentRepository.findById(request.getCommentId()).orElse(null);
    var reply = Reply.builder()
      .id(request.getId())
      .text(request.getText())
      .createdBy(user)
      .createdAt(request.getCreatedAt())
      .comment(comment)
      .build();

    replyRepository.save(reply);
  }

  public List<Reply> getReply(String commentId){
    return replyRepository.findAllByCommentId(commentId);
  }
}
