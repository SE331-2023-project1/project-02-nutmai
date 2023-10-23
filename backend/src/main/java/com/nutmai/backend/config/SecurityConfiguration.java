package com.nutmai.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nutmai.backend.model.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().cors().and()
      .authorizeHttpRequests()
      .requestMatchers("/api/v1/auth/**").permitAll()
      .requestMatchers("/api/v1/student/**").hasAnyAuthority(Role.STUDENT.name(), Role.TEACHER.name(), Role.ADMIN.name())
      .requestMatchers("/api/v1/teacher/**").hasAnyAuthority(Role.TEACHER.name(), Role.ADMIN.name())
      .requestMatchers("/api/v1/admin/**").hasAuthority(Role.ADMIN.name())
      .anyRequest().authenticated().and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
