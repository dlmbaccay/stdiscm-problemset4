package com.garynation.stdiscm.problemset4.gradeservice.repository;

import com.garynation.stdiscm.problemset4.gradeservice.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GradeRepository extends JpaRepository<Grade, UUID> {
    Grade getGradeByCourseIdAndStudentId(UUID courseId, UUID studentId);
    List<Grade> getGradeByCourseId(UUID courseId);
    List<Grade> getGradeByStudentId(UUID studentId);
}
