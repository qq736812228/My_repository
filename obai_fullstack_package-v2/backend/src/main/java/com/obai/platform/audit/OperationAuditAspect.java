package com.obai.platform.audit;

import com.obai.platform.common.RequestContext;
import com.obai.platform.entity.OperationLog;
import com.obai.platform.repository.OperationLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class OperationAuditAspect {
    private final OperationLogRepository repository;

    public OperationAuditAspect(OperationLogRepository repository) {
        this.repository = repository;
    }

    @Around("@annotation(audit)")
    public Object around(ProceedingJoinPoint joinPoint, OperationAudit audit) throws Throwable {
        long start = System.currentTimeMillis();
        int status = 200;
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            status = 500;
            throw ex;
        } finally {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                OperationLog log = new OperationLog();
                log.userId = RequestContext.userId();
                log.username = log.userId == null ? "anonymous" : String.valueOf(log.userId);
                log.module = audit.module();
                log.action = audit.action();
                log.method = request.getMethod();
                log.path = request.getRequestURI();
                log.clientIp = request.getRemoteAddr();
                log.statusCode = status;
                log.costMs = System.currentTimeMillis() - start;
                log.requestId = RequestContext.traceId();
                log.operatedAt = Instant.now();
                repository.save(log);
            }
        }
    }
}
