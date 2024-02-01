package com.vikky.app.dto;

import java.util.Set;

import com.vikky.app.entity.ProfileEntity;
import com.vikky.app.entity.UserDesignationEntity;
import com.vikky.app.entity.UserOtherDetailsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

	private String userId;
	private String userFName;
	private String userLName;
	private String userEmail;
	private String gender;
	private String userDesc;
	private String modifiedOn;
	private String userRegDate;
	private boolean userStatus;
	private String mobileNo;
	private UserOtherDetailsEntity otherDetails;
	private Set<UserDesignationEntity> userDesignation;
	private UserProfileResponse profile;
	
}
