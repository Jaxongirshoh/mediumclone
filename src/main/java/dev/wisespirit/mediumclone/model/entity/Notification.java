package dev.wisespirit.mediumclone.model.entity;

import dev.wisespirit.mediumclone.model.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Notification{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long articleId;
    private NotificationType type;
}
