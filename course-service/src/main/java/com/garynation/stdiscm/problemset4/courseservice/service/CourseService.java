package com.garynation.stdiscm.problemset4.courseservice.service;

import com.garynation.stdiscm.problemset4.courseservice.dto.CourseDto;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    CourseDto createCourse(CourseDto courseDto);
    CourseDto updateCourse(UUID id, CourseDto courseDto);
    CourseDto getCourseById(UUID id);
    List<CourseDto> getAllCoursesByFacultyId(UUID facultyId);
    List<CourseDto> getAllCoursesByStudentId(UUID studentId);
    List<CourseDto> getAllCourses();
    CourseDto deleteCourse(UUID id);
}
