# 数据库表关系说明

## 权限与用户

```text
sys_user 1--N sys_user_role N--1 sys_role
sys_role 1--N sys_role_menu N--1 sys_menu
```

核心用途：登录、JWT、RBAC、后台菜单与权限边界。

## 健康档案与长期记录

```text
sys_user 1--1 health_archive
sys_user 1--N self_test_record
sys_user 1--N behavior_record
sys_user 1--N detection_order
sys_user 1--N detection_report
```

- `health_archive`：长期健康档案。
- `self_test_record`：微态自测、题库版本、行为标签、风险等级。
- `behavior_record`：饮食、运动、睡眠、排便、压力等时序记录。
- `detection_order`：样本、检测类型和状态流转。
- `detection_report`：报告摘要、评分、菌群基础结果。

## 商城与可信商品

```text
merchant 1--N product
product 1--N product_price_snapshot
product 1--N product_review
sys_user 1--N mall_order
mall_order 1--N mall_order_item
```

- `merchant`：商户准入、信用分、审核状态。
- `product`：商品、菌株信息、价格区间、积分抵扣。
- `product_price_snapshot`：多渠道价格快照，支持“价格可比”。
- `product_review`：反馈评价与真实口碑。

## 积分与共建

```text
sys_user 1--1 point_account
sys_user 1--N point_transaction
sys_user 1--N invitation
```

- `point_account`：当前积分、累计获取和累计使用。
- `point_transaction`：积分变动审计流水。
- `invitation`：邀请共建记录与奖励。

## 审计与消息

- `operation_log`：后台操作日志，包含 request_id、user_id、路径、耗时、状态码。
- `notification_message`：公告、服务通知、报告提醒。
- `payment_callback`：微信支付回调原始记录与幂等审计。
