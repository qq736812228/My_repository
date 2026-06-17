package com.obai.platform.controller.admin;

import com.obai.platform.audit.OperationAudit;
import com.obai.platform.common.ApiResponse;
import com.obai.platform.common.EntityPatchUtil;
import com.obai.platform.entity.Invitation;
import com.obai.platform.repository.InvitationRepository;
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
@RequestMapping("/api/admin/invitations")
public class AdminInvitationController {
    private final InvitationRepository repository;

    public AdminInvitationController(InvitationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ApiResponse<List<Invitation>> list() {
        return ApiResponse.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Invitation> detail(@PathVariable Long id) {
        return ApiResponse.ok(repository.findById(id).orElseThrow());
    }

    @PostMapping
    @OperationAudit(module = "邀请管理", action = "新增")
    public ApiResponse<Invitation> create(@RequestBody Invitation body) {
        return ApiResponse.ok(repository.save(body));
    }

    @PutMapping("/{id}")
    @OperationAudit(module = "邀请管理", action = "修改")
    public ApiResponse<Invitation> update(@PathVariable Long id, @RequestBody Invitation body) {
        var target = repository.findById(id).orElseThrow();
        EntityPatchUtil.copyNonNull(body, target);
        return ApiResponse.ok(repository.save(target));
    }

    @DeleteMapping("/{id}")
    @OperationAudit(module = "邀请管理", action = "删除")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ApiResponse.ok();
    }
}
