package com.nutmai.backend.user;

import java.util.Date;
import java.util.UUID;

public class Comment {
  private UUID id;
  private String text;
  private Student student;
  private Date createdAt;
  private User createdBy;
  private Reply[] replies;
}
