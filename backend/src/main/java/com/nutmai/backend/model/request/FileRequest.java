package com.nutmai.backend.model.request;

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
public class FileRequest {
  @Id
  private String id;
  private String name;
  @Nullable
  private String description;
  private String type;
  private String data;
  private String createdByUserId;
}
