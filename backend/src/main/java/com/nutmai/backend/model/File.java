package com.nutmai.backend.model;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file")
public class File {
  @Id
  public String id;
  public String name;
  @Nullable
  public String description;
  public String type;
  @Lob
  @Column(name = "data", length = 100000000)
  public String data;
  @ManyToOne
  @JoinColumn(name = "created_by_user_id", referencedColumnName = "id")
  public User createdBy;
}
