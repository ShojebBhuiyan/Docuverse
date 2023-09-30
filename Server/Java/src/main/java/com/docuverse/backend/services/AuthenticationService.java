package com.docuverse.backend.services;

import com.docuverse.backend.enums.Subscription;
import com.docuverse.backend.models.Role;
import com.docuverse.backend.models.SignInResponseDTO;
import com.docuverse.backend.models.User;
import com.docuverse.backend.repository.RoleRepository;
import com.docuverse.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public boolean lookupUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public User signUpUser(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Subscription subscription = Subscription.free;

        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);


        User user = new User(username, email, encodedPassword, subscription, authorities);
        return userRepository.save(user);
    }

    public SignInResponseDTO signInUser(String username, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return new SignInResponseDTO(userRepository.findByName(username).get(), token);

        } catch (AuthenticationException e) {
            return new SignInResponseDTO(null, "");
        }
    }
}
