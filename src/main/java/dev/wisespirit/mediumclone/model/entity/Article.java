package dev.wisespirit.mediumclone.model.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;


@Entity
@Table(name = "articles")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,updatable = false)
    private Long userId;
    private Long reactionCount;
    @NotBlank
    @Column(nullable = false)
    private String title;
    @NotBlank
    @Column(nullable = false)
    private String articleText;
    @Column(nullable = false)
    private String topics;
    //private List<Comment> comments;

}
