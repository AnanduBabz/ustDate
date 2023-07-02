package com.example.ustdate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ustdate.entity.PreviousSelection;

@Repository
public interface PreviousSelectionRepository extends JpaRepository<PreviousSelection, String>{

}
