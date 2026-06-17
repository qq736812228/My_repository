# 接口联调指南

## 统一响应结构

```json
{
  "code": 0,
  "message": "OK",
  "data": {},
  "traceId": "request-trace-id"
}
```

## 鉴权

登录后端点：

```http
POST /api/auth/login
```

请求：

```json
{
  "username": "admin",
  "password": "admin123"
}
```

返回 `token` 后，管理端接口统一添加：

```http
Authorization: Bearer <token>
X-Trace-Id: admin-xxxx
```

## 管理端核心 CRUD

| 模块 | Endpoint |
|---|---|
| 当前账号 | `/api/admin/me` |
| 用户管理 | `/api/admin/users` |
| 用户分配角色 | `PUT /api/admin/users/{id}/roles` |
| 角色管理 | `/api/admin/roles` |
| 角色分配菜单 | `PUT /api/admin/roles/{id}/menus` |
| 菜单管理 | `/api/admin/menus` |
| 字典管理 | `/api/admin/dict-items` |
| 行政区划 | `/api/admin/regions` |
| 操作日志 | `/api/admin/operation-logs` |
| 健康档案 | `/api/admin/health-archives` |
| 自测记录 | `/api/admin/self-tests` |
| 检测订单 | `/api/admin/detection-orders` |
| 检测报告 | `/api/admin/detection-reports` |
| 商品管理 | `/api/admin/products` |
| 商品评价 | `/api/admin/product-reviews` |
| 价格快照 | `/api/admin/price-snapshots` |
| 商城订单 | `/api/admin/orders` |
| 订单明细 | `/api/admin/order-items` |
| 商户管理 | `/api/admin/merchants` |
| 积分流水 | `/api/admin/point-transactions` |
| 邀请记录 | `/api/admin/invitations` |
| 消息管理 | `/api/admin/messages` |
| 支付回调 | `/api/admin/payment-callbacks` |
| AI 报告 | `/api/admin/ai-reports` |

每个 CRUD 支持：

```http
GET    /api/admin/{endpoint}
GET    /api/admin/{endpoint}/{id}
POST   /api/admin/{endpoint}
PUT    /api/admin/{endpoint}/{id}
DELETE /api/admin/{endpoint}/{id}
```

## 小程序核心 API

| 场景 | Method | API |
|---|---|---|
| 首页数据 | GET | `/api/app/home` |
| 官方入口 | GET | `/api/app/home/official` |
| 公告 | GET | `/api/app/home/announcements` |
| 科普 | GET | `/api/app/home/knowledge` |
| 微信登录 | POST | `/api/auth/wechat-login` |
| 健康档案 | GET/PUT | `/api/app/health/archive` |
| 微态自测 | GET/POST | `/api/app/health/self-tests` |
| 行为记录 | GET/POST | `/api/app/health/behaviors` |
| 检测订单 | GET/POST | `/api/app/detection/orders` |
| 检测报告 | GET | `/api/app/detection/reports` |
| 商品列表 | GET | `/api/app/mall/products` |
| 比价 | GET | `/api/app/mall/products/{id}/price-compare` |
| 创建订单 | POST | `/api/app/mall/orders` |
| 积分中心 | GET | `/api/app/points` |
| 团体客户线索 | POST | `/api/app/leads/group-customer` |
| 合作伙伴线索 | POST | `/api/app/leads/partner` |
| 商家入驻 | POST | `/api/app/leads/merchant-application` |
| 微信支付参数 | POST | `/api/app/wechat/pay/params` |
| 微信支付回调 | POST | `/api/app/wechat/pay/notify` |
| 订阅消息 | POST | `/api/app/wechat/subscribe-message` |


## 微信登录与支付联调

### 微信登录

```http
POST /api/auth/wechat-login
Content-Type: application/json

{
  "code": "wx.login 返回的 code",
  "nickname": "微信用户",
  "avatarUrl": "https://example.com/avatar.png"
}
```

开发环境默认启用 `obai.wechat.dev-fallback-enabled=true`，没有真实微信配置时会生成稳定 OpenID，便于本地联调。生产环境设置为 `false` 后会调用微信 `jscode2session`。

### JSAPI 支付参数

```http
POST /api/app/wechat/pay/params
Authorization: Bearer <mini-program-user-token>
Content-Type: application/json

{
  "orderNo": "MO202606170001"
}
```

返回字段可直接传入 `uni.requestPayment`：`timeStamp`、`nonceStr`、`package`、`signType`、`paySign`。生产环境需配置微信支付商户号、证书序列号、PKCS8 私钥和回调地址。
