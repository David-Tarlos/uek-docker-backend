package com.example.demo.domain.user;

import com.example.demo.core.generic.AbstractRepository;

import java.util.Optional;


import org.springframework.stereotype.Repository;

/**
 * Repository für User-Datenbankoperationen.
 */
@Repository
public interface UserRepository extends AbstractRepository<User> {

  /** Findet User nach E-Mail-Adresse */
  Optional<User> findByEmail(String email);
}
