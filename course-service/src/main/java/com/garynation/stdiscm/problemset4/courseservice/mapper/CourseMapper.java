package com.garynation.stdiscm.problemset4.courseservice.mapper;


import com.garynation.stdiscm.problemset4.courseservice.dto.CourseDto;
import com.garynation.stdiscm.problemset4.courseservice.entity.Course;

public class CourseMapper {

    public static CourseDto mapToCourseDto(Course course) {
        return new CourseDto(
                course.getId(),
                course.getFacultyId(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getMaxEnrollees(),
                course.getCurrentEnrollees()
        );
    }

    public static Course mapToCourse(CourseDto courseDto) {
        return new Course(
                courseDto.getId(),
                courseDto.getFacultyId(),
                courseDto.getCourseCode(),
                courseDto.getCourseName(),
                courseDto.getMaxEnrollees(),
                courseDto.getCurrentEnrollees()
        );
    }

}
