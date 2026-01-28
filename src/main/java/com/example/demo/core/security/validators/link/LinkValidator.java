package com.example.demo.core.security.validators.link;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

/**
 * Validator für @Link Annotation. Prüft HTTP/HTTPS URLs.
 */
@Component
public class LinkValidator implements ConstraintValidator<Link, String> {

  /** Validiert URL mit Apache UrlValidator */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    UrlValidator urlValidator = new UrlValidator(new String[] {"http", "https"});

    return urlValidator.isValid(value);
  }
}




