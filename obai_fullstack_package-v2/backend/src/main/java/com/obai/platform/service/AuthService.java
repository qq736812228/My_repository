package com.obai.platform.service;

import com.obai.platform.common.BusinessException;
import com.obai.platform.common.HashUtil;
import com.obai.platform.dto.LoginRequest;
import com.obai.platform.dto.RegisterRequest;
import com.obai.platform.dto.TokenResponse;
import com.obai.platform.dto.WechatLoginRequest;
import com.obai.platform.entity.PointAccount;
import com.obai.platform.entity.SysRole;
import com.obai.platform.entity.SysUser;
import com.obai.platform.repository.PointAccountRepository;
import com.obai.platform.repository.SysRoleRepository;
import com.obai.platform.repository.SysUserRepository;
import com.obai.platform.security.JwtUtil;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final SysUserRepository userRepository;
    private final SysRoleRepository roleRepository;
    private final PointAccountRepository pointAccountRepository;
    private final JwtUtil jwtUtil;
    private final WechatService wechatService;

    public AuthService(SysUserRepository userRepository, SysRoleRepository roleRepository,
                       PointAccountRepository pointAccountRepository, JwtUtil jwtUtil,
                       WechatService wechatService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.pointAccountRepository = pointAccountRepository;
        this.jwtUtil = jwtUtil;
        this.wechatService = wechatService;
    }

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BusinessException(409, "用户名已存在");
        });
        SysUser user = new SysUser();
        user.username = request.username();
        user.nickname = request.nickname() == null ? request.username() : request.nickname();
        user.phone = request.phone();
        user.passwordHash = HashUtil.sha256(request.password());
        roleRepository.findByCode("USER").ifPresent(user.roles::add);
        SysUser saved = userRepository.save(user);
        createInitialPointAccount(saved.id);
        return token(saved);
    }

    public TokenResponse login(LoginRequest request) {
        SysUser user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessException(401, "用户名或密码错误"));
        if (!HashUtil.sha256(request.password()).equals(user.passwordHash)) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        if (!"ENABLED".equals(user.status)) {
            throw new BusinessException(403, "账号已停用");
        }
        return token(user);
    }

    @Transactional
    public TokenResponse wechatLogin(WechatLoginRequest request) {
        WechatService.WechatSession session = wechatService.code2Session(request.code());
        SysUser user = userRepository.findByOpenid(session.openid()).orElseGet(() -> {
            SysUser created = new SysUser();
            created.openid = session.openid();
            created.username = "wx_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            created.passwordHash = HashUtil.sha256(UUID.randomUUID().toString());
            created.nickname = request.nickname() == null || request.nickname().isBlank() ? "微信用户" : request.nickname();
            created.avatarUrl = request.avatarUrl();
            roleRepository.findByCode("USER").ifPresent(created.roles::add);
            SysUser saved = userRepository.save(created);
            createInitialPointAccount(saved.id);
            return saved;
        });
        return token(user);
    }

    public TokenResponse token(SysUser user) {
        List<String> roles = user.roles.stream().map(role -> role.code).toList();
        return new TokenResponse(jwtUtil.createToken(user.id, user.username), user.id, user.username, user.nickname, roles);
    }

    private void createInitialPointAccount(Long userId) {
        PointAccount account = new PointAccount();
        account.userId = userId;
        account.balance = 2680;
        account.totalEarned = 2680;
        pointAccountRepository.save(account);
    }
}
