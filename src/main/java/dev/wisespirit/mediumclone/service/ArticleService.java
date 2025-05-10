package dev.wisespirit.mediumclone.service;

import dev.wisespirit.mediumclone.model.dto.ArticleCreateDto;
import dev.wisespirit.mediumclone.model.dto.ArticleDto;
import dev.wisespirit.mediumclone.model.dto.ArticleUpdateDto;
import dev.wisespirit.mediumclone.model.entity.Article;
import dev.wisespirit.mediumclone.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Optional<ArticleDto> createArticle(ArticleCreateDto dto) {
        Article article = new Article();
        article.setUserId(dto.userId());
        article.setArticleText(dto.articleText());
        article.setTopics(String.join(",", dto.topics()));
        article.setTitle(dto.title());
        Article saved = articleRepository.save(article);
        return Optional.of(new ArticleDto(saved.getId(),
                saved.getUserId(),
                saved.getReactionCount(),
                saved.getTitle(),
                saved.getArticleText(),
                Arrays.asList(saved.getTopics().split(","))));
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public Optional<ArticleDto> updateArticleDto(ArticleUpdateDto dto, Long id) {
        Article article = articleRepository.findById(id).get();
        if (StringUtils.hasText(dto.articleText())) {
            article.setArticleText(dto.articleText());
        }

        if (StringUtils.hasText(dto.title())) {
            article.setTitle(dto.title());
        }

        if (StringUtils.hasText(dto.topics().toString())) {
            String topics = article.getTopics();
            article.setTopics(topics + ","+String.join(",", dto.topics()));
        }

        Article saved = articleRepository.save(article);
        return Optional.of(new ArticleDto(
                saved.getId(),
                saved.getUserId(),
                saved.getReactionCount(),
                saved.getTitle(),
                saved.getArticleText(),
                Arrays.asList(saved.getTopics().split(","))
        ));
    }

    public List<ArticleDto> getArticleByPublisher(Long userid) {
        return articleRepository.getArticleByUserId(userid)
                .stream()
                .map(article -> new ArticleDto(
                        article.getId(),
                        article.getUserId(),
                        article.getReactionCount(),
                        article.getTitle(),
                        article.getArticleText(),
                        Arrays.asList(article.getTopics().split(","))
                )).toList();
    }

    public void reactToArticle(Long articleId, Long count) {
        Article article = articleRepository.findById(articleId).get();
        if (count > 0) {
            article.setReactionCount(article.getReactionCount()!=null?article.getReactionCount() + count:count);
        } else {
            article.setReactionCount(article.getReactionCount()!=null?article.getReactionCount() - count:0);
        }
        articleRepository.save(article);
    }

    public List<ArticleDto> getArticleByTopic(String topic) {
        return articleRepository.findByTopics(topic)
                .stream().map(article -> new ArticleDto(
                        article.getId(),
                        article.getUserId(),
                        article.getReactionCount(),
                        article.getTitle(),
                        article.getArticleText(),
                        Arrays.asList(article.getTopics().split(","))
                )).toList();
    }

    public boolean existById(Long id) {
        return articleRepository.existsById(id);
    }


    public ArticleDto findById(Long id) {
        Article article = articleRepository.findById(id).get();
        return new ArticleDto(
                article.getId(),
                article.getUserId(),
                article.getReactionCount(),
                article.getTitle(),
                article.getArticleText(),
                Arrays.asList(article.getTopics().split(","))
        );
    }
}