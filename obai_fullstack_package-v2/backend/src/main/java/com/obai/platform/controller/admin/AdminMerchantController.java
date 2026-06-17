package com.obai.platform.controller.admin;

import com.obai.platform.audit.OperationAudit;
import com.obai.platform.common.ApiResponse;
import com.obai.platform.common.EntityPatchUtil;
import com.obai.platform.entity.Merchant;
import com.obai.platform.repository.MerchantRepository;
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
@RequestMapping("/api/admin/merchants")
public class AdminMerchantController {
    private final MerchantRepository repository;

    public AdminMerchantController(MerchantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ApiResponse<List<Merchant>> list() {
        return ApiResponse.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Merchant> detail(@PathVariable Long id) {
        return ApiResponse.ok(repository.findById(id).orElseThrow());
    }

    @PostMapping
    @OperationAudit(module = "商户管理", action = "新增")
    public ApiResponse<Merchant> create(@RequestBody Merchant body) {
        return ApiResponse.ok(repository.save(body));
    }

    @PutMapping("/{id}")
    @OperationAudit(module = "商户管理", action = "修改")
    public ApiResponse<Merchant> update(@PathVariable Long id, @RequestBody Merchant body) {
        var target = repository.findById(id).orElseThrow();
        EntityPatchUtil.copyNonNull(body, target);
        return ApiResponse.ok(repository.save(target));
    }

    @DeleteMapping("/{id}")
    @OperationAudit(module = "商户管理", action = "删除")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ApiResponse.ok();
    }
}
