package com.example.demo.domain.imagepost.dto;

import com.example.demo.core.generic.AbstractDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO für ImagePost-Daten inkl. Like-Count.
 */
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ImagePostDTO extends AbstractDTO {
    private String imageUrl;
    private String description;
    private UUID authorId;
    private int likeCount;
    private LocalDateTime createdAt;

    public ImagePostDTO(UUID id, String imageUrl, String description, UUID authorId, int likeCount, LocalDateTime createdAt) {
        super(id);
        this.imageUrl = imageUrl;
        this.description = description;
        this.authorId = authorId;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }
}
