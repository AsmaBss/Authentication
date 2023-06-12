package com.example.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.TokenInfo;

@Repository
public interface TokenInfoRepository extends JpaRepository<TokenInfo, Integer> {
	
	Optional<TokenInfo> findByRefreshToken (String refreshToken);

}
