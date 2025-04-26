package dev.wisespirit.mediumclone.model.dto;


import jakarta.validation.constraints.NotBlank;

public record ArticleCreateDto(Long userId,
                               Long reactionCount,
                               @NotBlank String title,
                               @NotBlank String articleText,
                               @NotBlank String topics) {
}
