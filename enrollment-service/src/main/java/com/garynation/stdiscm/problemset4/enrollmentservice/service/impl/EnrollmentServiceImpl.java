package com.garynation.stdiscm.problemset4.enrollmentservice.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.garynation.stdiscm.problemset4.enrollmentservice.dto.EnrollmentDto;
import com.garynation.stdiscm.problemset4.enrollmentservice.entity.Enrollment;
import com.garynation.stdiscm.problemset4.enrollmentservice.entity.User;
import com.garynation.stdiscm.problemset4.enrollmentservice.exception.ResourceNotFoundException;
import com.garynation.stdiscm.problemset4.enrollmentservice.mapper.EnrollmentMapper;
import com.garynation.stdiscm.problemset4.enrollmentservice.repository.CourseRepository;
import com.garynation.stdiscm.problemset4.enrollmentservice.repository.EnrollmentRepository;
import com.garynation.stdiscm.problemset4.enrollmentservice.repository.UserRepository;
import com.garynation.stdiscm.problemset4.enrollmentservice.service.EnrollmentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnrollmentServiceImpl
implements EnrollmentService {

    private EnrollmentRepository enrollmentRepository;
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Override
    public EnrollmentDto addEnrollment(EnrollmentDto enrollmentDto) {
        courseRepository.findById(enrollmentDto.getCourseId()).orElseThrow(()->new ResourceNotFoundException("Course not found"));
        User user = userRepository.findById(enrollmentDto.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found"));
        if (!Objects.equals(user.getRole(), "student")) {
            throw new IllegalArgumentException("Only students can enroll");
        }

        Enrollment enrollment = EnrollmentMapper.mapToEnrollment(enrollmentDto);
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return EnrollmentMapper.mapToEnrollmentDto(savedEnrollment);
    }

    @Override
    public EnrollmentDto deleteEnrollment(UUID facultyId, UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(()->new ResourceNotFoundException("Enrollment not found"));
        User user = userRepository.findById(facultyId).orElseThrow(()->new ResourceNotFoundException("User not found"));
        if (!Objects.equals(user.getRole(), "faculty")) {
            throw new IllegalArgumentException("Only faculty can remove enrollments");
        }
        enrollmentRepository.delete(enrollment);
        return null;
    }

    @Override
    public EnrollmentDto getEnrollmentById(UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(()->new ResourceNotFoundException("Enrollment not found"));

        return EnrollmentMapper.mapToEnrollmentDto(enrollment);
    }

    @Override
    public List<EnrollmentDto> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments.stream().map(EnrollmentMapper::mapToEnrollmentDto).collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByCourseId(UUID courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        return enrollments.stream().map(EnrollmentMapper::mapToEnrollmentDto).collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByUserId(UUID userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
        return enrollments.stream().map(EnrollmentMapper::mapToEnrollmentDto).collect(Collectors.toList());
    }
}
