package com.example.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.security.MyUserDetail;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user =  userRepository.findByEmail(username);
		if(!user.isPresent()) {
			throw new UsernameNotFoundException("This User not found with selected username: "+ username);
		}
		//return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), getAuthorities(user.get()));
		return new MyUserDetail(user.get());
	}

	private static List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(!user.getRoles().isEmpty()) {
			user.getRoles().forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role.getName().name()));
				});
		}
		return authorities;
	}
	
}
