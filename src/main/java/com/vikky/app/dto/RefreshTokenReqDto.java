package com.vikky.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenReqDto {

	@NotBlank(message = "refresh token is inavild")
	private String refreshToken;
}
