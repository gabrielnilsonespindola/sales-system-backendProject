package com.gabrielnilsonespindola.salesSystem.resources;

import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.gabrielnilsonespindola.salesSystem.entities.Role;
import com.gabrielnilsonespindola.salesSystem.repositories.UserRepository;
import com.gabrielnilsonespindola.salesSystem.resources.dto.LoginRequest;
import com.gabrielnilsonespindola.salesSystem.resources.dto.LoginResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TokenController {
	
	@Autowired
	private final JwtEncoder jwtEncoder;
	
	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public TokenController(JwtEncoder jwtEncoder , UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.jwtEncoder = jwtEncoder;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	 @PostMapping("/login")
	    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		 log.info("Inicio login");

	        var user = userRepository.findByUsername(loginRequest.username());
	        
	        if(user.isEmpty() || !user.get().isLoginCorrect(loginRequest , bCryptPasswordEncoder)) {
	        	log.warn("Login não efetuado, usuario ou senha invalidos");
	        	throw new BadCredentialsException("user or password is invalid");	        	
	        }
	        
	        var scopes = user.get().getRoles()
	        		.stream()
	        		.map(Role::getName)
	        		.collect(Collectors.joining(" "));
	        				
	        
	        var now = Instant.now();
	        var expiresIn = 300L;
	        
	        var claims = JwtClaimsSet.builder()
	        		.issuer("mybackend")
	        		.subject(user.get().getId().toString())
	        		.issuedAt(now)
	        		.expiresAt(now.plusSeconds(expiresIn))
	        		.claim("scope", scopes)
	                .build();
	        
	        var  jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	        
	        log.info("Login efetuado com Sucesso");	        		
	        return ResponseEntity.ok(new LoginResponse(jwtValue , expiresIn));	      
		
	}

}
