package com.docuverse.backend.repository;

import com.docuverse.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    User findByNameAndPassword(String username, String password);
}
