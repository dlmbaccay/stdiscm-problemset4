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
public class UserGradeDto {
    private UUID id;
    private UUID studentId;
    private UUID courseId;
    private Double grade;
    private String firstName;
    private String lastName;
}
