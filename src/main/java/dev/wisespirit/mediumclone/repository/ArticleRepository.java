package dev.wisespirit.mediumclone.repository;

import dev.wisespirit.mediumclone.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> getArticleByUserId(Long userId);
}
