package com.obai.platform.controller.admin;

import com.obai.platform.common.ApiResponse;
import com.obai.platform.controller.BaseController;
import com.obai.platform.entity.SysMenu;
import com.obai.platform.entity.SysRole;
import com.obai.platform.entity.SysUser;
import com.obai.platform.repository.SysUserRepository;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminAccountController extends BaseController {
    private final SysUserRepository userRepository;

    public AdminAccountController(SysUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> me() {
        SysUser user = userRepository.findById(currentUserId()).orElseThrow();
        List<String> roles = user.roles.stream().map(role -> role.code).sorted().toList();
        List<SysMenu> menus = user.roles.stream()
                .flatMap(role -> role.menus.stream())
                .filter(menu -> Boolean.TRUE.equals(menu.visible))
                .distinct()
                .sorted(Comparator.comparing(menu -> menu.sortNo == null ? 0 : menu.sortNo))
                .toList();
        List<String> permissions = menus.stream()
                .map(menu -> menu.permissionCode)
                .filter(StringUtils::hasText)
                .distinct()
                .sorted()
                .toList();
        Map<String, Object> profile = new LinkedHashMap<>();
        profile.put("id", user.id);
        profile.put("username", user.username);
        profile.put("nickname", user.nickname);
        profile.put("roles", roles);
        profile.put("menus", menus);
        profile.put("permissions", permissions);
        return ApiResponse.ok(profile);
    }
}
