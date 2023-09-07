package com.docuverse.backend.repository;

import com.docuverse.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String username);
    User findByNameAndPassword(String username, String password);
}
