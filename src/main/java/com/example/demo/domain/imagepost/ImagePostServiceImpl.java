package com.example.demo.domain.imagepost;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementierung des ImagePostService.
 * Verwaltet CRUD und Like-Logik mit Berechtigungsprüfung.
 */
@Service
public class ImagePostServiceImpl implements ImagePostService {

    private final ImagePostRepository repository;
    private final UserService userService;

    public ImagePostServiceImpl(ImagePostRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    /** {@inheritDoc} */
    @Override
    public ImagePost create(ImagePost post) {
        User currentUser = userService.getCurrentUser();
        post.setAuthor(currentUser);
        return repository.save(post);
    }

    /** {@inheritDoc} Prüft Autor- oder Admin-Berechtigung */
    @Override
    public ImagePost update(UUID id, ImagePost updatedPost) {
        ImagePost existing = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Image post not found"));
        User currentUser = userService.getCurrentUser();

        boolean isAuthor = existing.getAuthor().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));

        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("You do not have permission to update this post");
        }

        existing.setImageUrl(updatedPost.getImageUrl()).setDescription(updatedPost.getDescription());

        return repository.save(existing);
    }

    /** {@inheritDoc} Prüft Autor- oder Admin-Berechtigung */
    @Override
    public void delete(UUID id) {
        ImagePost post = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Image post not found"));
        User currentUser = userService.getCurrentUser();

        boolean isAuthor = post.getAuthor().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));

        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("You do not have permission to delete this post");
        }
        repository.deleteById(id);
    }

    /** {@inheritDoc} */
    @Override
    public Page<ImagePost> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /** {@inheritDoc} */
    @Override
    public Page<ImagePost> findByAuthorId(UUID authorId, Pageable pageable) {
        return repository.findByAuthorId(authorId, pageable);
    }

    /** {@inheritDoc} Verhindert Like auf eigenen Post */
    @Override
    public ImagePost toggleLike(UUID postId) {
        ImagePost post = repository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Image post not found"));
        User currentUser = userService.getCurrentUser();
        if (post.getAuthor().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("Can't like own post");
        }
        if (post.getLikedBy().contains(currentUser)) {
            post.getLikedBy().remove(currentUser);
        } else {
            post.getLikedBy().add(currentUser);
        }
        return repository.save(post);
    }
}
