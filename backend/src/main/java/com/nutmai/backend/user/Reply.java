package com.nutmai.backend.user;

import java.util.Date;
import java.util.UUID;

public class Reply {
  private UUID id;
  private String text;
  private Comment comment;
  private Date createdAt;
  private User createdBy;
}
