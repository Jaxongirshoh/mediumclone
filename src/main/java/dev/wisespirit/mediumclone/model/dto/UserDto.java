package dev.wisespirit.mediumclone.model.dto;


import java.util.List;

public record UserDto(Long id,
                      String fullName,
                      String password,
                      String bio,
                      String email,
                      List<Long> followingsId) {
}
