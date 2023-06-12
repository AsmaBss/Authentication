package pfe.service.authentication;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class DBRunner implements CommandLineRunner {
	
	@Autowired
    RoleRepo roleRepo;

	@Override
	public void run(String... args) throws Exception {
		Role r1 = new Role();
		r1.setId((long) 1);
		r1.setName(Name.ADMIN);
		Role r2 = new Role();
		r2.setId((long) 2);
		r2.setName(Name.USER);
		roleRepo.saveAll(Arrays.asList(
                r1,
                r2)); 
		
	}

}
