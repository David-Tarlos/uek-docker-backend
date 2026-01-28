package com.example.demo.core.generic;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/**
 * Generisches Service-Interface mit Standard-CRUD-Operationen.
 */
public interface AbstractService<T extends AbstractEntity> {

  /** Speichert Entity */
  T save(T entity);

  /** Löscht Entity nach ID */
  void deleteById(UUID id) throws NoSuchElementException;

  /** Aktualisiert Entity nach ID */
  T updateById(UUID id, T entity) throws NoSuchElementException;

  /** Findet alle Entities */
  List<T> findAll();

  /** Findet alle Entities paginiert */
  List<T> findAll(Pageable pageable);

  /** Findet Entity nach ID */
  T findById(UUID id);

  /** Prüft ob Entity existiert */
  boolean existsById(UUID id);
}
