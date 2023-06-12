package com.example.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{
	
}
