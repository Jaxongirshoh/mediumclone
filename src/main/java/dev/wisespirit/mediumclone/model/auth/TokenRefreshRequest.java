package dev.wisespirit.mediumclone.model.auth;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(@NotBlank(message = "token cannot be blank") String token) {
}
