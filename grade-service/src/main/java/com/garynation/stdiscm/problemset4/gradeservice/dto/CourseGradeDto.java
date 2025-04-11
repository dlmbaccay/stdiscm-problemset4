package com.garynation.stdiscm.problemset4.gradeservice.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseGradeDto {
    private UUID courseId;
    private UUID gradeId;
    private UUID studentId;
    private String courseCode;
    private String courseName;
    private Integer maxEnrollees;
    private Integer currentEnrollees;
    private Double grade;
}
