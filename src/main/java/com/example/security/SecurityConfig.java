package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUnAuthResponse jwtUnAuthResponse;
	
	private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
	};

	// définir providerSecurity
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		// inMemory
		/*auth.inMemoryAuthentication().withUser("admin").password("{noop}123").authorities("ADMIN").and()
				.withUser("user").password("{noop}123").authorities("USER");*/
	}
	
	// ajouter les permissions
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()..
		http.cors().and().csrf().disable()
		.exceptionHandling()
		.and().httpBasic()
		.authenticationEntryPoint(jwtUnAuthResponse)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeRequests() 
		.antMatchers(AUTH_WHITELIST).permitAll()
		.antMatchers("/auth/**").permitAll()
		//.antMatchers("/user/**").permitAll() //hasRole("USER") // ay haja taht user, ken l user ychoufha
		//.antMatchers("/role/**").permitAll() //hasRole("ADMIN") // ay haja taht role, ken l admin ychoufha
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.and()
		.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class); 
	}
	
	@Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
	
	@Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
	}
}
