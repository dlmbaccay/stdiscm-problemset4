package com.garynation.stdiscm.problemset4.gradeservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.garynation.stdiscm.problemset4.gradeservice.dto.CourseGradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.dto.GradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.dto.UserGradeDto;
import com.garynation.stdiscm.problemset4.gradeservice.entity.Course;
import com.garynation.stdiscm.problemset4.gradeservice.entity.Grade;
import com.garynation.stdiscm.problemset4.gradeservice.entity.User;
import com.garynation.stdiscm.problemset4.gradeservice.exception.ResourceNotFoundException;
import com.garynation.stdiscm.problemset4.gradeservice.mapper.GradeMapper;
import com.garynation.stdiscm.problemset4.gradeservice.repository.CourseRepository;
import com.garynation.stdiscm.problemset4.gradeservice.repository.GradeRepository;
import com.garynation.stdiscm.problemset4.gradeservice.repository.UserRepository;
import com.garynation.stdiscm.problemset4.gradeservice.service.GradeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GradeServiceImpl
implements GradeService {

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
    public List<UserGradeDto> getAllGradeByCourseId(UUID courseId) {
        List<Grade> grades = gradeRepository.getGradeByCourseId(courseId);
        List<User> users = userRepository.findAll();
        List<UserGradeDto> userGradeDtos = new ArrayList<>();
        for (Grade grade : grades) {
            User user = users.stream()
                .filter(u -> u.getId().equals(grade.getStudentId()))
                .findFirst()
                .orElse(null);
            userGradeDtos.add(GradeMapper.mapToUserGradeDto(grade, user));
        }

        return userGradeDtos;
    }

    @Override
    public List<CourseGradeDto> getAllGradeByStudentId(UUID studentId) {
        List<Grade> grades = gradeRepository.getGradeByStudentId(studentId);
        List<Course> courses = courseRepository.findAll();
        List<CourseGradeDto> courseGradeDtos = new ArrayList<>();
        for (Grade grade : grades) {
            Course course = courses.stream()
                .filter(c -> c.getId().equals(grade.getCourseId()))
                .findFirst()
                .orElse(null);
            courseGradeDtos.add(GradeMapper.mapToCourseGradeDto(grade, course));
        }

        return courseGradeDtos;
    }
}
