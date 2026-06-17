package com.obai.platform.repository;

import com.obai.platform.entity.TaskItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskItemRepository extends JpaRepository<TaskItem, Long> {
    List<TaskItem> findByEnabledTrueOrderByCreatedAtDesc();

}
