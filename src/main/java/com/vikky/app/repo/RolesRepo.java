package com.vikky.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikky.app.entity.RoleEntity;

public interface RolesRepo extends JpaRepository<RoleEntity, Integer> {	
	
}
