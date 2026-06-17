# 部署与启动说明

## 1. 本地开发环境

### 数据库

```bash
cd backend
psql -U postgres -f sql/00_create_database.sql
psql -U postgres -d obai -f sql/01_schema_postgresql.sql
psql -U postgres -d obai -f sql/02_seed_data.sql
```

### 后端

```bash
cd backend
mvn clean package
java -jar target/obai-platform-1.0.0.jar --spring.profiles.active=dev
```

接口文档：

- Swagger UI：`/swagger-ui.html`
- OpenAPI JSON：`/v3/api-docs`
- Knife4j：依赖已集成，可按项目网关路径访问。

### 管理端

```bash
cd admin
npm install
npm run dev
npm run build
```

### 小程序端

```bash
cd app
npm install
npm run dev:mp-weixin
npm run build:mp-weixin
```

## 2. Docker Compose

```bash
docker compose up -d --build
```

默认服务：

| 服务 | 端口 |
|---|---:|
| PostgreSQL | 5432 |
| Spring Boot 后端 | 8080 |

## 3. 环境变量

后端敏感配置通过环境变量注入：

| 变量 | 说明 |
|---|---|
| OBAI_DB_URL | PostgreSQL JDBC 地址 |
| OBAI_DB_USERNAME | 数据库用户 |
| OBAI_DB_PASSWORD | 数据库密码 |
| OBAI_JWT_SECRET | JWT 签名密钥 |
| OBAI_WECHAT_APPID | 微信小程序 AppID |
| OBAI_WECHAT_SECRET | 微信小程序密钥 |
| OBAI_WECHAT_DEV_FALLBACK_ENABLED | 开发环境是否启用微信登录/支付本地兜底，生产建议 false |
| OBAI_WECHAT_PAY_MCH_ID | 微信支付商户号 |
| OBAI_WECHAT_PAY_SERIAL_NO | 微信支付 API 证书序列号 |
| OBAI_WECHAT_PAY_PRIVATE_KEY | 微信支付 PKCS8 商户私钥，建议通过密钥管理系统注入 |
| OBAI_WECHAT_PAY_NOTIFY_URL | 微信支付回调地址 |

管理端和小程序端分别使用 `.env.development`、`.env.test`、`.env.production` 管理接口地址。

## 4. 生产部署注意项

1. 禁止将数据库密码、JWT 密钥、微信支付密钥写入源码或镜像。
2. 生产环境必须使用独立 PostgreSQL 数据卷、备份计划和只读账号。
3. 后台接口统一携带 `Authorization: Bearer <token>` 和 `X-Trace-Id`。
4. 操作日志已经按 AOP 落库，生产环境建议对 `operation_log` 做冷热分区或归档。
5. 微信登录已接入 `jscode2session` 调用；开发环境默认启用本地兜底，生产需设置 `OBAI_WECHAT_DEV_FALLBACK_ENABLED=false` 并提供真实 AppID/Secret。
6. 微信支付已实现 JSAPI 预下单请求签名和前端支付参数二次签名；生产需配置商户号、证书序列号、PKCS8 私钥、回调域名，并按微信支付 V3 回调规范补充平台证书解密验签。
7. 后台接口已在 Filter 层限制 ADMIN 角色访问，普通小程序用户 Token 不能访问 `/api/admin/**`。


## 5. 接口冒烟测试

后端启动并导入种子数据后，可执行：

```bash
cd docs
BASE_URL=http://localhost:8080 ./api-smoke-test.sh
```

脚本会覆盖登录、后台 ADMIN 权限、普通用户禁止访问后台、首页、积分等关键链路。需要本地安装 `curl` 和 `jq`。
