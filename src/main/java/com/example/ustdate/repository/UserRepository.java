package com.example.ustdate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ustdate.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	List<User> findByGender(String genderPref);

	List<User> findByGenderAndInChat(String genderPref, String string);

}
