package com.si.lab4.repository;


import com.si.lab4.model.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findCredentialByUserEmailContaining(String email);
}
