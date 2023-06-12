package com.example.services;

import java.util.Optional;

import com.example.entities.TokenInfo;

public interface ITokenInfoService {
	public TokenInfo findById(Integer id);
	public Optional<TokenInfo> findByRefreshToken(String refreshToken);
	public TokenInfo save(TokenInfo entity);
	public void deleteById(Integer id);

}
