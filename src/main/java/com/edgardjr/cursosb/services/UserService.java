package com.edgardjr.cursosb.services;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;

import com.edgardjr.cursosb.security.UserSS;

public class UserService {
	
	public static Optional<UserSS> authenticated() {
		UserSS userSS;
		try {
			userSS = (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			userSS = null;
		}
		
		return Optional.ofNullable(userSS);
	}
}
