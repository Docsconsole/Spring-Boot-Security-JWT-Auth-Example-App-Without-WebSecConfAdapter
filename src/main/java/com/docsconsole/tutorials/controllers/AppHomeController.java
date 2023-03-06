package com.docsconsole.tutorials.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class AppHomeController {

	@GetMapping("/all")
	public String allAccess() {
		return "Access Type: All";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
	public String userAccess() {
		return "Access Type: User";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Access Type: Admin";
	}

	@GetMapping("/super-admin")
	@PreAuthorize("hasRole('SUPER_ADMIN')")
	public String superAdminAccess() {
		return "Access Type: SuperAdmin";
	}

}
