package com.example.demo.core.security;

import com.example.demo.core.security.helpers.AuthorizationSchemas;
import com.example.demo.core.security.helpers.Credentials;
import com.example.demo.core.security.helpers.JwtProperties;
import com.example.demo.domain.user.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.Date;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Filter für JWT-Authentifizierung bei Login.
 * Generiert JWT-Token bei erfolgreicher Anmeldung.
 */
@Log4j2
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private final JwtProperties jwtProperties;

  public JWTAuthenticationFilter(RequestMatcher requestMatcher, AuthenticationManager authenticationManager,
                                 JwtProperties jwtProperties) {
    super(requestMatcher, authenticationManager);
    this.jwtProperties = jwtProperties;
  }

  /** Generiert JWT-Token mit User-ID und Authorities */
  private String generateToken(Authentication authResult) {
    UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authResult.getPrincipal();
    byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());

    return Jwts.builder()
               .setSubject(userDetailsImpl.user().getId().toString())
               .claim("authorities", userDetailsImpl.getAuthorities()
                                                    .stream()
                                                    .map(GrantedAuthority::getAuthority)
                                                    .toList())
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMillis()))
               .setIssuer(jwtProperties.getIssuer())
               .signWith(Keys.hmacShaKeyFor(keyBytes))
               .compact();
  }

  /** Versucht Authentifizierung mit E-Mail/Passwort */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    try {
      Credentials credentials = new ObjectMapper().readValue(request.getInputStream(), Credentials.class);
      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
    }
    catch (IOException e) {
      log.error("Exception while Authentication thrown.", e);
      return null;
    }
  }

  /** Fügt JWT-Token zum Response-Header hinzu */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                          Authentication authResult) throws IOException {
    response.addHeader(HttpHeaders.AUTHORIZATION, AuthorizationSchemas.BEARER + " " + generateToken(authResult));
  }

  /** Setzt 401 Status bei fehlgeschlagener Authentifizierung */
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            AuthenticationException failed) {
    SecurityContextHolder.clearContext();
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
  }
}
