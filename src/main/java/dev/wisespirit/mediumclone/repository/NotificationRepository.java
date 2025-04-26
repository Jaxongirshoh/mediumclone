package dev.wisespirit.mediumclone.repository;

import dev.wisespirit.mediumclone.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
