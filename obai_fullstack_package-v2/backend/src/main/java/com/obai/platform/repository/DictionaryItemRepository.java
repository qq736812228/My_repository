package com.obai.platform.repository;

import com.obai.platform.entity.DictionaryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryItemRepository extends JpaRepository<DictionaryItem, Long> {
}
