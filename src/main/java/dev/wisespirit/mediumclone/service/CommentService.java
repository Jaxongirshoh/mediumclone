package dev.wisespirit.mediumclone.service;

import dev.wisespirit.mediumclone.model.dto.CommentDto;
import dev.wisespirit.mediumclone.model.dto.UpdateComment;
import dev.wisespirit.mediumclone.model.entity.Comment;
import dev.wisespirit.mediumclone.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> createComment(CommentDto dto){
        return Optional.of(commentRepository
                .save(new Comment(
                        null,
                        dto.parentId() == null ? null : dto.parentId(),
                        dto.articleId(),
                        dto.userId(),
                        dto.commentText()
                )));
    }

    public void deleteCommitById(Long id){
        commentRepository.deleteById(id);
    }

    public Optional<Comment> updateComment(Long commentId, UpdateComment updateComment){
        Comment comment = commentRepository.findById(commentId).get();
        comment.setCommentText(updateComment.commentText());
        return Optional.of(commentRepository.save(comment));
    }

    public List<Comment> getCommentsOfArticleId(Long articleId){
        return commentRepository.getCommentsByArticleId(articleId);
    }


    public boolean existById(Long articleId) {
        return commentRepository.existsByArticleId(articleId);
    }

    public boolean existComment(Long id) {
        return commentRepository.existsById(id);
    }
}
