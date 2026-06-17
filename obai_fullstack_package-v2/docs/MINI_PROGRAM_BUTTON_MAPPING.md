# 小程序首页按钮与接口映射

| 首页模块 | 按钮/入口 | 前端方法 | 后端接口 | 数据持久化 |
|---|---|---|---|---|
| 快速入口 | 团体客户入口 | `goGroup` / `api.groupLead` | `POST /api/app/leads/group-customer` | `group_customer_lead` |
| 快速入口 | 合作伙伴入口 | `goPartner` / `api.partnerLead` | `POST /api/app/leads/partner` | `partner_lead` |
| 快速入口 | 注册 / 登录 | `goLogin` / `api.login` / `api.wechatLogin` | `POST /api/auth/login`, `POST /api/auth/wechat-login` | `sys_user`, `point_account` |
| 快速入口 | 官方入口 | `goOfficial` / `api.official` | `GET /api/app/home/official` | 无，读取平台配置 |
| 身体可知 | 专业检测 / 去检测 | `startDetection` | `POST /api/app/detection/orders` | `detection_order` |
| 身体可知 | 档案报告 / 查看档案 | `goArchive` | `GET /api/app/health/archive`, `GET /api/app/detection/reports` | `health_archive`, `detection_report` |
| 身体可知 | 微态自测 / 开始记录 | `goSelfTest`, `submitSelfTest` | `POST /api/app/health/self-tests` | `self_test_record` |
| OB 积分 | 积分明细/任务中心 | `goPoints` | `GET /api/app/points` | `point_account`, `point_transaction`, `task_item` |
| 邀请共建 | 立即邀请 | `goInvite` | 分享链路，可扩展 `invitation` | `invitation` |
| 菌淘商城 | 进入商城/购买 | `goMall`, `buy` | `GET /api/app/mall/products`, `POST /api/app/mall/orders` | `mall_order`, `mall_order_item` |
| 菌淘商城 | 商品比价 | `api.priceCompare` | `GET /api/app/mall/products/{id}/price-compare` | `product_price_snapshot` |
| 商家发布 | 立即查看/提交申请 | `goMerchant`, `merchantApply` | `POST /api/app/leads/merchant-application` | `merchant_application` |
| 平台公告 | 更多 | `goMessage` / `api.announcements` | `GET /api/app/home/announcements` | `notification_message` 可扩展 |
| 科普中心 | 更多 | `goKnowledge` / `api.knowledge` | `GET /api/app/home/knowledge` | 内容表可扩展 |
| 消息 | 订阅消息 | `sub` | `POST /api/app/wechat/subscribe-message` | 可扩展消息订阅表 |
| 商城支付 | 微信支付 | `uni.requestPayment` | `POST /api/app/wechat/pay/params`, `POST /api/app/wechat/pay/notify` | `payment_callback` |
