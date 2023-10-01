package com.docuverse.backend.controllers;

import com.docuverse.backend.configuration.UserAuthenticationProvider;
import com.docuverse.backend.dtos.CredentialsDTO;
import com.docuverse.backend.dtos.SignUpDTO;
import com.docuverse.backend.dtos.UserDTO;

import com.docuverse.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signin(@RequestBody @Valid CredentialsDTO credentialsDto) {
        UserDTO userDto = userService.signin(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody @Valid SignUpDTO user) {
        UserDTO createdUser = userService.signup(user);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
