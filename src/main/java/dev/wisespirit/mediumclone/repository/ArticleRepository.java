package dev.wisespirit.mediumclone.repository;

import dev.wisespirit.mediumclone.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> getArticleByUserId(Long userId);

    @Query("""
select a from Article a where
lower(a.topics) like lower(concat('%',:topic,'%'))
""")
    List<Article> findByTopics(@Param("topic") String topic);

}
