package pfe.service.authentication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
	User findByEmailAddress(String email);
	
	List<User> findByRolesName(Name name);
	
	
	

}
