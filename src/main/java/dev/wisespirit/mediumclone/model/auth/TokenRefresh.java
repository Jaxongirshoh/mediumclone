package dev.wisespirit.mediumclone.model.auth;

import jakarta.validation.constraints.NotBlank;

public record TokenRefresh(@NotBlank(message = "token cannot be blank") String token) {
}
