package com.obai.platform.controller.admin;

import com.obai.platform.audit.OperationAudit;
import com.obai.platform.common.ApiResponse;
import com.obai.platform.common.EntityPatchUtil;
import com.obai.platform.entity.PointTransaction;
import com.obai.platform.repository.PointTransactionRepository;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/point-transactions")
public class AdminPointTransactionController {
    private final PointTransactionRepository repository;

    public AdminPointTransactionController(PointTransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ApiResponse<List<PointTransaction>> list() {
        return ApiResponse.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<PointTransaction> detail(@PathVariable Long id) {
        return ApiResponse.ok(repository.findById(id).orElseThrow());
    }

    @PostMapping
    @OperationAudit(module = "积分流水", action = "新增")
    public ApiResponse<PointTransaction> create(@RequestBody PointTransaction body) {
        return ApiResponse.ok(repository.save(body));
    }

    @PutMapping("/{id}")
    @OperationAudit(module = "积分流水", action = "修改")
    public ApiResponse<PointTransaction> update(@PathVariable Long id, @RequestBody PointTransaction body) {
        var target = repository.findById(id).orElseThrow();
        EntityPatchUtil.copyNonNull(body, target);
        return ApiResponse.ok(repository.save(target));
    }

    @DeleteMapping("/{id}")
    @OperationAudit(module = "积分流水", action = "删除")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ApiResponse.ok();
    }
}
