package dev.wisespirit.mediumclone.model.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record CommentDto(Long parentId,
                         @Column(nullable = false) Long articleId,
                         @Column(nullable = false)Long userId,
                         @Column(nullable = false) @NotBlank String commentText) {
}