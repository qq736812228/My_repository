package com.obai.platform.service;

import com.obai.platform.entity.PointAccount;
import com.obai.platform.entity.PointTransaction;
import com.obai.platform.entity.TaskItem;
import com.obai.platform.repository.PointAccountRepository;
import com.obai.platform.repository.PointTransactionRepository;
import com.obai.platform.repository.TaskItemRepository;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    private final PointAccountRepository accountRepository;
    private final PointTransactionRepository transactionRepository;
    private final TaskItemRepository taskItemRepository;

    public PointService(PointAccountRepository accountRepository, PointTransactionRepository transactionRepository, TaskItemRepository taskItemRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.taskItemRepository = taskItemRepository;
    }

    public Map<String, Object> summary(Long userId) {
        PointAccount account = accountRepository.findByUserId(userId).orElseGet(() -> {
            PointAccount created = new PointAccount();
            created.userId = userId;
            created.balance = 2680;
            created.totalEarned = 2680;
            return accountRepository.save(created);
        });
        List<PointTransaction> transactions = transactionRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<TaskItem> tasks = taskItemRepository.findByEnabledTrueOrderByCreatedAtDesc();
        return Map.of("account", account, "transactions", transactions, "tasks", tasks);
    }
}
