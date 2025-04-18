package com.garynation.stdiscm.problemset4.enrollmentservice.repository;

import com.garynation.stdiscm.problemset4.enrollmentservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
