package dev.wisespirit.mediumclone.model.dto;


public record UserUpdateDto(String fullName,
                            String password,
                            String bio,
                            String email) {
}
