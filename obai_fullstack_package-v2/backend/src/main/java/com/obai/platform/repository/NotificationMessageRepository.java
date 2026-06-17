package com.obai.platform.repository;

import com.obai.platform.entity.NotificationMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long> {
    List<NotificationMessage> findByUserIdOrderByCreatedAtDesc(Long userId);

}
