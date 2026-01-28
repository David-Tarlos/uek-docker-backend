package com.example.demo.core.security.helpers;

/**
 * HTTP Authorization-Schemas (RFC 1945).
 */
public enum AuthorizationSchemas {
  BASIC("Basic"), BEARER("Bearer"), DIGEST("Digest");

  private final String text;

  AuthorizationSchemas(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
