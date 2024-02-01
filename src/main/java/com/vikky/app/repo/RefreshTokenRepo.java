package com.vikky.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikky.app.entity.RefreshTokenEntity;

public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, Long>{

	public Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
	
}
