package jsilgado.mecalux.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jsilgado.mecalux.persistence.entity.User;
import jsilgado.mecalux.security.JwtAuthResponseDTO;
import jsilgado.mecalux.security.JwtTokenProvider;
import jsilgado.mecalux.service.UserDetailsServiceImpl;
import jsilgado.mecalux.service.dto.LoginDTO;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/auth")
public class AuthControlller {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Operation(summary = "Login", description = "Check if the username and password are valid. Return token", tags = {
			"AuthControlller" })
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {

		Authentication autenticar = authentication(loginDTO.getUsername(), loginDTO.getPassword());

		// obtenemos el token del jwtTokenProvider
		String token = jwtTokenProvider.generateToken(autenticar);

		return ResponseEntity.ok(new JwtAuthResponseDTO(token));
	}

	private Authentication authentication(String username, String password) {

		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

	}

	@Operation(summary = "Current user", description = "Get the current user data", tags = {
	"AuthControlller" })
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		return (User) userDetailsService.loadUserByUsername(principal.getName());
	}

}
