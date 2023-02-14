package com.te.lmsproject.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.te.lmsproject.adminentity.Users;
import com.te.lmsproject.adminrepository.UserRepository;

@Service
public class JwtUsersDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

// this method is used to get the user details
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users users = userRepository.findByUsername(username);

		if (users != null) {
			return new User(users.getUsername(), users.getPassword(),
					Arrays.asList(new SimpleGrantedAuthority("ROLE_" + users.getRole())));
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}