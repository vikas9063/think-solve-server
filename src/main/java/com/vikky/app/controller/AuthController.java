package com.vikky.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikky.app.dto.LoginReqDto;
import com.vikky.app.dto.LoginResponseDto;
import com.vikky.app.dto.RefreshTokenReqDto;
import com.vikky.app.dto.RegistrationReqDto;
import com.vikky.app.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<LoginResponseDto> doRegister(@Valid @RequestBody RegistrationReqDto dto) {
		log.info(" ----- Registration Request  ------- : {}", dto);
		return ResponseEntity.ok().body(this.userService.createUser(dto));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> authenticateAndGetToken(@RequestBody LoginReqDto authRequest) {
		return ResponseEntity.ok().body(this.userService.doLogin(authRequest));
	}
	
	@PostMapping("/token")
	public ResponseEntity<LoginResponseDto> tokenWithRefreshToken(@Valid @RequestBody RefreshTokenReqDto authRequest) {
		return ResponseEntity.ok().body(this.userService.tokenWithRefreshToken(authRequest));
	}
	

}
