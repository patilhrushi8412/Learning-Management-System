package com.te.lmsproject.admincontroller;

import static com.te.lmsproject.lmsconstants.Constants.ENTER_VALID_USERNAME_AND_PASSWORD;
import static com.te.lmsproject.lmsconstants.Constants.ENTER_VALID_USERNAME_AND_PASSWORD_FOR_TOKEN;
import static com.te.lmsproject.lmsconstants.Constants.GENERATING_TOKEN_USING_USERNAME_AND_PASSWORD;
import static com.te.lmsproject.lmsconstants.Constants.INVALID_CREDENTIALS;
import static com.te.lmsproject.lmsconstants.Constants.REGISTERING_NEW_ROLE;
import static com.te.lmsproject.lmsconstants.Constants.USER_DISABLED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.te.lmsproject.admindto.UserRequest;
import com.te.lmsproject.adminentity.JwtRequest;
import com.te.lmsproject.adminentity.JwtResponse;
import com.te.lmsproject.adminservices.UserServiceImpl;
import com.te.lmsproject.config.JwtTokensUtil;
import com.te.lmsproject.config.JwtUsersDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@Slf4j
public class JwtAutheticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokensUtil jwtTokenUtil;

	@Autowired
	private JwtUsersDetailService userDetailsService;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		log.info(GENERATING_TOKEN_USING_USERNAME_AND_PASSWORD);
		log.warn(ENTER_VALID_USERNAME_AND_PASSWORD_FOR_TOKEN);
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> saveUsers(@RequestBody UserRequest request) {
		log.info(REGISTERING_NEW_ROLE);
		log.warn(ENTER_VALID_USERNAME_AND_PASSWORD);
		return ResponseEntity.ok(userServiceImpl.saveUser(request));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception(USER_DISABLED, e);
		} catch (BadCredentialsException e) {
			throw new Exception(INVALID_CREDENTIALS, e);
		}
	}

	@GetMapping("/logout")
	public ResponseEntity<?> logout() {
		return ResponseEntity.ok(new String("Succesfully Logged Out"));
	}
}
