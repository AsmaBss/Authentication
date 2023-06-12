package com.example.services;

import java.util.List;

import com.example.entities.Role;

public interface IRoleService {
	public List<Role> retrieveAll();
	public Role retrieveById(Integer id);
	public void add(Role role);
}
