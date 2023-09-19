package com.docuverse.backend.controllers;

import com.docuverse.backend.models.SignUpDTO;
import com.docuverse.backend.models.User;
import com.docuverse.backend.services.AuthenticationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    private AuthenticationServices authenticationServices;

    @PostMapping("/signup")
    public User signUpUser(@RequestBody SignUpDTO body) {
        return authenticationServices.signUpUser(body.getUsername(), body.getEmail(), body.getPassword());
    }
}
