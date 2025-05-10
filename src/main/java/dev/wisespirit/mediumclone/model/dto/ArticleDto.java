package dev.wisespirit.mediumclone.model.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.util.List;

/**
 * DTO for {@link dev.wisespirit.mediumclone.model.entity.Article}
 */

public record ArticleDto(Long id,
                         Long userId,
                         Long reactionCount,
                         String title,
                         String articleText,
                         List<String> topics) {
}