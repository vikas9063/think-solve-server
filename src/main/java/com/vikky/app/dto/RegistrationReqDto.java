package com.vikky.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationReqDto {

	private String userId;
	@NotBlank(message = "first name is mandatory")
	private String userFName;
	private String userLName;
	@NotBlank(message = "email is mandatory")
	@Email(message = "Invalid Email")
	private String userEmail;
	@NotBlank(message = "gender is mandatory")
	private String userGender;
	private String userDesc;
	@Pattern(regexp = "^(?=.*[a-z]|[A-Z]|[0-9])(?=\\S+$).{8,}$", message = "Please Enter a valid Password")
	private String password;
	@Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$", message = "Please Enter a valid Phone No")
	@NotBlank(message = "mobileNo is mandatory")
	private String mobileNo;

}
