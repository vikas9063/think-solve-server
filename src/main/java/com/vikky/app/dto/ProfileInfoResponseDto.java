package com.vikky.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoResponseDto {

	private String status;
	private String message;
	private UserResponseDto userInfo;
	
	
}
