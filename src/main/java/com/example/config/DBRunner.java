package com.example.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.entities.Name;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.services.IRoleService;
import com.example.services.IUserService;

@Component
public class DBRunner implements CommandLineRunner {
	@Autowired
	IRoleService roleService;
	
	@Autowired
	IUserService userService;

	@Override
	public void run(String... args) throws Exception {
		if(roleService.retrieveAll().isEmpty()) {
			roleService.add(new Role(1, Name.ADMIN, null));
			roleService.add(new Role(2, Name.USER, null));
		}
		
		if(userService.retrieveAll().isEmpty()) {
			Set<Role> admin = new HashSet<>();
			admin.add(roleService.retrieveById(1));
			
			User u = new User(1, "admin", "admin", "admin", "admin");
			
			System.out.println("aa : " + u.getRoles());
			userService.add(u, 1); 
		} 
	}
	

}
