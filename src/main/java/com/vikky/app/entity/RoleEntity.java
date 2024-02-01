package com.vikky.app.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;
	private String roleTitle;
	private String roleDesc;
	
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "roles")
	private Set<UserRoleMapEntity> userRoles = new HashSet<UserRoleMapEntity>();
	
}
