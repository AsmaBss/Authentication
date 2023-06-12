package pfe.service.authentication;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
	@Autowired
	private UserRepo userRepository;
	@Autowired
	private RoleRepo roleRepository;

	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public User findUserByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void save(User user, Long idRole) {
		Role role = roleRepository.findById(idRole).orElseThrow(null);

		user.setPasswd(bCryptPasswordEncoder.encode(user.getPasswd()));
		user.setConfirmPasswd(bCryptPasswordEncoder.encode(user.getConfirmPasswd()));
		user.setEnabled(true);
		
		role.getUsers().add(user);
	    user.getRoles().add(role);
	    userRepository.save(user);
		// user.setRoles(new HashSet<>(roleRepository.findAll()));

	}

	@Override
	public void deleteUser(Long id) {

		userRepository.deleteById(id);
	}

	@Override
	public void assignRolesToUser(Long idUser, Long idRole) {

		User user = userRepository.findById(idUser).get();
		Role role = roleRepository.findById(idRole).get();

		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Override
	public List<User> retrieveUsers() {

		return userRepository.findAll();
	}

	@Override
	public User retriveUser(Long idUser) {

		return userRepository.findById(idUser).orElse(null);
	}

	@Override
	public User findUserByEmailAddress(String email) {
		return userRepository.findByEmailAddress(email);
	}

	@Override
	public User resetPasswd(String email) {
		User u = userRepository.findByEmailAddress(email);
		if (u==null){
			return (null);
		}
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

		String accessToken = JWT.create().withSubject(email)
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.sign(algorithm);
		
		System.out.println("completed");
		return u;
	}

	@Override
	public int findByUserRoleAdmin() {
		
		return userRepository.findByRolesName(Name.ADMIN).size();
		
	}
	@Override
	public int findByUserRoleUser() {
		
		return userRepository.findByRolesName(Name.USER).size();
		
	}

	

	@Override
	public User disableAccount(Long id) {
		User u = userRepository.findById(id).orElse(null);
		u.setEnabled(false);
		userRepository.save(u);
		return u;
	}
	
	public String deleteAllUsers(){
		List<User> users = retrieveUsers();
		for(User u : users){
			
			deleteUser(u.getIdUser());
			
		}
		return "Uesers removed";
	}


}
