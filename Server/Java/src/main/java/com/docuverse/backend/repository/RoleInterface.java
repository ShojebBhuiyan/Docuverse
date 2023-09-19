package com.docuverse.backend.repository;

import com.docuverse.backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleInterface extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(String authority);
}
