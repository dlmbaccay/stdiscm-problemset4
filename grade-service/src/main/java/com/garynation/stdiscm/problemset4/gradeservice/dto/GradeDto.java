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
public class GradeDto {
    private UUID id;
    private UUID studentId;
    private UUID courseId;
    private Double grade;
}
