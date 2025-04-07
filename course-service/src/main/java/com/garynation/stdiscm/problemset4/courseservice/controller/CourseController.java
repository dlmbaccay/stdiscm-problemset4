package com.garynation.stdiscm.problemset4.courseservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garynation.stdiscm.problemset4.courseservice.dto.CourseDto;
import com.garynation.stdiscm.problemset4.courseservice.service.CourseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private CourseService courseService;

    @PostMapping("create")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        CourseDto course = courseService.createCourse(courseDto);
        return new ResponseEntity<>(course, HttpStatus.CREATED);

    }

    @GetMapping("{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable("id") UUID courseId) {

        CourseDto course = courseService.getCourseById(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<CourseDto> getCourseByFacultyId(@PathVariable("id") UUID courseId) {
        CourseDto course = courseService.getCourseById(courseId);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable("id") UUID courseId, @RequestBody CourseDto courseDto) {
        CourseDto course = courseService.updateCourse(courseId, courseDto);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") UUID courseId) {
        CourseDto course = courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted succesfully.");
    }
}
