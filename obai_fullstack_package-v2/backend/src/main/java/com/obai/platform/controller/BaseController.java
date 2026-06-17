package com.obai.platform.controller;

import com.obai.platform.common.BusinessException;
import com.obai.platform.common.RequestContext;

public abstract class BaseController {
    protected Long currentUserId() {
        Long userId = RequestContext.userId();
        if (userId == null) {
            throw new BusinessException(401, "请先登录");
        }
        return userId;
    }

    protected Long optionalUserId() {
        return RequestContext.userId();
    }
}
