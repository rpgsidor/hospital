package com.venvw.hospital.controller;

import com.venvw.hospital.dto.AccessTokenDto;
import com.venvw.hospital.dto.SignInDto;
import com.venvw.hospital.security.JwtProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/identity")
public class IdentityController {

    private final AuthenticationManager authenticationManager;
    private final JwtProviderService jwtProviderService;

    @Autowired
    public IdentityController(AuthenticationManager authenticationManager, JwtProviderService jwtProviderService) {
        this.authenticationManager = authenticationManager;
        this.jwtProviderService = jwtProviderService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/sign-in")
    public AccessTokenDto signIn(@Valid @RequestBody SignInDto dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            GrantedAuthority authority = authentication.getAuthorities().stream().findFirst().orElse(null);

            Objects.requireNonNull(authority);

            return new AccessTokenDto(jwtProviderService.createToken(dto.getUsername(), authority.getAuthority()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, null, e);
        }
    }
}
