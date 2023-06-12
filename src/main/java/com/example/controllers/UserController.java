package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.IUserService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/user")
@Api(tags = "USer Controller")
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	IUserService userService;
	
	@GetMapping("/show")
	public ResponseEntity<?> retrieveAll(){
		return ResponseEntity.ok(userService.retrieveAll());
	}
	
	@GetMapping("/show/{id}")
	public ResponseEntity<?> retrieveById(@PathVariable Integer id){
		return ResponseEntity.ok(userService.retrieveById(id));
	}

}
