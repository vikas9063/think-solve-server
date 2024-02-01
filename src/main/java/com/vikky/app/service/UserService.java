package com.vikky.app.service;


import java.util.Map;

import com.vikky.app.dto.LoginReqDto;
import com.vikky.app.dto.LoginResponseDto;
import com.vikky.app.dto.ProfileInfoResponseDto;
import com.vikky.app.dto.RefreshTokenReqDto;
import com.vikky.app.dto.RegistrationReqDto;

public interface UserService {

	public LoginResponseDto createUser(RegistrationReqDto registrationReqDto);
	public LoginResponseDto doLogin(LoginReqDto loginReqDto);
	LoginResponseDto tokenWithRefreshToken(RefreshTokenReqDto refreshTokenReqDto);
	public ProfileInfoResponseDto profileInfo(String userName);
	
}
