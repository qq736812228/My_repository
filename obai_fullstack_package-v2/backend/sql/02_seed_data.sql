-- OBAI 基础数据初始化脚本

INSERT INTO sys_role(id, code, name, description, enabled)
VALUES
    (1, 'ADMIN', '管理员', '全量数据读写、后台权限和审计权限', TRUE),
    (2, 'USER', '普通用户', '小程序用户，仅访问自身数据', TRUE),
    (3, 'DOCTOR', '医生/专家', '授权范围内报告与用户健康数据查看', TRUE),
    (4, 'MERCHANT', '商户', '商品发布、批次追踪、反馈查看', TRUE),
    (5, 'AI_AGENT', 'AI Agent', '仅访问受限业务字段，不读取敏感 PII', TRUE)
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_user(id, username, password_hash, nickname, phone, status)
VALUES
    (1, 'admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '平台管理员', '18800000000', 'ENABLED'),
    (2, 'demo_user', 'e606e38b0d8c19b24cf0ee3808183162ea7cd63ff7912dbb22b5e803286b4446', '演示用户', '18800000001', 'ENABLED')
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_user_role(user_id, role_id) VALUES (1, 1), (2, 2) ON CONFLICT DO NOTHING;

INSERT INTO sys_menu(id, parent_id, title, path, component, icon, sort_no, permission_code, visible)
VALUES
    (1, 0, '控制台', '/dashboard', 'Dashboard', 'dashboard', 1, 'dashboard:view', TRUE),
    (2, 0, '系统管理', '/system', 'Layout', 'setting', 2, 'system:view', TRUE),
    (3, 2, '用户管理', '/system/users', 'system/UserList', 'user', 1, 'user:manage', TRUE),
    (4, 2, '角色管理', '/system/roles', 'system/RoleList', 'role', 2, 'role:manage', TRUE),
    (5, 2, '菜单管理', '/system/menus', 'system/MenuList', 'menu', 3, 'menu:manage', TRUE),
    (6, 2, '操作日志', '/system/logs', 'system/LogList', 'log', 4, 'log:view', TRUE),
    (7, 0, '健康业务', '/health', 'Layout', 'leaf', 3, 'health:view', TRUE),
    (8, 7, '健康档案', '/health/archives', 'health/ArchiveList', 'archive', 1, 'archive:manage', TRUE),
    (9, 7, '自测记录', '/health/self-tests', 'health/SelfTestList', 'test', 2, 'selftest:manage', TRUE),
    (10, 7, '检测订单', '/health/detection-orders', 'health/DetectionOrderList', 'lab', 3, 'detection:manage', TRUE),
    (11, 7, '检测报告', '/health/detection-reports', 'health/DetectionReportList', 'report', 4, 'report:manage', TRUE),
    (12, 0, '菌淘商城', '/mall', 'Layout', 'shop', 4, 'mall:view', TRUE),
    (13, 12, '商品管理', '/mall/products', 'mall/ProductList', 'product', 1, 'product:manage', TRUE),
    (14, 12, '订单管理', '/mall/orders', 'mall/OrderList', 'order', 2, 'order:manage', TRUE),
    (15, 12, '商户审核', '/mall/merchants', 'mall/MerchantList', 'merchant', 3, 'merchant:manage', TRUE),
    (16, 0, '积分与共建', '/growth', 'Layout', 'points', 5, 'growth:view', TRUE),
    (17, 16, '积分流水', '/growth/point-transactions', 'growth/PointTransactionList', 'points', 1, 'points:manage', TRUE),
    (18, 16, '邀请记录', '/growth/invitations', 'growth/InvitationList', 'invite', 2, 'invite:manage', TRUE)
ON CONFLICT (id) DO NOTHING;


INSERT INTO sys_menu(id, parent_id, title, path, component, icon, sort_no, permission_code, visible)
VALUES
    (19, 2, '字典管理', '/system/dict-items', 'system/DictItemList', 'dict', 5, 'dict:manage', TRUE),
    (20, 2, '行政区划', '/system/regions', 'system/RegionList', 'region', 6, 'region:manage', TRUE),
    (21, 7, '行为记录', '/health/behaviors', 'health/BehaviorList', 'behavior', 5, 'behavior:manage', TRUE),
    (22, 7, '菌种基础库', '/health/microbe-species', 'health/MicrobeSpeciesList', 'microbe', 6, 'microbe:manage', TRUE),
    (23, 12, '价格快照', '/mall/price-snapshots', 'mall/PriceSnapshotList', 'price', 4, 'price:manage', TRUE),
    (24, 12, '商品评价', '/mall/reviews', 'mall/ProductReviewList', 'review', 5, 'review:manage', TRUE),
    (25, 12, '订单明细', '/mall/order-items', 'mall/OrderItemList', 'order-item', 6, 'order:item:manage', TRUE),
    (26, 12, '商户申请', '/mall/merchant-applications', 'mall/MerchantApplicationList', 'merchant-apply', 7, 'merchant:application:manage', TRUE),
    (27, 16, '积分账户', '/growth/point-accounts', 'growth/PointAccountList', 'account', 3, 'points:account:manage', TRUE),
    (28, 16, '任务中心', '/growth/tasks', 'growth/TaskList', 'task', 4, 'task:manage', TRUE),
    (29, 0, '内容与报告', '/content', 'Layout', 'content', 6, 'content:view', TRUE),
    (30, 29, '消息管理', '/content/messages', 'content/MessageList', 'message', 1, 'message:manage', TRUE),
    (31, 29, '支付回调', '/content/payment-callbacks', 'content/PaymentCallbackList', 'payment', 2, 'payment:callback:view', TRUE),
    (32, 29, 'AI 报告', '/reports/ai-reports', 'reports/AiReportList', 'ai', 3, 'ai:report:manage', TRUE),
    (33, 0, '线索管理', '/leads', 'Layout', 'lead', 7, 'lead:view', TRUE),
    (34, 33, '团体客户线索', '/leads/group', 'lead/GroupLeadList', 'group', 1, 'lead:group:manage', TRUE),
    (35, 33, '合作伙伴线索', '/leads/partner', 'lead/PartnerLeadList', 'partner', 2, 'lead:partner:manage', TRUE)
ON CONFLICT (id) DO NOTHING;

INSERT INTO sys_role_menu(role_id, menu_id)
SELECT 1, id FROM sys_menu
ON CONFLICT DO NOTHING;

INSERT INTO sys_dictionary_item(dict_code, item_key, item_value, sort_no)
VALUES
    ('user_status', 'ENABLED', '启用', 1),
    ('user_status', 'DISABLED', '停用', 2),
    ('detection_status', 'CREATED', '已创建', 1),
    ('detection_status', 'SAMPLED', '已采样', 2),
    ('detection_status', 'REPORTED', '已出报告', 3),
    ('product_status', 'ON_SALE', '上架', 1),
    ('product_status', 'OFF_SALE', '下架', 2),
    ('merchant_status', 'PENDING', '待审核', 1),
    ('merchant_status', 'APPROVED', '审核通过', 2),
    ('merchant_status', 'REJECTED', '审核拒绝', 3)
ON CONFLICT DO NOTHING;

INSERT INTO sys_region(code, name, parent_code, level, sort_no)
VALUES
    ('370000', '山东省', NULL, 'province', 1),
    ('370200', '青岛市', '370000', 'city', 1),
    ('370202', '市南区', '370200', 'district', 1),
    ('370203', '市北区', '370200', 'district', 2),
    ('370211', '黄岛区', '370200', 'district', 3)
ON CONFLICT (code) DO NOTHING;

INSERT INTO merchant(id, name, contact_name, contact_phone, license_no, status, credit_score, audit_remark)
VALUES
    (1, 'OBAI 官方优选', '平台运营', '18800000002', 'LICENSE-OBAI-001', 'APPROVED', 96, '平台自营与优选商品')
ON CONFLICT (id) DO NOTHING;

INSERT INTO product(id, merchant_id, name, category, brand, probiotic_strains, price, market_min_price, market_max_price, point_deduction_limit, status, quality_status, image_url, description)
VALUES
    (1, 1, 'OBAI 益生菌基础装', 'PROBIOTICS', 'OBAI', 'Bifidobacterium longum; Lactobacillus plantarum', 198.00, 188.00, 268.00, 300, 'ON_SALE', 'VERIFIED', '/static/products/probiotic-box.png', '入门型肠道微生态长期记录配套产品，非药品。'),
    (2, 1, 'OBAI 益生元随行条', 'PREBIOTICS', 'OBAI', 'Inulin; FOS', 99.00, 89.00, 129.00, 120, 'ON_SALE', 'VERIFIED', '/static/products/prebiotic-stick.png', '膳食纤维方向产品，适合与行为记录结合观察。')
ON CONFLICT (id) DO NOTHING;

INSERT INTO product_price_snapshot(product_id, channel_name, price, url, captured_at)
VALUES
    (1, 'OBAI 平台', 198.00, 'https://obai.local/mall/1', now()),
    (1, '主流平台 A', 218.00, 'https://example.com/a', now()),
    (1, '主流平台 B', 236.00, 'https://example.com/b', now()),
    (2, 'OBAI 平台', 99.00, 'https://obai.local/mall/2', now()),
    (2, '主流平台 A', 108.00, 'https://example.com/c', now())
ON CONFLICT DO NOTHING;

INSERT INTO point_account(user_id, balance, total_earned, total_used)
VALUES (1, 9999, 9999, 0), (2, 2680, 2680, 0)
ON CONFLICT DO NOTHING;

INSERT INTO point_transaction(user_id, amount, type, source, ref_no, description)
VALUES
    (2, 2680, 'EARN', 'INIT', 'INIT-0001', '新用户初始化积分'),
    (2, 36, 'EARN', 'DAILY_RECORD', 'DR-0001', '今日微态记录奖励')
ON CONFLICT DO NOTHING;

INSERT INTO task_item(code, title, description, reward_points, enabled)
VALUES
    ('DAILY_SELF_TEST', '完成每日微态记录', '记录排便、睡眠、压力、饮食等自感状态', 36, TRUE),
    ('ARCHIVE_COMPLETE', '完善健康档案', '补充基础档案与行为背景', 120, TRUE),
    ('INVITE_FRIEND', '邀请好友共建', '邀请好友加入长期健康数据共建', 180, TRUE)
ON CONFLICT (code) DO NOTHING;

INSERT INTO health_archive(user_id, gender, birthday, height_cm, weight_kg, diet_preference, sleep_pattern, bowel_habit, completeness_score)
VALUES (2, 'unknown', '1990-01-01', '170', '65', '清淡饮食', '23:30-07:00', '每日 1 次', 72)
ON CONFLICT DO NOTHING;

INSERT INTO detection_order(user_id, order_no, sample_no, test_type, status, institution_name, sampled_at)
VALUES (2, 'DT-DEMO-0001', 'S-DEMO-0001', '16S', 'REPORTED', 'OBAI 合作检测机构', now())
ON CONFLICT (order_no) DO NOTHING;

INSERT INTO detection_report(order_id, user_id, report_no, health_score, dominant_species, diversity_level, summary, raw_json)
VALUES (1, 2, 'RP-DEMO-0001', 82, 'Bifidobacterium; Lactobacillus', 'MEDIUM', '整体菌群多样性中等，建议结合饮食、睡眠与复测持续观察。', '{}')
ON CONFLICT (report_no) DO NOTHING;

SELECT setval(pg_get_serial_sequence('sys_role', 'id'), COALESCE((SELECT max(id) FROM sys_role), 1));
SELECT setval(pg_get_serial_sequence('sys_user', 'id'), COALESCE((SELECT max(id) FROM sys_user), 1));
SELECT setval(pg_get_serial_sequence('sys_menu', 'id'), COALESCE((SELECT max(id) FROM sys_menu), 1));
SELECT setval(pg_get_serial_sequence('merchant', 'id'), COALESCE((SELECT max(id) FROM merchant), 1));
SELECT setval(pg_get_serial_sequence('product', 'id'), COALESCE((SELECT max(id) FROM product), 1));
