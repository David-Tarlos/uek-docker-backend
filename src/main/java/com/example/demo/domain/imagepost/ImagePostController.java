package com.example.demo.domain.imagepost;

import com.example.demo.domain.imagepost.dto.ImagePostCreateDTO;
import com.example.demo.domain.imagepost.dto.ImagePostDTO;
import com.example.demo.domain.imagepost.dto.ImagePostMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST-Controller für ImagePost-Operationen.
 * Verwaltet CRUD-Operationen und Like-Funktion für Bilder.
 */
@RestController
@RequestMapping("/api/image-posts")
@Validated
public class ImagePostController {

    private final ImagePostService service;
    private final ImagePostMapper mapper;

    public ImagePostController(ImagePostService service, @Qualifier("imagePostMapperImpl") ImagePostMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /** Erstellt einen neuen ImagePost */
    @PostMapping
    @PreAuthorize("hasAuthority('IMAGE_CREATE')")
    public ResponseEntity<ImagePostDTO> create(
            @Validated @RequestBody ImagePostCreateDTO postDTO
    ) {
        ImagePost post = mapper.fromCreateDTO(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(service.create(post)));
    }

    /** Holt alle ImagePosts paginiert (default 12 pro Seite, sortiert nach createdAt) */
    @GetMapping
    @PreAuthorize("hasAuthority('IMAGE_READ')")
    public Page<ImagePostDTO> getAll(
            @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam(required = false) UUID authorId
    ) {
        if (authorId != null) {
            return service.findByAuthorId(authorId, pageable).map(mapper::toDTO);
        }
        return service.findAll(pageable).map(mapper::toDTO);
    }

    /** Aktualisiert einen ImagePost */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('IMAGE_MODIFY')")
    public ImagePostDTO update(@PathVariable UUID id, @RequestBody ImagePostCreateDTO postDTO) {
        ImagePost post = mapper.fromCreateDTO(postDTO);
        return mapper.toDTO(service.update(id, post));
    }

    /** Löscht einen ImagePost */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('IMAGE_DELETE')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Toggled Like-Status für einen ImagePost */
    @PostMapping("/{id}/like")
    @PreAuthorize("hasAuthority('LIKE_CREATE')")
    public ImagePostDTO toggleLike(@PathVariable UUID id){
        return mapper.toDTO(service.toggleLike(id));
    }
}
