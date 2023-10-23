package com.nutmai.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nutmai.backend.config.JwtService;
import com.nutmai.backend.model.User;
import com.nutmai.backend.model.request.LoginRequest;
import com.nutmai.backend.model.request.RegisterRequest;
import com.nutmai.backend.model.request.UpdateProfileRequest;
import com.nutmai.backend.model.response.AuthenticationResponse;
import com.nutmai.backend.repository.CourseRepository;
import com.nutmai.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;

  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request){

    var advisor = userRepository.findById(request.getAdvisorId()).orElse(null);
    var advisees = userRepository.findAllById(request.getAdviseeIds());
    var studyCourses = courseRepository.findAllById(request.getStudyCourseIds());
    var teachCourses = courseRepository.findAllById(request.getTeachCourseIds());

    var user = User.builder().
    id(request.getId()).username(request.getUsername()).password(passwordEncoder.encode(request.getPassword()))
    .title(request.getTitle()).name(request.getName()).surname(request.getSurname()).img(request.getImg())
    .role(request.getRole()).department(request.getDepartment()).advisor(advisor).advisees(advisees)
    .academicPosition(request.getAcademicPosition()).studyCourses(studyCourses).teachCourses(teachCourses).build();

    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
      .id(user.getId())
      .username(user.getUsername())
      .name(user.getName())
      .surname(user.getSurname())
      .img(user.getImg())
      .role(user.getRole().toString())
      .department(user.getDepartment())
      .academicPosition(user.getAcademicPosition())
      .token(jwtToken)
      .build();
  }

  public AuthenticationResponse login(LoginRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );
    var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
      .id(user.getId())
      .username(user.getUsername())
      .name(user.getName())
      .surname(user.getSurname())
      .img(user.getImg())
      .role(user.getRole().toString())
      .department(user.getDepartment())
      .academicPosition(user.getAcademicPosition())
      .token(jwtToken)
      .build();
  }

  public AuthenticationResponse update(UpdateProfileRequest request){
    var user = userRepository.findById(request.getId()).orElse(null);
    user.setName(request.getName());
    user.setSurname(request.getSurname());
    user.setImg(request.getImg());
    user.setDepartment(request.getDepartment());
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
      .id(user.getId())
      .username(user.getUsername())
      .name(user.getName())
      .surname(user.getSurname())
      .img(user.getImg())
      .role(user.getRole().toString())
      .department(user.getDepartment())
      .academicPosition(user.getAcademicPosition())
      .token(jwtToken)
      .build();
  }
}
