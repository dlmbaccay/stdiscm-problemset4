package com.garynation.stdiscm.problemset4.enrollmentservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garynation.stdiscm.problemset4.enrollmentservice.dto.EnrollmentDto;
import com.garynation.stdiscm.problemset4.enrollmentservice.service.EnrollmentService;

import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;

@AllArgsConstructor
@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {
    private EnrollmentService enrollmentService;

    @Operation(summary = "Create enrollment")
    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentDto> addEnrollment(@RequestBody EnrollmentDto enrollmentDto) {
        EnrollmentDto enrollment = enrollmentService.addEnrollment(enrollmentDto);
        return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete enrollment")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnrollment(@PathVariable("id") UUID enrollmentId) {
        EnrollmentDto enrollment = enrollmentService.deleteEnrollment(enrollmentId);
        return ResponseEntity.ok("Enrollment deleted succesfully.");
    }

    @Operation(summary = "Get enrollment by id")
    @GetMapping("{id}")
    public ResponseEntity<EnrollmentDto> getEnrollment(@PathVariable("id") UUID enrollmentId) {
        EnrollmentDto enrollment = enrollmentService.getEnrollmentById(enrollmentId);
        return new ResponseEntity<>(enrollment, HttpStatus.OK);
    }

    @Operation(summary = "Get all enrollments")
    @GetMapping("all")
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollments() {
        List<EnrollmentDto> enrollments = enrollmentService.getAllEnrollments();
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @Operation(summary = "Get enrollment by user id")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<EnrollmentDto>> getEnrollmentByUserId(@PathVariable("id") UUID userId) {
        List<EnrollmentDto> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

    @Operation(summary = "Get enrollment by course id")
    @GetMapping("/course/{id}")
    public ResponseEntity<List<EnrollmentDto>> getEnrollmentByCourseId(@PathVariable("id") UUID courseId) {
        List<EnrollmentDto> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);
        return new ResponseEntity<>(enrollments, HttpStatus.OK);
    }

}
