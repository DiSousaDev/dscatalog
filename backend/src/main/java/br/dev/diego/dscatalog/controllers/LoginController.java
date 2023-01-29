package br.dev.diego.dscatalog.controllers;

import br.dev.diego.dscatalog.controllers.dto.TokenJWTResponse;
import br.dev.diego.dscatalog.controllers.dto.UserLoginDto;
import br.dev.diego.dscatalog.entities.User;
import br.dev.diego.dscatalog.services.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenJWTResponse> getLoginToken(@RequestBody UserLoginDto loginDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        return ResponseEntity.ok(tokenService.generateToken((User) auth.getPrincipal()));
    }

}
