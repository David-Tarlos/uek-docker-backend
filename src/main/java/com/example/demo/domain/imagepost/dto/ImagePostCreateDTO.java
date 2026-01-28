package com.example.demo.domain.imagepost.dto;

import com.example.demo.core.security.validators.link.Link;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO für das Erstellen eines neuen ImagePosts.
 */
public record ImagePostCreateDTO (
        @NotBlank @Link String imageUrl,
        @Size(max = 200) String description
        ) {}
