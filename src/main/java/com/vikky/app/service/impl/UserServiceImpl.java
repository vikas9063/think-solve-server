package com.vikky.app.service.impl;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikky.app.dto.LoginReqDto;
import com.vikky.app.dto.LoginResponseDto;
import com.vikky.app.dto.ProfileInfoResponseDto;
import com.vikky.app.dto.RefreshTokenReqDto;
import com.vikky.app.dto.RegistrationReqDto;
import com.vikky.app.dto.UserProfileResponse;
import com.vikky.app.dto.UserResponseDto;
import com.vikky.app.entity.CredentialsEntity;
import com.vikky.app.entity.ProfileEntity;
import com.vikky.app.entity.RefreshTokenEntity;
import com.vikky.app.entity.RoleEntity;
import com.vikky.app.entity.UserDesignationEntity;
import com.vikky.app.entity.UserEntity;
import com.vikky.app.entity.UserOtherDetailsEntity;
import com.vikky.app.entity.UserRoleMapEntity;
import com.vikky.app.exception.ResourceNotProcessedException;
import com.vikky.app.repo.RolesRepo;
import com.vikky.app.repo.UsersRepo;
import com.vikky.app.service.RefreshTokenService;
import com.vikky.app.service.UserService;
import com.vikky.app.utils.FileUtils;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UsersRepo usersRepo;

	@Autowired
	private RolesRepo rolesRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTServiceUtil jwtService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public LoginResponseDto createUser(RegistrationReqDto registrationReqDto) {
		this.usersRepo.findByUserEmail(registrationReqDto.getUserEmail()).ifPresent(user -> {
			throw new ResourceNotProcessedException("User with the given email already exists");
		});
		UserEntity user = this.modelMapper.map(registrationReqDto, UserEntity.class);
		user.setEnabled(true);
		user.setModifiedOn(new Timestamp(System.currentTimeMillis()));
		user.setUserId(UUID.randomUUID().toString());

//		Set Credentials
		CredentialsEntity userCredentials = new CredentialsEntity();
		userCredentials.setPassword(this.encoder.encode(registrationReqDto.getPassword()));
		userCredentials.setUser(user);

		user.setCredentials(userCredentials);

//		Create Role
		RoleEntity roles = new RoleEntity();
		roles.setRoleId(1010);
		roles.setRoleDesc("This Is a Normal User");
		roles.setRoleTitle("NORMAL_USER");

		UserRoleMapEntity usersRole = new UserRoleMapEntity();
		usersRole.setRoles(roles);
		usersRole.setUsers(user);

		Set<UserRoleMapEntity> usersRoles = new HashSet<>();
		usersRoles.add(usersRole);

//		Set User Roles		
		user.setUserRoles(usersRoles);
		user.setUserRegDate(new Date(System.currentTimeMillis()).toString());

		try {
			this.rolesRepo.save(roles);
			user = this.usersRepo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotProcessedException("Registration Failed ...");
		}
		RefreshTokenEntity refreshToken = this.refreshTokenService.createRefreshToken(user.getUserEmail());
		return LoginResponseDto.builder().accessToken(this.jwtService.generateToken(user.getUsername()))
				.refreshToken(refreshToken.getRefreshToken()).refreshTokenExpiresOn(refreshToken.getExpiry())
				.username(user.getUsername()).status("success")
				.message("User created and token generated successfully...").build();

	}

	@Override
	public LoginResponseDto doLogin(LoginReqDto loginReqDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginReqDto.getUsername(), loginReqDto.getPassword()));
		if (authentication.isAuthenticated()) {
			String token = jwtService.generateToken(loginReqDto.getUsername());
			RefreshTokenEntity refreshToken = this.refreshTokenService.createRefreshToken(loginReqDto.getUsername());
			return LoginResponseDto.builder().accessToken(token).refreshToken(refreshToken.getRefreshToken())
					.refreshTokenExpiresOn(refreshToken.getExpiry()).username(loginReqDto.getUsername())
					.status("success").message("token generated successfully...").build();

		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}

	}

	@Override
	public LoginResponseDto tokenWithRefreshToken(RefreshTokenReqDto refreshTokenReqDto) {
		RefreshTokenEntity verifyRefreshToken = this.refreshTokenService
				.verifyRefreshToken(refreshTokenReqDto.getRefreshToken());
		return LoginResponseDto.builder()
				.accessToken(this.jwtService.generateToken(verifyRefreshToken.getUser().getUserEmail()))
				.message("Token Generated Successfully ...").refreshToken(verifyRefreshToken.getRefreshToken())
				.refreshTokenExpiresOn(verifyRefreshToken.getExpiry()).status("success")
				.username(verifyRefreshToken.getUser().getUsername()).build();

	}

	@Override
	public ProfileInfoResponseDto profileInfo(String userName) {
		try {
			UserEntity user = this.usersRepo.findByUserEmail(userName)
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			Set<UserDesignationEntity> userDesignation = user.getUserDesignation();
			// sorting based on date
			userDesignation = userDesignation.stream()
	        .sorted(Comparator.comparing(UserDesignationEntity::getDesignatedOn).reversed())
	        .collect(Collectors.toSet());

			UserOtherDetailsEntity otherDetails = user.getOtherDetails();

			UserResponseDto userResponse = this.modelMapper.map(user, UserResponseDto.class);
			userResponse.setUserDesignation(userDesignation);
			userResponse.setOtherDetails(otherDetails);
			userResponse.setUserStatus(user.isEnabled());
			ProfileEntity profile = user.getProfile();
			log.info("--------- user Profile ---------- :: {}", profile);
			if (profile != null) {
				String fileBase64 = FileUtils.getFileBase64(profile.getProfileImgLocation());
				userResponse.setProfile(UserProfileResponse.builder().imageBase64(fileBase64)
						.imageExtension(profile.getImageExtension()).build());				
			}
			return ProfileInfoResponseDto.builder().message("Profile Info fetched successfully").status("success")
					.userInfo(userResponse).build();
		} catch (Exception e) {
			log.error("------------------ error ----------- : {}", e.getMessage());
			throw new ResourceNotProcessedException("Something went wrong , Profile Info");
		}

	}

}
