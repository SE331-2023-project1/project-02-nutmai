package com.nutmai.backend.model.request;

import java.util.Date;
import java.util.List;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
  @Id
  private String id;
  private String text;
  private String userId;
  private Date createdAt;
  private String createdById;
  @Nullable
  private List<String> replyIds;
}
