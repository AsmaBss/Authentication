package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;

@Service
public class UserService implements IUserService{
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public List<User> retrieveAll() {
		return (List<User>) userRepository.findAll();
	}
	
	@Override
	public User retrieveById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void add(User user, Integer idRole) {
		/*user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user); */

		Role role = roleRepository.findById(idRole).get();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		role.getUsers().add(user);
		System.out.println("role " + user.toString());
		user.getRoles().add(role); 
		System.out.println("test " + user.getRoles());
		userRepository.save(user);
	}   
	

}
