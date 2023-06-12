package pfe.service.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
	//@Query("select * from role where name=:name")
	Role getRoleByName(@Param("name")String name);

}
