package com.example.security;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	@NotEmpty
	private String login;
	
	@NotEmpty
	private String password;

}
