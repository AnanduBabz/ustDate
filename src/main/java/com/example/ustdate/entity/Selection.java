package com.example.ustdate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Selection {

	@Id
	@Column
	private Long id;
	
	@Column
	private String selection;
	
}
