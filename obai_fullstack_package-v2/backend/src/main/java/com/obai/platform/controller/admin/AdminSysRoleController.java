package com.obai.platform.controller.admin;

import com.obai.platform.audit.OperationAudit;
import com.obai.platform.common.ApiResponse;
import com.obai.platform.common.EntityPatchUtil;
import com.obai.platform.entity.SysRole;
import com.obai.platform.repository.SysRoleRepository;
import com.obai.platform.service.AdminService;
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
@RequestMapping("/api/admin/roles")
public class AdminSysRoleController {
    private final SysRoleRepository repository;
    private final AdminService adminService;

    public AdminSysRoleController(SysRoleRepository repository, AdminService adminService) {
        this.repository = repository;
        this.adminService = adminService;
    }

    @GetMapping
    public ApiResponse<List<SysRole>> list() {
        return ApiResponse.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<SysRole> detail(@PathVariable Long id) {
        return ApiResponse.ok(repository.findById(id).orElseThrow());
    }

    @PostMapping
    @OperationAudit(module = "角色管理", action = "新增")
    public ApiResponse<SysRole> create(@RequestBody SysRole body) {
        return ApiResponse.ok(repository.save(body));
    }

    @PutMapping("/{id}")
    @OperationAudit(module = "角色管理", action = "修改")
    public ApiResponse<SysRole> update(@PathVariable Long id, @RequestBody SysRole body) {
        var target = repository.findById(id).orElseThrow();
        EntityPatchUtil.copyNonNull(body, target);
        return ApiResponse.ok(repository.save(target));
    }

    @PutMapping("/{id}/menus")
    @OperationAudit(module = "角色管理", action = "分配菜单权限")
    public ApiResponse<SysRole> menus(@PathVariable Long id, @RequestBody List<Long> menuIds) {
        return ApiResponse.ok(adminService.assignMenus(id, menuIds));
    }

    @DeleteMapping("/{id}")
    @OperationAudit(module = "角色管理", action = "删除")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ApiResponse.ok();
    }
}
