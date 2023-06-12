package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Role;
import com.example.entities.User;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;
import com.example.services.IUserService;
import com.example.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private IUserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
		JwtResponse jwtResponseDto = authService.login(loginRequest.getLogin(), loginRequest.getPassword());
		return ResponseEntity.ok(jwtResponseDto);
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<AccessToken> refreshAccessToken(@RequestParam String refreshToken) {
		AccessToken dto = authService.refreshAccessToken(refreshToken);
		return ResponseEntity.ok(dto);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestParam String refreshToken) {
		authService.logoutUser(refreshToken);
		return ResponseEntity.ok(null);
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/register/{idRole}")
    public void registerUser(@RequestBody User user, @PathVariable Integer idRole) {
		userService.add(user, idRole);
    }
	
}
