package dev.wisespirit.mediumclone.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long parentId;
    @Column(nullable = false)
    private Long articleId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long commentText;
}
