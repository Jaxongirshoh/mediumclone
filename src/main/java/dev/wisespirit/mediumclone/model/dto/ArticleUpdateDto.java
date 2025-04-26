package dev.wisespirit.mediumclone.model.dto;


public record ArticleUpdateDto(
        String title,
        String articleText,
        String topics) {
}