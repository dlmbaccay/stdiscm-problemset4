package com.garynation.grade_service.controller;

import com.garynation.grade_service.entity.Grade;
import com.garynation.grade_service.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeService.getAllGrades();
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    public Grade getGradeByStudentAndCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return gradeService.getGradeByStudentAndCourse(studentId, courseId);
    }

    @PostMapping
    public Grade assignGrade(@RequestParam Long studentId, @RequestParam Long courseId, @RequestParam String grade) {
        return gradeService.assignGrade(studentId, courseId, grade);
    }
}
