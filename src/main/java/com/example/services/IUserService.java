package com.example.services;

import java.util.List;

import com.example.entities.User;

public interface IUserService {
	
	public List<User> retrieveAll();
	public User retrieveById(Integer id);
	
	public void add(User user, Integer idRole);

}
