package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Role;
import com.example.repositories.RoleRepository;

@Service
public class RoleService implements IRoleService{
	@Autowired
	RoleRepository roleRepository;

	@Override
	public List<Role> retrieveAll() {
		return (List<Role>) roleRepository.findAll();
	}

	@Override
	public Role retrieveById(Integer id) {
		return roleRepository.findById(id).orElse(null);
	}

	@Override
	public void add(Role role) {
		roleRepository.save(role);
		
	}

}
