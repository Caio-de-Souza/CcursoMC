package com.souza.caio.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.souza.caio.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder
					.getContext()
					.getAuthentication()
					.getPrincipal();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
