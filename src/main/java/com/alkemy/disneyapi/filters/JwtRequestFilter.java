package com.alkemy.disneyapi.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alkemy.disneyapi.security.UserDetailsCustomService;
import com.alkemy.disneyapi.services.JwtUtils;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsCustomService userDetailsCustomService;
	@Autowired
	private JwtUtils jwtUtil;
	@Lazy
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		final String authorizationHeader = request.getHeader("Authorization");

		String username = null;
		String jwt = null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails details = this.userDetailsCustomService.loadUserByUsername(username);
			
			if(jwtUtil.validateToken(jwt, details)) {
				UsernamePasswordAuthenticationToken authReq =
						new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), details.getAuthorities());

				authReq.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authReq);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
