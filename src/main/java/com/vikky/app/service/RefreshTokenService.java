package com.vikky.app.service;

import com.vikky.app.entity.RefreshTokenEntity;

public interface RefreshTokenService {

	
	public RefreshTokenEntity createRefreshToken(String userName);
	public RefreshTokenEntity verifyRefreshToken(String refreshToken);
}
