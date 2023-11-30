package com.example.stocktkl.repository;

import com.example.stocktkl.model.Role;
import com.example.stocktkl.model.enum_class.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
