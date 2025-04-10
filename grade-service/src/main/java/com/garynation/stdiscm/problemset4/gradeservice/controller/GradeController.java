package com.garynation.stdiscm.problemset4.gradeservice.controller;

import com.garynation.stdiscm.problemset4.gradeservice.dto.GradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.service.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/grades")
public class GradeController {
    private GradeService gradeService;

    @Operation(summary = "Create grade")
    @PostMapping("/add")
    public ResponseEntity<GradeDto> addGrade(@RequestBody GradeDto gradeDto) {
        GradeDto enrollment = gradeService.addGrade(gradeDto);
        return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
    }

    @Operation(summary = "Update grade")
    @PutMapping("{id}")
    public ResponseEntity<GradeDto> updateGrade(@PathVariable("id") UUID id, @RequestBody GradeDto gradeDto) {
        GradeDto enrollment = gradeService.updateGrade(id, gradeDto);
        return new ResponseEntity<>(enrollment, HttpStatus.OK);
    }

    @Operation(summary = "Delete grade")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable("id") UUID id) {
        gradeService.deleteGrade(id);
        return ResponseEntity.ok("Grade deleted successfully.");
    }

    @GetMapping("{id}")
    public ResponseEntity<GradeDto> getGrade(@PathVariable("id") UUID id) {
        GradeDto grade = gradeService.getGradeById(id);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @Operation(summary = "Get grade by course id and student id")
    @GetMapping("/{courseId}/{studentId}")
    public ResponseEntity<GradeDto> getGradeByCourseIdAndStudentId(@PathVariable("courseId") UUID courseId, @PathVariable("studentId") UUID studentId) {
        GradeDto grade = gradeService.getGradeByCourseIdAndStudentId(courseId, studentId);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @Operation(summary = "Get all grades")
    @GetMapping("/all")
    public ResponseEntity<List<GradeDto>> getAllGrades() {
        List<GradeDto> grades = gradeService.getAllGrade();
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @Operation(summary = "Get all grades by course id")
    @GetMapping("/all/course/{courseId}")
    public ResponseEntity<List<GradeDto>> getAllGradesByCourseId(@PathVariable("courseId") UUID courseId) {
        List<GradeDto> grades = gradeService.getAllGradeByCourseId(courseId);
        return new ResponseEntity<>(grades, HttpStatus.OK);

    }

    @Operation(summary = "Get all grades by student id")
    @GetMapping("/all/student/{studentId}")
    public ResponseEntity<List<GradeDto>> getAllGradesByStudentId(@PathVariable("studentId") UUID studentId) {
        List<GradeDto> grades = gradeService.getAllGradeByStudentId(studentId);
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }




}
