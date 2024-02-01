package com.vikky.app.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

	private String accessToken;
	private String refreshToken;
	private Instant refreshTokenExpiresOn;
	private String username;
	private String status;
	private String message;
	
	
}
