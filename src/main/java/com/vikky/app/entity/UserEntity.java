package com.vikky.app.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class UserEntity implements UserDetails {

	private static final long serialVersionUID = 2628847630456086764L;
	
	@Id
	@Column(nullable = false)
	private String userId;
	@Column(nullable = false)
	private String userFName;
	@Column(nullable = false)
	private String userLName;
	@Column(nullable = false,unique = true)
	private String userEmail;
	@Column(nullable = false)
	private String userGender;
	@Column(nullable = false, length = 1000)
	private String userDesc;
	private boolean isEnabled;
	@Column(nullable = false, length = 15)
	private String mobileNo;
	
	private Timestamp modifiedOn;
	private String modifiedBy;
	@Column(nullable = false)
	private String userRegDate;
	private boolean isDeleted;
	
	
	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private RefreshTokenEntity refreshToken;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user" , fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private CredentialsEntity credentials;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserRoleMapEntity> userRoles = new HashSet<UserRoleMapEntity>();
	
	
	@OneToOne(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private ProfileEntity profile;
	
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<UserDesignationEntity> userDesignation;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserOtherDetailsEntity otherDetails;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userRoles.stream().map((role) -> new SimpleGrantedAuthority(role.getRoles().getRoleTitle()))
				.collect(Collectors.toList());		
	}


	@Override
	public String getPassword() {
		return this.credentials.getPassword();
	}


	@Override
	public String getUsername() {
		return this.userEmail;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
