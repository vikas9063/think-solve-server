package com.vikky.app.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USER_DESIGNATION")
public class UserDesignationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userDesginationId;
	private String designation;
	private String designationDescription;
	private Timestamp modifiedOn;
	private String modifiedBy;
	private Timestamp designatedOn;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private UserEntity user;
	
	
}
