package com.nutmai.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nutmai.backend.model.File;

public interface FileRepository extends JpaRepository<File, String> {
  
}
