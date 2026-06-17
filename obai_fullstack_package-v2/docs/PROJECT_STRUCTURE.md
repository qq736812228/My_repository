# 项目结构说明

```text
obai_fullstack_package/
├── backend/
│   ├── pom.xml
│   ├── src/main/java/com/obai/platform/
│   │   ├── common/        # 统一响应、异常、Trace 上下文、哈希工具
│   │   ├── config/        # CORS、OpenAPI、Trace Filter
│   │   ├── security/      # JWT 工具与后台接口鉴权过滤器
│   │   ├── audit/         # 操作审计注解与 AOP 落库
│   │   ├── entity/        # JPA Entity，对应 PostgreSQL 表
│   │   ├── repository/    # DAO/Repository 层
│   │   ├── service/       # 业务 Service 层
│   │   └── controller/    # admin 与 app REST Controller
│   └── sql/               # PostgreSQL 建表与初始化脚本
├── admin/
│   ├── src/api/           # Axios 请求封装
│   ├── src/router/        # 路由与菜单
│   ├── src/stores/        # Pinia 登录状态
│   ├── src/layout/        # 后台布局
│   └── src/views/         # 登录、看板、通用 CRUD
├── app/
│   ├── api/               # 小程序 API 封装
│   ├── utils/             # 请求工具
│   └── pages/             # 首页、档案、商城、消息、我的等页面
└── docs/
```

## 后端统一包名

后端统一使用：`com.obai.platform`。

## 前端统一目录

管理端统一采用 Vite + Vue3 常见结构：`src/api`、`src/router`、`src/stores`、`src/views`、`src/layout`。

小程序端按 uni-app 标准结构组织：`pages.json`、`manifest.json`、`App.vue`、`main.js`、`pages/*`。
