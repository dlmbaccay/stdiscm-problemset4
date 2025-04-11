package com.garynation.stdiscm.problemset4.gradeservice.service;

import com.garynation.stdiscm.problemset4.gradeservice.dto.CourseGradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.dto.GradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.dto.UserGradeDto;

import java.util.List;
import java.util.UUID;

public interface GradeService {
    GradeDto addGrade(GradeDto gradeDto);
    GradeDto updateGrade(UUID id, GradeDto gradeDto);
    GradeDto deleteGrade(UUID id);
    GradeDto getGradeById(UUID id);
    GradeDto getGradeByCourseIdAndStudentId(UUID courseId, UUID studentId);
    List<GradeDto> getAllGrade();
    List<UserGradeDto> getAllGradeByCourseId(UUID courseId);
    List<CourseGradeDto> getAllGradeByStudentId(UUID studentId);
}
