package com.example.security;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtTokenUtils {
	private static String TOKEN_SECRET;
	private static Long ACCESS_TOKEN_VALIDITY;
	private static Long REFRESH_TOKEN_VALIDITY;
	
	public JwtTokenUtils(@Value("${auth.secret}") String secret, 
			@Value("${auth.access.expiration}") Long accessValidity, 
			@Value("${auth.refresh.expiration}") Long refreshValidity) {
		Assert.notNull(accessValidity, "Validity must not be null");
		Assert.hasText(secret, "Validity must not be null or empty");
		
		TOKEN_SECRET = secret;
		ACCESS_TOKEN_VALIDITY = accessValidity;
		REFRESH_TOKEN_VALIDITY = refreshValidity;
		
	}

	public static String generateToken(final String username, final String tokenId, boolean isRefresh) {
		return Jwts.builder()
				.setId(tokenId)
				.setSubject(username)
				.setIssuedAt(new Date())
				.setIssuer("app-Service")
				.setExpiration(calcTokenExpirationDate(isRefresh))
				.claim("created", Calendar.getInstance().getTime())
				.signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
				.compact();
	}

	private static Date calcTokenExpirationDate(boolean isRefresh) {
		return new Date(System.currentTimeMillis() + (isRefresh ? REFRESH_TOKEN_VALIDITY : ACCESS_TOKEN_VALIDITY) * 1000);
	}
	
	public String getUsernameFromToken(String token) {
		Claims claims = getClaims(token);
		return claims.getSubject();
	}
	
	public String getTokenIdFromToken(String token) {
		return getClaims(token).getId();
	}
	
	public boolean isTokenValid(String token, MyUserDetail userDetail) {
		log.info("isTokenExpired >>> " + isTokenExpired(token));
		String username = getUsernameFromToken(token);
		log.info("username from token >>> " + username);
		log.info("userDetails.getUsername >>> " + userDetail.getUsername());
		log.info("username = >>> userDetails.getUsername >>> " + username.equals(userDetail.getUsername()));
		Boolean isUsernameEqual = username.equalsIgnoreCase(userDetail.getUsername());
		return (isUsernameEqual && !isTokenExpired(token));
	}

	public boolean isTokenExpired(String token) {
		Date expiration = getClaims(token).getExpiration();
		return expiration.before(new Date());
	}

	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token).getBody();
	}
	
	public boolean validateToken(String token, HttpServletRequest httpServletRequest) {
		try {
			Jwts.parser().setSigningKey(TOKEN_SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			log.info("Invalid JWT Signature");
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token");
			httpServletRequest.setAttribute("expired", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT exception");
		} catch (IllegalArgumentException e) {
			log.info("Jxt claims string is empty");
		}
		return false;
	}
	

}
