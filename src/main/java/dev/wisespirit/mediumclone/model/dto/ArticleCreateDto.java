package dev.wisespirit.mediumclone.model.dto;


import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ArticleCreateDto(Long userId,
                               @NotBlank String title,
                               @NotBlank String articleText,
                               List<String> topics) {
}
