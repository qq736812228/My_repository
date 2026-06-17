# 耦白 OBAI 全栈整合工程包

本工程包按 Java 后端（Spring Boot）+ Vue3 管理端（Vite）+ uni-app 微信小程序三端结构组织，覆盖 OBAI 肠道微生态稳态建设数据 AI 健康管理平台的 MVP 级闭环功能。

> 交付说明：当前包基于归档口径和本次界面/功能要求生成整合版可运行工程。由于本会话未提供真实历史 Git 仓库或历史源码，包内不包含逐 commit 的历史源码合并记录；但已按统一目录、统一包名、统一接口、统一 SQL、统一配置和统一文档输出。

## 目录

```text
backend/   Spring Boot 后端源码、PostgreSQL SQL、Checkstyle、部署文件
admin/     Vue3 + Vite 后台管理端源码、ESLint + Prettier、环境配置
app/       uni-app 微信小程序源码、按钮交互、微信登录/支付/订阅消息适配
docs/      部署说明、接口联调指南、数据库关系说明、小程序按钮映射、验收清单、继续完成记录
```

## 快速启动

```bash
# 1. 启动数据库
cd backend
psql -U postgres -f sql/01_schema_postgresql.sql
psql -U postgres -d obai -f sql/02_seed_data.sql

# 2. 启动后端
cd backend
mvn clean package
java -jar target/obai-platform-1.0.0.jar --spring.profiles.active=dev

# 3. 启动管理端
cd admin
npm install
npm run dev

# 4. 启动小程序
cd app
npm install
npm run dev:mp-weixin
```

默认管理账号：`admin` / `admin123`。

## 核心功能覆盖

- 后端 RESTful API、JWT 登录、RBAC 基础权限、操作日志、管理端 CRUD、业务统计报表。
- PostgreSQL 全量建表脚本、主键/外键/索引、字典/菜单/角色/行政区基础数据。
- 管理端用户、角色、菜单、日志、健康档案、检测订单、报告、商城、商户、积分、消息管理。
- 小程序端首页全部按钮绑定请求逻辑：团体客户、合作伙伴、注册/登录、官方入口、专业检测、档案报告、微态自测、积分、邀请共建、菌淘商城、商家发布、公告、科普、消息、我的。
- 微信登录、JSAPI 支付参数生成、支付回调、订阅消息接口和前端调用封装。
- 管理端 `/api/admin/**` 已限制 ADMIN 角色，普通小程序用户 Token 无法访问后台接口。
- 通用 CRUD 已使用显式字段 Schema 与非空补丁式更新，避免空数据页面无法新增或局部编辑覆盖旧值。
- dev/test/prod 多环境配置、Docker Compose、CI 示例、Checkstyle、ESLint、Prettier、Sonar 配置。


## 继续完成 V2

详见 `docs/CONTINUATION_COMPLETION_LOG.md`。本轮重点补齐严格鉴权、后台字段 Schema、非空补丁式更新、微信登录生产调用、微信支付 JSAPI 预下单签名、支付回调状态联动，以及字典/行政区/订单明细/支付回调等后台入口。
