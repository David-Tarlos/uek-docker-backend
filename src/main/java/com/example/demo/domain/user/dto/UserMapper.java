package com.example.demo.domain.user.dto;


import com.example.demo.core.generic.AbstractMapper;
import com.example.demo.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper für User Entity/DTO Konvertierung.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends AbstractMapper<User, UserDTO> {

  /** Mappt RegisterDTO zu User Entity */
  User fromUserRegisterDTO(UserRegisterDTO dto);
}
