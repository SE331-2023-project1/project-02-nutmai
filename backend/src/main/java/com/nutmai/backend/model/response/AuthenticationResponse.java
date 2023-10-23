package com.nutmai.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private String id;
  private String username;
  private String name;
  private String surname;
  private String img;
  private String role;
  private String department;
  private String academicPosition;
  private String token;
}
