package com.garynation.stdiscm.problemset4.gradeservice.service.impl;

import com.garynation.stdiscm.problemset4.gradeservice.dto.GradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.entity.Grade;
import com.garynation.stdiscm.problemset4.gradeservice.exception.ResourceNotFoundException;
import com.garynation.stdiscm.problemset4.gradeservice.mapper.GradeMapper;
import com.garynation.stdiscm.problemset4.gradeservice.repository.CourseRepository;
import com.garynation.stdiscm.problemset4.gradeservice.repository.EnrollmentRepository;
import com.garynation.stdiscm.problemset4.gradeservice.repository.GradeRepository;
import com.garynation.stdiscm.problemset4.gradeservice.repository.UserRepository;
import com.garynation.stdiscm.problemset4.gradeservice.service.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GradeServiceImpl
implements GradeService {

    private EnrollmentRepository enrollmentRepository;
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private GradeRepository gradeRepository;

    @Override
    public GradeDto addGrade(GradeDto gradeDto) {
        Grade grade = GradeMapper.mapToGrade(gradeDto);
        Grade savedGrade = gradeRepository.save(grade);

        return GradeMapper.mapToGradeDto(savedGrade);
    }

    @Override
    public GradeDto updateGrade(UUID id, GradeDto gradeDto) {
        Grade grade = gradeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Grade not found."));
        grade.setGrade(gradeDto.getGrade());

        Grade savedGrade = gradeRepository.save(grade);
        return GradeMapper.mapToGradeDto(savedGrade);
    }

    @Override
    public GradeDto deleteGrade(UUID id) {
        Grade grade = gradeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Grade not found."));
        gradeRepository.delete(grade);
        return null;
    }

    @Override
    public GradeDto getGradeById(UUID id) {
        Grade grade = gradeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Grade not found."));

        return GradeMapper.mapToGradeDto(grade);
    }

    @Override
    public GradeDto getGradeByCourseIdAndStudentId(UUID courseId, UUID studentId) {
        Grade grade = gradeRepository.getGradeByCourseIdAndStudentId(courseId, studentId);

        return GradeMapper.mapToGradeDto(grade);
    }

    @Override
    public List<GradeDto> getAllGrade() {
        List<Grade> gradeList = gradeRepository.findAll();
        return gradeList.stream().map(GradeMapper::mapToGradeDto).collect(Collectors.toList());
    }

    @Override
    public List<GradeDto> getAllGradeByCourseId(UUID courseId) {
        List<Grade> grades = gradeRepository.getGradeByCourseId(courseId);

        return grades.stream().map(GradeMapper::mapToGradeDto).collect(Collectors.toList());
    }

    @Override
    public List<GradeDto> getAllGradeByStudentId(UUID studentId) {
        List<Grade> grades = gradeRepository.getGradeByStudentId(studentId);
        return grades.stream().map(GradeMapper::mapToGradeDto).collect(Collectors.toList());
    }
}
