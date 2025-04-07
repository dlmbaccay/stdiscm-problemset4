package com.garynation.stdiscm.problemset4.gradeservice.mapper;

import com.garynation.stdiscm.problemset4.gradeservice.dto.GradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.entity.Grade;

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
}
