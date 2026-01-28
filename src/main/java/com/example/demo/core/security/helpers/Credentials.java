package com.example.demo.core.security.helpers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Login-Credentials mit E-Mail und Passwort.
 */
@NoArgsConstructor
@Getter
@Setter
public class Credentials {

  private String email;
  private String password;

}
