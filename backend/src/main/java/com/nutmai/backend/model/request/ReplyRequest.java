package com.nutmai.backend.model.request;

import java.sql.Date;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequest {
  @Id
  private String id;
  private String text;
  private String createdByUserId;
  private Date createdAt;
  private String commentId;
}
