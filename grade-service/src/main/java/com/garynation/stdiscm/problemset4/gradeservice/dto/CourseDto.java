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
public class CourseDto {
    private UUID id;
    private UUID facultyId;
    private String courseCode;
    private String courseName;
    private Integer maxEnrollees;
    private Integer currentEnrollees;

}
