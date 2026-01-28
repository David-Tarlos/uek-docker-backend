package com.example.demo.domain.user;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetails-Implementierung für Spring Security.
 * Wrappet User-Entity und extrahiert Authorities.
 */
public record UserDetailsImpl(User user) implements UserDetails {

  /** Extrahiert Authorities aus User-Rollen */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getRoles()
               .stream()
               .flatMap(r -> r.getAuthorities()
                              .stream())
               .map(a -> new SimpleGrantedAuthority(a.getName()))
               .toList();
  }

  /** Gibt User-Passwort zurück */
  @Override
  public String getPassword() {
    return user.getPassword();
  }

  /** Gibt E-Mail als Username zurück */
  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
