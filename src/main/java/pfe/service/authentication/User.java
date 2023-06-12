package pfe.service.authentication;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;
	@Column
	private String firstname;
	@Column
	private String name;
	@Column
	private String emailAddress;
	@Column
	private String username;
	private String passwd;
	private String confirmPasswd;
	private boolean enabled; 

	@ManyToMany(fetch = FetchType.EAGER)//, fetch= FetchType.EAGER
	@JsonIgnore
	private Set<Role> roles = new HashSet<>();
}
