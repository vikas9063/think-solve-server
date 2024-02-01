package com.vikky.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikky.app.dto.ProfileInfoResponseDto;
import com.vikky.app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@PutMapping("/update")
	public ResponseEntity<?> updateProfile() {
		return ResponseEntity.ok().body("Hi Update .....");
	}

	@GetMapping("/profile-info/{userEmail}")
	public ProfileInfoResponseDto profileInfo(@PathVariable String userEmail) {
		log.info("------ Profile Info : ----------- email : {}", userEmail);
		return this.userService.profileInfo(userEmail);

	}

}
