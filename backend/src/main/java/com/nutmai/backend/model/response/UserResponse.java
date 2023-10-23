package com.nutmai.backend.model.response;

import java.util.List;

import com.nutmai.backend.model.Comment;
import com.nutmai.backend.model.Course;
import com.nutmai.backend.model.Reply;
import com.nutmai.backend.model.Role;
import com.nutmai.backend.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  private String id;
  private String username;
  private String title;
  private String name;
  private String surname;
  private String img;
  private Role role;
  private String department;
  private String academicPosition;
  private User advisor;
  private List<User> advisees;
  private List<Course> studyCourses;
  private List<Course> teachCourses;
  private List<Comment> comments;
  private List<Comment> createdComments;
  private List<Reply> createdReplies;
}
