package com.demo.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.blog.exceptions.ResourceNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
/**
 * this class runs before any request
 * checks that the request is authenticated or not
 * if not then it not allow to hit our request to hit controllers
 * if user is not authenticated that time JwtAuthenticationEntryPoint class commence method
 * @author Samundar Singh Rathore
 *
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

        //1. get token  form header 
		String requestToken = request.getHeader("Authorization");
		
		// Bearer 2352523sdgsg
		System.out.println(requestToken);

		String username = null;

		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer")) {

			//remove Bearer from token
			token = requestToken.substring(7);

			try {
				//get user name from jwt token
				username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT token has expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid JWT token");
			}

		} else {
			System.out.println("JWT token does not begin with Bearer");
		}

		// once we get the token , now validate the token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			//fetching the user details from userDetails
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if (this.jwtTokenHelper.validateToken(token, userDetails)) {
				
				// All things working fine
				// now we have do authentication
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			} else {
				System.out.println("invalid jwt");
			}

		} else {
			System.out.println("username is null");
		}

		//after successfully authentication only the filter allow to go request
		filterChain.doFilter(request, response);
	}

}
