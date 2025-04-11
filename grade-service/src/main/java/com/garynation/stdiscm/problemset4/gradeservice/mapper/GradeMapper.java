package com.garynation.stdiscm.problemset4.gradeservice.mapper;

import com.garynation.stdiscm.problemset4.gradeservice.dto.CourseGradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.dto.GradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.dto.UserGradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.entity.Course;
import com.garynation.stdiscm.problemset4.gradeservice.entity.Grade;
import com.garynation.stdiscm.problemset4.gradeservice.entity.User;

public class GradeMapper {

    public static GradeDto mapToGradeDto(Grade grade) {
        return new GradeDto(
                grade.getId(),
                grade.getStudentId(),
                grade.getCourseId(),
                grade.getGrade()
        );
    }

    public static Grade mapToGrade(GradeDto gradeDto) {
        return new Grade(
                gradeDto.getId(),
                gradeDto.getStudentId(),
                gradeDto.getCourseId(),
                gradeDto.getGrade()
        );
    }
    public static UserGradeDto mapToUserGradeDto(Grade grade, User user) {
        return new UserGradeDto(
            grade.getId(),
            grade.getStudentId(),
            grade.getCourseId(),
            grade.getGrade(),
            user.getFirstName(),
            user.getLastName()
        );
    }
    public static CourseGradeDto mapToCourseGradeDto(Grade grade, Course course) {
        return new CourseGradeDto(
            grade.getCourseId(),
            grade.getId(),
            grade.getStudentId(),
            course.getCourseCode(),
            course.getCourseName(),
            course.getMaxEnrollees(),
            course.getCurrentEnrollees(),
            grade.getGrade()
        );
    }
}
