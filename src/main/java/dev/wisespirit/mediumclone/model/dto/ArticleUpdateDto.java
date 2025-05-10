package dev.wisespirit.mediumclone.model.dto;


import java.util.List;

public record ArticleUpdateDto(
        String title,
        String articleText,
        List<String> topics) {
}