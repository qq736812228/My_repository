package com.obai.platform.controller.admin;

import com.obai.platform.audit.OperationAudit;
import com.obai.platform.common.ApiResponse;
import com.obai.platform.entity.SysUser;
import com.obai.platform.repository.SysUserRepository;
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
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final AdminService adminService;
    private final SysUserRepository userRepository;

    public AdminUserController(AdminService adminService, SysUserRepository userRepository) {
        this.adminService = adminService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ApiResponse<List<SysUser>> list() {
        return ApiResponse.ok(adminService.users());
    }


    @GetMapping("/{id}")
    public ApiResponse<SysUser> detail(@PathVariable Long id) {
        return ApiResponse.ok(userRepository.findById(id).orElseThrow());
    }

    @PostMapping
    @OperationAudit(module = "用户管理", action = "新增用户")
    public ApiResponse<SysUser> create(@RequestBody SysUser user) {
        return ApiResponse.ok(adminService.saveUser(user));
    }

    @PutMapping("/{id}")
    @OperationAudit(module = "用户管理", action = "修改用户")
    public ApiResponse<SysUser> update(@PathVariable Long id, @RequestBody SysUser user) {
        user.id = id;
        return ApiResponse.ok(adminService.saveUser(user));
    }

    @PutMapping("/{id}/roles")
    @OperationAudit(module = "用户管理", action = "分配角色")
    public ApiResponse<SysUser> roles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        return ApiResponse.ok(adminService.assignRoles(id, roleIds));
    }

    @DeleteMapping("/{id}")
    @OperationAudit(module = "用户管理", action = "删除用户")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ApiResponse.ok();
    }
}
