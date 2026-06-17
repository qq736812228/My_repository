# 交付验收清单

## 源码结构

- [x] `backend/` Spring Boot 源码与 SQL 脚本。
- [x] `admin/` Vue3 + Vite 管理端源码。
- [x] `app/` uni-app 微信小程序源码。
- [x] `docs/` 部署、接口、数据库和按钮映射文档。

## 后端

- [x] 统一包名 `com.obai.platform`。
- [x] RESTful API 与统一响应结构。
- [x] JWT 登录、微信登录 jscode2session、RBAC 基础表与 ADMIN 后台访问控制。
- [x] Service/Repository/Controller 分层。
- [x] 操作日志 AOP 落库。
- [x] Swagger/OpenAPI/Knife4j 依赖集成。
- [x] Checkstyle 与 Sonar 配置。

## 数据库

- [x] PostgreSQL 全量建表。
- [x] 主键、外键、索引。
- [x] 菜单、角色、用户、字典、行政区、商品、积分基础数据。

## 管理端

- [x] 登录态与 Axios 拦截器。
- [x] 控制台报表。
- [x] 用户、角色、菜单、字典、行政区、日志、档案、检测、商城、商户、订单明细、支付回调、积分、邀请等通用 CRUD。
- [x] ESLint + Prettier。
- [x] dev/test/prod 环境配置。

## 小程序

- [x] 首页视觉与截图功能口径对齐。
- [x] 全部功能按钮已绑定请求逻辑或页面跳转。
- [x] 微信登录、JSAPI 支付参数、支付回调、订阅消息调用封装。
- [x] 档案、自测、检测、报告、积分、邀请、商城、商户申请等页面。

## 部署

- [x] 后端 `mvn clean package`。
- [x] 管理端 `npm run build`。
- [x] 小程序 `npm run build:mp-weixin`。
- [x] Docker Compose 示例。
