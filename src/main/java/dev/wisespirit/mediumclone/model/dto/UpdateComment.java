package dev.wisespirit.mediumclone.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record UpdateComment(@Column(nullable = false)
                            @NotBlank
                            String  commentText) {
}
