package com.docuverse.backend.services;

import com.docuverse.backend.enums.Subscription;
import com.docuverse.backend.models.Role;
import com.docuverse.backend.models.User;
import com.docuverse.backend.repository.RoleRepository;
import com.docuverse.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signUpUser(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Subscription subscription = Subscription.free;

        Role userRole = roleRepository.findByAuthority("USER").get();
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User user = new User(username, email, encodedPassword, subscription, authorities);
        return userRepository.save(user);
    }
}
