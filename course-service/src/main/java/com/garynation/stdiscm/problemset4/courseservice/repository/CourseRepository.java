package com.garynation.stdiscm.problemset4.courseservice.repository;

import com.garynation.stdiscm.problemset4.courseservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByFacultyId(UUID facultyId);
}
