package com.example.demo.domain.imagepost;

import com.example.demo.core.generic.AbstractEntity;
import com.example.demo.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity für Bild-Posts mit URL, Beschreibung, Autor und Likes.
 */
@Entity
@Table(name = "image_posts")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ImagePost extends AbstractEntity {

    @Column(name = "imageUrl")
    @NotEmpty(message = "Cant be empty")
    private String imageUrl;

    @Column(length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany
    @JoinTable(
            name = "image_post_likes",
            joinColumns = @JoinColumn(name = "image_post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedBy = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
