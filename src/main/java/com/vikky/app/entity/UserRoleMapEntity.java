package com.vikky.app.entity;



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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_ROLE_MAP")
@Builder
public class UserRoleMapEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userRoleMapId;
	@ManyToOne(fetch = FetchType.EAGER)
	private UserEntity users;

	
	@ManyToOne(fetch = FetchType.EAGER)
	private RoleEntity roles; 
}
