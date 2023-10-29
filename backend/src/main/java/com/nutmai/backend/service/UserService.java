package com.nutmai.backend.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nutmai.backend.model.File;
import com.nutmai.backend.model.Role;
import com.nutmai.backend.model.request.FileRequest;
import com.nutmai.backend.model.request.UpdateAdvisorRequest;
import com.nutmai.backend.model.response.Pagination;
import com.nutmai.backend.model.response.UserResponse;
import com.nutmai.backend.repository.FileRepository;
import com.nutmai.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
        private final UserRepository userRepository;
        private final FileRepository fileRepository;

        public UserResponse getUserById(String id) {
                var user = userRepository.findById(id).orElse(null);
                return UserResponse.builder().id(user.getId()).username(user.getUsername()).title(user.getTitle())
                                .name(user.getName()).surname(user.getSurname()).img(user.getImg()).role(user.getRole())
                                .department(user.getDepartment()).academicPosition(user.getAcademicPosition())
                                .advisor(user.getAdvisor())
                                .advisees(user.getAdvisees()).studyCourses(user.getStudyCourses())
                                .teachCourses(user.getTeachCourses())
                                .comments(user.getComments()).createdComments(user.getCreatedComments())
                                .createdReplies(user.getCreatedReplies()).build();
        }

        public Pagination<List<UserResponse>> getStudents(int page, int pageSize) {
                var users = userRepository.findAllByRole(Role.STUDENT, PageRequest.of(page, pageSize));
                return Pagination.<List<UserResponse>>builder()
                                .page(page)
                                .pageSize(pageSize)
                                .totalPage(users.getTotalPages())
                                .totalElements(users.getTotalElements())
                                .data(users.getContent().stream()
                                                .map(user -> UserResponse.builder().id(user.getId())
                                                                .username(user.getUsername())
                                                                .title(user.getTitle())
                                                                .name(user.getName()).surname(user.getSurname())
                                                                .img(user.getImg()).role(user.getRole())
                                                                .department(user.getDepartment())
                                                                .academicPosition(user.getAcademicPosition())
                                                                .advisor(user.getAdvisor()).advisees(user.getAdvisees())
                                                                .studyCourses(user.getStudyCourses())
                                                                .teachCourses(user.getTeachCourses())
                                                                .comments(user.getComments())
                                                                .createdComments(user.getCreatedComments())
                                                                .createdReplies(user.getCreatedReplies())
                                                                .build())
                                                .toList())
                                .build();
        }

        public Pagination<List<UserResponse>> getTeachers(int page, int pageSize) {
                var users = userRepository.findAllByRole(Role.TEACHER, PageRequest.of(page, pageSize));
                return Pagination.<List<UserResponse>>builder()
                                .page(page)
                                .pageSize(pageSize)
                                .totalPage(users.getTotalPages())
                                .totalElements(users.getTotalElements())
                                .data(users.getContent().stream()
                                                .map(user -> UserResponse.builder().id(user.getId())
                                                                .username(user.getUsername())
                                                                .title(user.getTitle())
                                                                .name(user.getName()).surname(user.getSurname())
                                                                .img(user.getImg()).role(user.getRole())
                                                                .department(user.getDepartment())
                                                                .academicPosition(user.getAcademicPosition())
                                                                .advisor(user.getAdvisor()).advisees(user.getAdvisees())
                                                                .studyCourses(user.getStudyCourses())
                                                                .teachCourses(user.getTeachCourses())
                                                                .comments(user.getComments())
                                                                .createdComments(user.getCreatedComments())
                                                                .createdReplies(user.getCreatedReplies())
                                                                .build())
                                                .toList())
                                .build();
        }

        public void updateAdvisor(UpdateAdvisorRequest request) {
                var student = userRepository.findById(request.getStudentId()).orElse(null);
                var teacher = userRepository.findById(request.getAdvisorId()).orElse(null);
                student.setAdvisor(teacher);
                userRepository.save(student);

                var advisees = teacher.getAdvisees();
                advisees.add(student);
                teacher.setAdvisees(advisees);
                userRepository.save(teacher);
        }

        public void uploadFile(FileRequest request) {
                var user = userRepository.findById(request.getCreatedByUserId()).orElse(null);
                var file = File.builder().id(request.getId()).name(request.getName()).type(request.getType())
                                .data(request.getData()).description(request.getDescription()).createdBy(user)
                                .build();
                fileRepository.save(file);
        }

        public List<File> getAllFiles() {
                return fileRepository.findAll();
        }
}
