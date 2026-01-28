package com.example.demo.core.generic;

import java.util.List;
import java.util.Set;

/**
 * Generisches Mapper-Interface für Entity/DTO Konvertierung.
 */
public interface AbstractMapper<BO extends AbstractEntity, DTO extends AbstractDTO> {

  /** Mappt DTO zu Entity */
  BO fromDTO(DTO dto);

  /** Mappt DTO-Liste zu Entity-Liste */
  List<BO> fromDTOs(List<DTO> dtos);

  /** Mappt DTO-Set zu Entity-Set */
  Set<BO> fromDTOs(Set<DTO> dtos);

  /** Mappt Entity zu DTO */
  DTO toDTO(BO BO);

  /** Mappt Entity-Liste zu DTO-Liste */
  List<DTO> toDTOs(List<BO> BOs);

  /** Mappt Entity-Set zu DTO-Set */
  Set<DTO> toDTOs(Set<BO> BOs);
}
