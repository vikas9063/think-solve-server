package com.vikky.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqDto {

	@NotBlank(message = "User Name is Required")
	private String username;
	@NotBlank(message = "Password is Required")
	private String password;
	
	
}
