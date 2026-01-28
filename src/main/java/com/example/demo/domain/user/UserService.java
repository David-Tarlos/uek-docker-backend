package com.example.demo.domain.user;

import com.example.demo.core.generic.AbstractService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Service-Interface für User-Operationen.
 */
public interface UserService extends UserDetailsService, AbstractService<User> {

  /** Registriert User mit Passwort */
  User register(User user);

  /** Registriert User ohne Passwort (Test/Dev) */
  User registerUser(User user);

  /** Gibt aktuell eingeloggten User zurück */
  User getCurrentUser();
}
