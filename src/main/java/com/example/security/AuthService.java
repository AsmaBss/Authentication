package com.example.security;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.entities.TokenInfo;
import com.example.entities.User;
import com.example.services.ITokenInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	AuthenticationManager authManager;
	@Autowired
	HttpServletRequest httpRequest;
	@Autowired
	ITokenInfoService tokenInfoService;
	@Autowired
	JwtTokenUtils jwtTokenUtils;

	public JwtResponse login(String login, String password) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(login, password));
		log.debug("Valid userDetails credentials.");
		MyUserDetail userDetails = (MyUserDetail) authentication.getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.debug("SecurityContextHolder updated. [login={}]", login);
		TokenInfo tokenInfo = createLoginToken(login, userDetails.getUser().getId());

		return JwtResponse.builder().accessToken(tokenInfo.getAccessToken()).refreshToken(tokenInfo.getRefreshToken())
				.build();
	}

	public TokenInfo createLoginToken(String username, Integer userId) {
		String userAgent = httpRequest.getHeader(HttpHeaders.USER_AGENT);
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String accessTokenId = UUID.randomUUID().toString();
		String accessToken = JwtTokenUtils.generateToken(username, accessTokenId, false);
		log.info("Access token created. [tokenId={}]", accessTokenId);

		String refreshTokenId = UUID.randomUUID().toString();
		String refreshToken = JwtTokenUtils.generateToken(username, refreshTokenId, true);
		log.info("Refresh token created. [tokenId={}]", accessTokenId);

		TokenInfo tokenInfo = new TokenInfo(accessToken, refreshToken);
		tokenInfo.setUser(new User(userId));
		tokenInfo.setUserAgentText(userAgent);
		tokenInfo.setLocalIpAddress(ip.getHostAddress());
		tokenInfo.setRemoteIpAddress(httpRequest.getRemoteAddr());
		// tokenInfo.setLoginInfo(createLoginInfoFromRequestUserAgent());
		return tokenInfoService.save(tokenInfo);
	}

	public AccessToken refreshAccessToken(String refreshToken) {
		if (jwtTokenUtils.isTokenExpired(refreshToken)) {
			return null;
		}
		String userName = jwtTokenUtils.getUsernameFromToken(refreshToken);
		Optional<TokenInfo> refresh = tokenInfoService.findByRefreshToken(refreshToken);
		if (!refresh.isPresent()) {
			return null;
		}
		return new AccessToken(JwtTokenUtils.generateToken(userName, UUID.randomUUID().toString(), false));

	}

	public void logoutUser(String refreshToken) {
		Optional<TokenInfo> tokenInfo = tokenInfoService.findByRefreshToken(refreshToken);
		if (tokenInfo.isPresent()) {
			tokenInfoService.deleteById(tokenInfo.get().getId());
		}
	}

}
