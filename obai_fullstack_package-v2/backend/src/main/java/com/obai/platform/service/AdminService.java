package com.obai.platform.service;

import com.obai.platform.common.EntityPatchUtil;
import com.obai.platform.common.HashUtil;
import com.obai.platform.entity.SysMenu;
import com.obai.platform.entity.SysRole;
import com.obai.platform.entity.SysUser;
import com.obai.platform.repository.SysMenuRepository;
import com.obai.platform.repository.SysRoleRepository;
import com.obai.platform.repository.SysUserRepository;
import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final SysUserRepository userRepository;
    private final SysRoleRepository roleRepository;
    private final SysMenuRepository menuRepository;

    public AdminService(SysUserRepository userRepository, SysRoleRepository roleRepository,
                        SysMenuRepository menuRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.menuRepository = menuRepository;
    }

    public List<SysUser> users() {
        return userRepository.findAll();
    }

    @Transactional
    public SysUser saveUser(SysUser user) {
        if (user.id == null) {
            if (user.passwordHash == null || user.passwordHash.isBlank()) {
                user.passwordHash = HashUtil.sha256("Obai@123456");
            }
            return userRepository.save(user);
        }
        SysUser target = userRepository.findById(user.id).orElseThrow();
        EntityPatchUtil.copyNonNull(user, target);
        return userRepository.save(target);
    }

    @Transactional
    public SysUser assignRoles(Long userId, List<Long> roleIds) {
        SysUser user = userRepository.findById(userId).orElseThrow();
        List<SysRole> roles = roleRepository.findAllById(roleIds);
        user.roles = new HashSet<>(roles);
        return userRepository.save(user);
    }

    @Transactional
    public SysRole assignMenus(Long roleId, List<Long> menuIds) {
        SysRole role = roleRepository.findById(roleId).orElseThrow();
        List<SysMenu> menus = menuRepository.findAllById(menuIds);
        role.menus = new HashSet<>(menus);
        return roleRepository.save(role);
    }
}
