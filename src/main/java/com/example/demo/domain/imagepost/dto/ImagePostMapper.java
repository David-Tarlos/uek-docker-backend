package com.example.demo.domain.imagepost.dto;

import com.example.demo.core.generic.AbstractMapper;
import com.example.demo.domain.imagepost.ImagePost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper für ImagePost Entity/DTO Konvertierung.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImagePostMapper extends AbstractMapper<ImagePost, ImagePostDTO> {

    /** Mappt Entity zu DTO mit Author-ID und Like-Count */
    @Override
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "likeCount", expression = "java(post.getLikedBy() == null ? 0 : post.getLikedBy().size())")
    ImagePostDTO toDTO(ImagePost post);

    /** Mappt CreateDTO zu Entity */
    ImagePost fromCreateDTO(ImagePostCreateDTO dto);
}
