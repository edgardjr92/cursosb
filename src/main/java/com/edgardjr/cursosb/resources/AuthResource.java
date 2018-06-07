package com.edgardjr.cursosb.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edgardjr.cursosb.dto.EmailDTO;
import com.edgardjr.cursosb.security.JWTUtil;
import com.edgardjr.cursosb.security.UserSS;
import com.edgardjr.cursosb.services.AuthService;
import com.edgardjr.cursosb.services.UserService;
import com.edgardjr.cursosb.services.exceptions.AuthorizationException;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;

	@RequestMapping(value="/refresh_token", method=RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated().orElseThrow(() -> new AuthorizationException("Acesso negado"));
		
		String token = this.jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/forgot", method=RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
		this.authService.sendNewPassword(emailDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
}
