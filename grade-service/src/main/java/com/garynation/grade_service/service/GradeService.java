package com.garynation.grade_service.service;

import com.garynation.grade_service.entity.Grade;
import com.garynation.grade_service.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade getGradeByStudentAndCourse(Long studentId, Long courseId) {
        return gradeRepository.findById(studentId).orElse(null);  // You can create custom queries if needed
    }

    public Grade assignGrade(Long studentId, Long courseId, String grade) {
        Grade gradeEntry = new Grade();
        gradeEntry.setStudentId(studentId);
        gradeEntry.setCourseId(courseId);
        gradeEntry.setGrade(grade);
        return gradeRepository.save(gradeEntry);
    }
}
