package com.vikky.app.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vikky.app.entity.UserEntity;
import com.vikky.app.repo.UsersRepo;

@Service
public class UserInfoUserDetailsService implements UserDetailsService{

	@Autowired
	private UsersRepo usersRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		UserEntity user = usersRepo.findByUserEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
        return user;
	}
	
	
}
