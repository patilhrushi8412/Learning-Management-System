package com.te.lmsproject.adminservices;

import static com.te.lmsproject.lmsconstants.Constants.USER_SAVED_SUCCESFULLY;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.te.lmsproject.admindto.UserRequest;
import com.te.lmsproject.adminentity.Users;
import com.te.lmsproject.adminrepository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Users saveUser(UserRequest user) {
		Users newUser = new Users();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setRole(user.getRole());
		log.info(USER_SAVED_SUCCESFULLY);
		return userRepository.save(newUser);
	}
}
