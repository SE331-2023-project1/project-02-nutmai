package com.nutmai.backend.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reply")
public class Reply {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  private String text;
  @ManyToOne
  @JoinColumn(name = "comment_id", referencedColumnName = "id")
  private Comment comment;
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
  @ManyToOne
  private User createdBy;
}
