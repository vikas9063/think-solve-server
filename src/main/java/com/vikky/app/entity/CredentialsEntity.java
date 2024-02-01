package com.vikky.app.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_CREDENTIALS")
public class CredentialsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int credentialsId;
	private String password;
	
	@OneToOne
	private UserEntity user;
}
