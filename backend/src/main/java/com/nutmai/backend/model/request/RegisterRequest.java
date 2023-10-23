package com.nutmai.backend.model.request;

import java.util.ArrayList;
import java.util.List;

import com.nutmai.backend.model.Role;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @Id
  private String id;
  private String username;
  private String password;
  private String title;
  private String name;
  private String surname;
  @Nullable
  @Builder.Default
  private String img = "https://fakeimg.pl/300x400";
  @Enumerated(EnumType.STRING)
  private Role role;
  private String department;
  @Nullable
  @Builder.Default
  private String advisorId = "";
  @Nullable
  @Builder.Default
  private List<String> adviseeIds = new ArrayList<>();
  @Nullable
  @Builder.Default
  private String academicPosition = "";
  @Nullable
  @Builder.Default
  private List<String> studyCourseIds = new ArrayList<>();
  @Nullable
  @Builder.Default
  private List<String> teachCourseIds = new ArrayList<>();
}
