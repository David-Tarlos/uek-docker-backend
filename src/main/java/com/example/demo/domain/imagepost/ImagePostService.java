package com.example.demo.domain.imagepost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service-Interface für ImagePost-Operationen.
 */
public interface ImagePostService {

    /** Erstellt neuen ImagePost */
    ImagePost create(ImagePost post);

    /** Aktualisiert bestehenden ImagePost */
    ImagePost update(UUID id, ImagePost updatedPost);

    /** Löscht ImagePost */
    void delete(UUID id);

    /** Findet alle ImagePosts paginiert */
    Page<ImagePost> findAll(Pageable pageable);

    /** Findet alle ImagePosts eines bestimmten Autors paginiert */
    Page<ImagePost> findByAuthorId(UUID authorId, Pageable pageable);

    /** Toggled Like-Status */
    ImagePost toggleLike(UUID postId);
}
