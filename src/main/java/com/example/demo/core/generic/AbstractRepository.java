package com.example.demo.core.generic;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Generisches Repository-Interface mit UUID als ID-Typ.
 */
public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, UUID> {
}
