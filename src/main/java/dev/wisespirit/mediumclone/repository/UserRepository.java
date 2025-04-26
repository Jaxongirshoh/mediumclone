package dev.wisespirit.mediumclone.repository;

import dev.wisespirit.mediumclone.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
