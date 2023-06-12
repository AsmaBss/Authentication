package com.example.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.TokenInfo;
import com.example.repositories.TokenInfoRepository;

@Service
public class TokenInfoService implements ITokenInfoService{

	@Autowired
	TokenInfoRepository tokenInfoRepo;

	@Override
	public TokenInfo findById(Integer id) {
		return tokenInfoRepo.findById(id).orElse(null);
	}

	@Override
	public Optional<TokenInfo> findByRefreshToken(String refreshToken) {
		return tokenInfoRepo.findByRefreshToken(refreshToken);
	}

	@Override
	public TokenInfo save(TokenInfo entity) {
		return tokenInfoRepo.save(entity);
	}
	
	@Override
	public void deleteById(Integer id) {
		tokenInfoRepo.deleteById(id);
	}
}
