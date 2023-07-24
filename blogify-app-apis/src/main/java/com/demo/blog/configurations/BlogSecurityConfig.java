package com.demo.blog.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.blog.reposistories.RoleRepo;
import com.demo.blog.security.CustomUserDetailService;
import com.demo.blog.security.JwtAuthenticationEntryPoint;
import com.demo.blog.security.JwtAuthenticationFilter;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BlogSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService detailService;
	

	
	
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//for basic authentication configuration
		//http.cors().disable().authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();
		
		//for jwt authentication configuration
		http
		.csrf()  //csrf token
		.disable()  //disable csrf token
		.authorizeHttpRequests()  //for authorization of http requests
		.antMatchers("/api/v1/auth/**").permitAll() //make urls public
		.anyRequest()  //which request to authorize
		.authenticated()  //we have to authenticate otherwise exception come
		.and()
		.exceptionHandling()  //handle exception when any unauthorized exception come
		.authenticationEntryPoint(entryPoint)  //entry point for checking authorization like watchman
		.and() //session management policy
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//add filter
		http.addFilterBefore(this.authenticationFilter,UsernamePasswordAuthenticationFilter.class);
		
	}
	
	
	//configure authentication manager
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.detailService).passwordEncoder(passwordEncoder());
	}
	
	//create encrpted password
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
