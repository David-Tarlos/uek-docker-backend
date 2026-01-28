package com.example.demo.domain.user;

import com.example.demo.core.generic.AbstractServiceImpl;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

/**
 * Implementierung des UserService.
 * Verwaltet Registrierung, Authentifizierung und User-Operationen.
 */
@Service
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;

  @Autowired
  public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, RoleService roleService) {
    super(repository);
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
  }

  /** {@inheritDoc} */
  @Override
  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new IllegalStateException("No authenticated user");
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof UserDetailsImpl userDetails) {
      return userDetails.user();
    }

    throw new IllegalStateException("Unexpected principal type");
  }



  /** Lädt User nach E-Mail für Spring Security */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return ((UserRepository) repository).findByEmail(email)
                                        .map(UserDetailsImpl::new)
                                        .orElseThrow(() -> new UsernameNotFoundException(email));
  }

  /** {@inheritDoc} Setzt Default-Rolle */
  @Override
  public User register(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Role defaultRole=roleService.findById(UUID.fromString("d29e709c-0ff1-4f4c-a7ef-09f656c390f1"));//Default role
    user.setRoles(Set.of(defaultRole));
    return save(user);
  }
  /** {@inheritDoc} Passwort wird auf "1234" gesetzt */
  @Override
  public User registerUser(User user){
    user.setPassword(passwordEncoder.encode("1234"));
    return save(user);
  }

}
