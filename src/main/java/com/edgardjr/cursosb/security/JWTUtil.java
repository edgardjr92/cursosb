package com.edgardjr.cursosb.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + this.expiration))
				.signWith(SignatureAlgorithm.HS512, this.secret.getBytes())
				.compact();
	}

	public boolean isValidToken(String token) {
		Claims claims = this.getClaims(token);
		
		if (claims != null) {
			String username = claims.getSubject();
			Date expiration = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			if (!StringUtils.isEmpty(username) && expiration != null && now.before(expiration)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String getUsername(String token) {
		Claims claims = this.getClaims(token);
		
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(this.secret.getBytes())
					.parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}
}
