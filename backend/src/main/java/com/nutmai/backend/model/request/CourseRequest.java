package com.nutmai.backend.model.request;

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
public class CourseRequest {
  @Id
  private String id;
  private String name;
  @Nullable
  @Builder.Default
  private String description = "";
  private String teacherId;
  private List<String> studentIds;
}
