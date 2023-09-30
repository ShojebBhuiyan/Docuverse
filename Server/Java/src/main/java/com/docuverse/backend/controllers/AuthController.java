package com.docuverse.backend.controllers;

import com.docuverse.backend.models.SignInResponseDTO;
import com.docuverse.backend.models.SignUpDTO;
import com.docuverse.backend.models.User;
import com.docuverse.backend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/lookup")
    public ResponseEntity lookupUser(@RequestBody String email) {
        boolean userExists = authenticationService.lookupUser(email);
        if (userExists)
            return ResponseEntity.ok(userExists);
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not in use");
    }

    @PostMapping("/signup")
    public User signUpUser(@RequestBody SignUpDTO body) {
        return authenticationService.signUpUser(body.getUsername(), body.getEmail(), body.getPassword());
    }

    @PostMapping("/signin")
    public SignInResponseDTO signInUser(@RequestBody SignUpDTO body) {
        return authenticationService.signInUser(body.getUsername(), body.getPassword());
    }
}
