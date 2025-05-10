package dev.wisespirit.mediumclone.repository;

import dev.wisespirit.mediumclone.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> getCommentsByArticleId(Long articleId);

    boolean existsByArticleId(Long articleId);
}
