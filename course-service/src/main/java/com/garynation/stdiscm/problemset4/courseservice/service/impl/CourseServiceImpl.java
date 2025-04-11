package com.garynation.stdiscm.problemset4.courseservice.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.garynation.stdiscm.problemset4.courseservice.dto.CourseDto;
import com.garynation.stdiscm.problemset4.courseservice.entity.Course;
import com.garynation.stdiscm.problemset4.courseservice.entity.Enrollment;
import com.garynation.stdiscm.problemset4.courseservice.exception.ResourceNotFoundException;
import com.garynation.stdiscm.problemset4.courseservice.mapper.CourseMapper;
import com.garynation.stdiscm.problemset4.courseservice.repository.CourseRepository;
import com.garynation.stdiscm.problemset4.courseservice.repository.EnrollmentRepository;
import com.garynation.stdiscm.problemset4.courseservice.service.CourseService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private EnrollmentRepository enrollmentRepository;
    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = CourseMapper.mapToCourse(courseDto);
        Course savedCourse = courseRepository.save(course);

        return CourseMapper.mapToCourseDto(savedCourse);
    }

    @Override
    public CourseDto updateCourse(UUID id, CourseDto courseDto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        course.setCourseCode(course.getCourseCode());
        course.setCourseName(course.getCourseName());

        Course savedCourse = courseRepository.save(course);

        return CourseMapper.mapToCourseDto(savedCourse);
    }

    @Override
    public CourseDto getCourseById(UUID id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        return CourseMapper.mapToCourseDto(course);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        
        return courses.stream().map(CourseMapper::mapToCourseDto).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getAllCoursesByFacultyId(UUID facultyId) {
        List<Course> courses = courseRepository.findByFacultyId(facultyId);
        return courses.stream().map(CourseMapper::mapToCourseDto).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getAllCoursesByStudentId(UUID studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(studentId);
        List<Course> enrolledCourses = enrollments.stream()
                .map(enrollment -> courseRepository.findById(enrollment.getCourseId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return enrolledCourses.stream().map(CourseMapper::mapToCourseDto).collect(Collectors.toList());
    }

    @Override
    public CourseDto deleteCourse(UUID id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        courseRepository.delete(course);
        return null;
    }
}
