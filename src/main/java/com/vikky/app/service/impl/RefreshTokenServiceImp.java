package com.vikky.app.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vikky.app.entity.RefreshTokenEntity;
import com.vikky.app.entity.UserEntity;
import com.vikky.app.exception.RefreshTokenExpiredException;
import com.vikky.app.repo.RefreshTokenRepo;
import com.vikky.app.repo.UsersRepo;
import com.vikky.app.service.RefreshTokenService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RefreshTokenServiceImp implements RefreshTokenService {

	@Autowired
	private RefreshTokenRepo refreshTokenRepo;

	@Autowired
	private UsersRepo usersRepo;
	
	// three days
	private static final long REFRESH_TOKEN_VALIDATION_TIME=1000*60*60*24*3;

	@Override
	public RefreshTokenEntity createRefreshToken(String userName) {

		UserEntity user = this.usersRepo.findByUserEmail(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User Not found"));
		RefreshTokenEntity refreshToken = user.getRefreshToken();
		if (refreshToken == null) {
			log.info("-------- Inside if :: token is null ------------");
			refreshToken = RefreshTokenEntity.builder().refreshToken(UUID.randomUUID().toString())
					.expiry(Instant.now().plusMillis(REFRESH_TOKEN_VALIDATION_TIME))
					.user(this.usersRepo.findByUserEmail(userName).get()).build();
		} else {
			log.info("-------- Inside else :: token is not null ------------");
			refreshToken.setExpiry(Instant.now().plusMillis(REFRESH_TOKEN_VALIDATION_TIME));
		}
		user.setRefreshToken(refreshToken);
		try {
			refreshToken = refreshTokenRepo.save(refreshToken);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return refreshToken;
	}

	@Override
	public RefreshTokenEntity verifyRefreshToken(String refreshToken) {
		RefreshTokenEntity refreshTokenObj = refreshTokenRepo.findByRefreshToken(refreshToken)
				.orElseThrow(() -> new RefreshTokenExpiredException("Invalid Refresh Token"));
		if (refreshTokenObj.getExpiry().compareTo(Instant.now()) < 0) {
			refreshTokenRepo.delete(refreshTokenObj);
			throw new RefreshTokenExpiredException("Refresh Token Expired");
		}
		return refreshTokenObj;
	}

}
