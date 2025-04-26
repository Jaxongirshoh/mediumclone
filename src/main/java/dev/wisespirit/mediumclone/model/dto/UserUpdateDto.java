package dev.wisespirit.mediumclone.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto(@NotBlank @Column(nullable = false) String fullName,
                            @NotBlank @Column(nullable = false) String password,
                            String bio,
                            @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\\\\\.[a-zA-Z]{2,}$\",message = \"Invalid email format\"") String email) {
}
