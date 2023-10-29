package com.nutmai.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutmai.backend.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, String> {
  List<Reply> findAllByCommentId(String commentId);
}
