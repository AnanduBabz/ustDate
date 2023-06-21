package com.example.ustdate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ustdate.entity.UserRegistering;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistering, String> {

}
