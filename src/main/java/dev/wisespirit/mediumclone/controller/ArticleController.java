package dev.wisespirit.mediumclone.controller;

import dev.wisespirit.mediumclone.model.dto.ArticleCreateDto;
import dev.wisespirit.mediumclone.model.dto.ArticleDto;
import dev.wisespirit.mediumclone.model.dto.ArticleUpdateDto;
import dev.wisespirit.mediumclone.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create")
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleCreateDto dto){
        return articleService.createArticle(dto)
                .map(articleDto -> new ResponseEntity<ArticleDto>(articleDto, HttpStatus.CREATED))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId){
        if (articleService.existById(articleId)){
            articleService.deleteArticle(articleId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{articleId}")
    public ResponseEntity<ArticleDto> updateArticle(@RequestBody ArticleUpdateDto dto,
                                                    @PathVariable Long articleId){
        if (!articleService.existById(articleId)){
            return ResponseEntity.notFound().build();
        }
        return articleService.updateArticleDto(dto, articleId)
                .map(articleDto -> ResponseEntity.<ArticleDto>ok(articleDto))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id){
        if (!articleService.existById(id)){
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(articleService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/publishers/{publisherId}")
    public ResponseEntity<List<ArticleDto>> getArticlesOfPublisher(@PathVariable Long publisherId){
        return ResponseEntity.ok(articleService.getArticleByPublisher(publisherId));
    }

    @PutMapping("/{articleId}/{count}")
    public ResponseEntity<Void> reactToArticle(@PathVariable Long articleId,@PathVariable Long count){
        if (articleService.existById(articleId)){
            articleService.reactToArticle(articleId,count);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<ArticleDto>> getArticlesByTopics(@RequestParam String topic){
        return ResponseEntity.ok(articleService.getArticleByTopic(topic));
    }



}
