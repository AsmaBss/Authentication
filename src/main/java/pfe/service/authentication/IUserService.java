package pfe.service.authentication;

import java.util.List;

public interface IUserService {
	
	User findUserByUsername(String username);
	User findUserByEmailAddress(String email);
	

	void save(User user , Long idRole );
	
	
	public void assignRolesToUser(Long idUser ,Long idRole );

	void deleteUser(Long id);
	
	List<User> retrieveUsers();
	User retriveUser(Long idUser);
	
	User resetPasswd(String email);
	int findByUserRoleAdmin();
	int findByUserRoleUser();
	String deleteAllUsers();
	User disableAccount(Long id);
	

}
