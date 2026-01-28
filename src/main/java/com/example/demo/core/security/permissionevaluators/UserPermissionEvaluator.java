package com.example.demo.core.security.permissionevaluators;

import com.example.demo.domain.user.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Permission-Evaluator für User-spezifische Berechtigungsprüfungen.
 */
@Component
@NoArgsConstructor
public class UserPermissionEvaluator {

  /**
   * Prüft ob der User sich selbst bearbeiten/lesen will.
   * Wird in @PreAuthorize verwendet.
   */
  public boolean isOwner(User principal, UUID id) {
    return principal.getId().equals(id);
  }

}
