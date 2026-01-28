package com.example.demo.core.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generische Service-Implementierung mit Standard-CRUD-Operationen.
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractServiceImpl<T extends AbstractEntity> implements AbstractService<T> {
  protected AbstractRepository<T> repository;

  /** {@inheritDoc} */
  @Override
  public T save(T entity) {
    return repository.save(entity);
  }

  /** {@inheritDoc} */
  @Override
  public void deleteById(UUID id) throws NoSuchElementException {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new NoSuchElementException(String.format("Entity with ID '%s' could not be found", id));
    }
  }

  /** {@inheritDoc} */
  @Override
  public T updateById(UUID id, T entity) throws NoSuchElementException {
    if (repository.existsById(id)) {
      entity.setId(id);
      return repository.save(entity);
    } else {
      throw new NoSuchElementException(String.format("Entity with ID '%s' could not be found", id));
    }
  }

  /** {@inheritDoc} */
  @Override
  public List<T> findAll() {
    return repository.findAll();
  }

  /** {@inheritDoc} */
  @Override
  public List<T> findAll(Pageable pageable) {
    Page<T> pagedResult = repository.findAll(pageable);
    return pagedResult.hasContent() ? pagedResult.getContent() : new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public T findById(UUID id) {
    return repository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  /** {@inheritDoc} */
  @Override
  public boolean existsById(UUID id) {
    return repository.existsById(id);
  }


}
