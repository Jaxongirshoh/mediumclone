package dev.wisespirit.mediumclone.model.auth;

import jakarta.validation.constraints.NotBlank;

public record GenerateTokenRequest(
        @NotBlank(message = "email cannot be blank") String email,
        @NotBlank(message = "password cannot be blank") String password) {
}
