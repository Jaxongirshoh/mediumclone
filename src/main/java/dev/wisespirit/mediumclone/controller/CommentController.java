package dev.wisespirit.mediumclone.controller;

import dev.wisespirit.mediumclone.model.dto.CommentDto;
import dev.wisespirit.mediumclone.model.dto.UpdateComment;
import dev.wisespirit.mediumclone.model.entity.Comment;
import dev.wisespirit.mediumclone.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto){
        return commentService.createComment(commentDto)
                .map(comment -> new ResponseEntity<Comment>(comment, HttpStatus.CREATED))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<List<Comment>> getCommentsOfArticle(@PathVariable Long articleId){
        if (!commentService.existById(articleId)) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(commentService.getCommentsOfArticleId(articleId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id){
        if (commentService.existComment(id)){
            commentService.deleteCommitById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<Comment> editComment(@RequestBody UpdateComment dto,
                                            @PathVariable Long commentId){
        return commentService.updateComment(commentId, dto)
                .map(comment -> new ResponseEntity<Comment>(comment, HttpStatus.NO_CONTENT))
                .orElse(ResponseEntity.internalServerError().build());
    }

}
