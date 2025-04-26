package dev.wisespirit.mediumclone.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

/**
 * DTO for {@link dev.wisespirit.mediumclone.model.entity.Article}
 */

public record ArticleDto(Long id,
                         Long userId,
                         Long reactionCount,
                         @NotBlank String title,
                         @NotBlank String articleText,
                         @NotBlank String topics) {
}