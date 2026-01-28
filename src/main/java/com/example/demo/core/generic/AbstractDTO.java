package com.example.demo.core.generic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Basis-DTO mit UUID. Alle DTOs erben von dieser Klasse.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractDTO {

  private UUID id;

}
