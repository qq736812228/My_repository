# OBAI 工程交付文档目录

本文档集用于支持 OBAI 工程从本地开发、接口联调、数据库初始化到生产部署的交付接收。

## 文档清单

| 文档 | 说明 |
|---|---|
| PROJECT_STRUCTURE.md | 工程结构、包名、目录职责 |
| DEPLOYMENT.md | dev/test/prod 启动与部署说明 |
| API_INTEGRATION.md | 前后端、小程序接口联调说明 |
| DATABASE_RELATIONSHIP.md | PostgreSQL 表关系、索引和初始化说明 |
| MINI_PROGRAM_BUTTON_MAPPING.md | 小程序首页按钮到 API 的映射 |
| ACCEPTANCE_CHECKLIST.md | 交付验收清单 |
| openapi/obai-openapi-v1.yaml | 核心接口 OpenAPI 摘要 |

## 重要边界

OBAI 是健康管理辅助和长期稳态观察平台，不替代医院或医生，不输出医疗诊断、治疗方案、处方建议或疗效承诺。工程实现中的自测、AI 报告、商品匹配和积分激励均按“非诊断健康观察”口径设计。
