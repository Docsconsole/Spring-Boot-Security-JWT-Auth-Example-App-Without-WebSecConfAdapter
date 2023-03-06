package com.docsconsole.tutorials.controllers;

import com.docsconsole.tutorials.models.ERole;
import com.docsconsole.tutorials.models.Role;
import com.docsconsole.tutorials.models.User;
import com.docsconsole.tutorials.payload.request.AppLoginRequest;
import com.docsconsole.tutorials.payload.request.AppSignupRequest;
import com.docsconsole.tutorials.payload.response.AppJwtResponse;
import com.docsconsole.tutorials.payload.response.AppMessageResponse;
import com.docsconsole.tutorials.repository.RoleRepository;
import com.docsconsole.tutorials.repository.UserRepository;
import com.docsconsole.tutorials.security.jwt.JWTAuthHelper;
import com.docsconsole.tutorials.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AppAuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JWTAuthHelper jwtHelper;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody AppLoginRequest appLoginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(appLoginRequest.getUsername(), appLoginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtHelper.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new AppJwtResponse(jwt,
												 userDetails.getId(),
												 userDetails.getUsername(),
												 userDetails.getEmail(),
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody AppSignupRequest signUpRequestUser) {
		if (userRepository.existsByUsername(signUpRequestUser.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new AppMessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequestUser.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new AppMessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequestUser.getUsername(),
							 signUpRequestUser.getEmail(),
							 encoder.encode(signUpRequestUser.getPassword()));

		Set<String> strRoles = signUpRequestUser.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "superadmin":
					Role modRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new AppMessageResponse("User registered successfully!"));
	}
}
