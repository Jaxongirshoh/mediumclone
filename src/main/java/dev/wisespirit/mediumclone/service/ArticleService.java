package dev.wisespirit.mediumclone.service;

import dev.wisespirit.mediumclone.model.dto.ArticleCreateDto;
import dev.wisespirit.mediumclone.model.dto.ArticleDto;
import dev.wisespirit.mediumclone.model.dto.ArticleUpdateDto;
import dev.wisespirit.mediumclone.model.entity.Article;
import dev.wisespirit.mediumclone.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleDto createArticle(ArticleCreateDto dto) {
        Article article = new Article();
        article.setUserId(dto.userId());
        article.setArticleText(dto.articleText());
        article.setTopics(dto.topics());
        article.setTitle(dto.title());
        Article saved = articleRepository.save(article);
        return new ArticleDto(saved.getId(),
                saved.getUserId(),
                saved.getReactionCount(),
                saved.getTitle(),
                saved.getArticleText(),
                saved.getTopics());
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public ArticleDto updateArticleDto(ArticleUpdateDto dto, Long id) {
        Article article = new Article();
        article.setId(id);
        if (dto.articleText() != null) {
            article.setArticleText(dto.articleText());
        }

        if (dto.title() != null) {
            article.setTitle(dto.title());
        }

        if (article.getTopics() != null) {
            article.setTopics(dto.topics());
        }

        Article saved = articleRepository.save(article);
        return new ArticleDto(
                saved.getId(),
                saved.getUserId(),
                saved.getReactionCount(),
                saved.getTitle(),
                saved.getArticleText(),
                saved.getTopics()
        );
    }

    public List<ArticleDto> getArticleByPublisher(Long userid){
        return articleRepository.getArticleByUserId(userid)
                .stream()
                .map(article -> new ArticleDto(
                        article.getId(),
                        article.getUserId(),
                        article.getReactionCount(),
                        article.getTitle(),
                        article.getArticleText(),
                        article.getTopics()
                )).toList();
    }

    public List<ArticleDto> getArticleByTopic(String topic){
        
    }

}