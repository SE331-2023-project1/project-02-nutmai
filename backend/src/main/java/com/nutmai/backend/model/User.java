package com.nutmai.backend.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
@JsonIgnoreProperties({"advisees", "studyCourses", "teachCourses", "comments", "createdComments", "createdReplies", "createdFiles"})
public class User implements UserDetails {
  @Id
  private String id;
  private String username;
  @JsonIgnore
  private String password;
  private String title;
  private String name;
  private String surname;
  @Nullable
  @Lob
  @Column(name = "img", length = 100000000)
  private String img;
  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private Role role;
  private String department;
  @Nullable
  private String academicPosition;
  @Nullable
  @ManyToOne
  @JoinColumn(name = "advisor_id", referencedColumnName = "id")
  private User advisor;
  @Nullable
  @OneToMany(mappedBy = "advisor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<User> advisees;
  @Nullable
  @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Course> studyCourses;
  @Nullable
  @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Course> teachCourses;
  @Nullable
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Comment> comments;
  @Nullable
  @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Comment> createdComments;
  @Nullable
  @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Reply> createdReplies;
  @Nullable
  @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<File> createdFiles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
