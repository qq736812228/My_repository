-- OBAI PostgreSQL 全量建表脚本
-- 目标：新环境一键建表，包含主键、外键、索引和审计字段。

CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT,
    title VARCHAR(100) NOT NULL,
    path VARCHAR(200) NOT NULL,
    component VARCHAR(200),
    icon VARCHAR(80),
    sort_no INTEGER DEFAULT 0,
    permission_code VARCHAR(120),
    visible BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS sys_role (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(64) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(128) NOT NULL,
    nickname VARCHAR(100),
    phone VARCHAR(32),
    avatar_url VARCHAR(500),
    openid VARCHAR(120),
    status VARCHAR(32) DEFAULT 'ENABLED',
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_sys_user_openid ON sys_user(openid) WHERE openid IS NOT NULL;

CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES sys_role(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS sys_role_menu (
    role_id BIGINT NOT NULL REFERENCES sys_role(id) ON DELETE CASCADE,
    menu_id BIGINT NOT NULL REFERENCES sys_menu(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, menu_id)
);

CREATE TABLE IF NOT EXISTS operation_log (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    username VARCHAR(100),
    module VARCHAR(100),
    action VARCHAR(100),
    method VARCHAR(20),
    path VARCHAR(300),
    client_ip VARCHAR(80),
    status_code INTEGER,
    cost_ms BIGINT,
    request_id VARCHAR(80),
    remark VARCHAR(500),
    operated_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_operation_log_user_time ON operation_log(user_id, operated_at DESC);
CREATE INDEX IF NOT EXISTS idx_operation_log_request ON operation_log(request_id);

CREATE TABLE IF NOT EXISTS sys_dictionary_item (
    id BIGSERIAL PRIMARY KEY,
    dict_code VARCHAR(80) NOT NULL,
    item_key VARCHAR(80) NOT NULL,
    item_value VARCHAR(200) NOT NULL,
    sort_no INTEGER DEFAULT 0,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE UNIQUE INDEX IF NOT EXISTS uk_dict_key ON sys_dictionary_item(dict_code, item_key);

CREATE TABLE IF NOT EXISTS sys_region (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    parent_code VARCHAR(20),
    level VARCHAR(20),
    sort_no INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS health_archive (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    gender VARCHAR(20),
    birthday DATE,
    height_cm VARCHAR(20),
    weight_kg VARCHAR(20),
    diet_preference VARCHAR(300),
    sleep_pattern VARCHAR(300),
    bowel_habit VARCHAR(300),
    chronic_history VARCHAR(500),
    allergy_history VARCHAR(500),
    red_flag_note VARCHAR(500),
    completeness_score INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE UNIQUE INDEX IF NOT EXISTS uk_health_archive_user ON health_archive(user_id);

CREATE TABLE IF NOT EXISTS self_test_record (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    version VARCHAR(40),
    symptom_tags VARCHAR(500),
    behavior_tags VARCHAR(500),
    stool_status VARCHAR(100),
    sleep_status VARCHAR(100),
    pressure_level VARCHAR(80),
    answer_json TEXT,
    score INTEGER DEFAULT 0,
    risk_level VARCHAR(40),
    advice_summary TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_self_test_user_time ON self_test_record(user_id, created_at DESC);

CREATE TABLE IF NOT EXISTS behavior_record (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    record_date DATE NOT NULL,
    diet_tags VARCHAR(500),
    exercise_tags VARCHAR(500),
    sleep_hours VARCHAR(40),
    stool_frequency VARCHAR(80),
    pressure_level VARCHAR(80),
    note TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_behavior_record_user_date ON behavior_record(user_id, record_date DESC);
CREATE INDEX IF NOT EXISTS idx_behavior_record_month ON behavior_record(user_id, record_date);

CREATE TABLE IF NOT EXISTS detection_order (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    order_no VARCHAR(80) NOT NULL UNIQUE,
    sample_no VARCHAR(80) UNIQUE,
    test_type VARCHAR(40),
    status VARCHAR(40),
    institution_name VARCHAR(200),
    sampled_at TIMESTAMPTZ,
    reported_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_detection_order_user ON detection_order(user_id, created_at DESC);

CREATE TABLE IF NOT EXISTS detection_report (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES detection_order(id) ON DELETE SET NULL,
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    report_no VARCHAR(80) NOT NULL UNIQUE,
    health_score INTEGER,
    dominant_species VARCHAR(500),
    diversity_level VARCHAR(80),
    summary TEXT,
    raw_json TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_detection_report_user ON detection_report(user_id, created_at DESC);

CREATE TABLE IF NOT EXISTS microbe_species (
    id BIGSERIAL PRIMARY KEY,
    latin_name VARCHAR(200) NOT NULL,
    cn_name VARCHAR(200),
    function_tag VARCHAR(200),
    evidence_level VARCHAR(40),
    description VARCHAR(1000),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE UNIQUE INDEX IF NOT EXISTS uk_microbe_latin ON microbe_species(latin_name);

CREATE TABLE IF NOT EXISTS merchant (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    contact_name VARCHAR(100),
    contact_phone VARCHAR(32),
    license_no VARCHAR(120),
    status VARCHAR(40),
    credit_score INTEGER DEFAULT 60,
    audit_remark VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_merchant_status ON merchant(status);

CREATE TABLE IF NOT EXISTS merchant_application (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES sys_user(id) ON DELETE SET NULL,
    merchant_name VARCHAR(200),
    contact_name VARCHAR(100),
    contact_phone VARCHAR(32),
    license_no VARCHAR(120),
    status VARCHAR(40),
    audit_remark VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS product (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT REFERENCES merchant(id) ON DELETE SET NULL,
    name VARCHAR(200) NOT NULL,
    category VARCHAR(80),
    brand VARCHAR(100),
    probiotic_strains VARCHAR(500),
    price NUMERIC(12,2),
    market_min_price NUMERIC(12,2),
    market_max_price NUMERIC(12,2),
    point_deduction_limit INTEGER DEFAULT 0,
    status VARCHAR(40),
    quality_status VARCHAR(40),
    image_url VARCHAR(500),
    description TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_product_status ON product(status, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_product_merchant ON product(merchant_id);

CREATE TABLE IF NOT EXISTS product_price_snapshot (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    channel_name VARCHAR(100),
    price NUMERIC(12,2),
    url VARCHAR(500),
    captured_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_price_snapshot_product ON product_price_snapshot(product_id, captured_at DESC);

CREATE TABLE IF NOT EXISTS product_review (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES sys_user(id) ON DELETE SET NULL,
    rating INTEGER,
    tags VARCHAR(500),
    content TEXT,
    verified_purchase BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS mall_order (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    order_no VARCHAR(80) NOT NULL UNIQUE,
    total_amount NUMERIC(12,2),
    points_used INTEGER DEFAULT 0,
    status VARCHAR(40),
    pay_channel VARCHAR(40),
    transaction_id VARCHAR(120),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_mall_order_user ON mall_order(user_id, created_at DESC);

CREATE TABLE IF NOT EXISTS mall_order_item (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES mall_order(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES product(id) ON DELETE SET NULL,
    product_name VARCHAR(200),
    quantity INTEGER,
    unit_price NUMERIC(12,2),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS point_account (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    balance INTEGER DEFAULT 0,
    total_earned INTEGER DEFAULT 0,
    total_used INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE UNIQUE INDEX IF NOT EXISTS uk_point_account_user ON point_account(user_id);

CREATE TABLE IF NOT EXISTS point_transaction (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    amount INTEGER NOT NULL,
    type VARCHAR(40),
    source VARCHAR(80),
    ref_no VARCHAR(120),
    description VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_point_transaction_user ON point_transaction(user_id, created_at DESC);

CREATE TABLE IF NOT EXISTS task_item (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(80) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    reward_points INTEGER DEFAULT 0,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS invitation (
    id BIGSERIAL PRIMARY KEY,
    inviter_user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    invitee_user_id BIGINT REFERENCES sys_user(id) ON DELETE SET NULL,
    invite_code VARCHAR(80) NOT NULL,
    status VARCHAR(40),
    reward_points INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_invitation_inviter ON invitation(inviter_user_id, created_at DESC);

CREATE TABLE IF NOT EXISTS notification_message (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES sys_user(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    type VARCHAR(40),
    read_flag BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_message_user ON notification_message(user_id, read_flag, created_at DESC);

CREATE TABLE IF NOT EXISTS ai_report (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES sys_user(id) ON DELETE CASCADE,
    report_no VARCHAR(80) NOT NULL UNIQUE,
    report_type VARCHAR(80),
    stability_score INTEGER,
    risk_level VARCHAR(40),
    conclusion TEXT,
    suggestions TEXT,
    status VARCHAR(40),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_ai_report_user ON ai_report(user_id, created_at DESC);

CREATE TABLE IF NOT EXISTS payment_callback (
    id BIGSERIAL PRIMARY KEY,
    order_no VARCHAR(80),
    pay_channel VARCHAR(40),
    transaction_id VARCHAR(120),
    status VARCHAR(40),
    raw_payload TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX IF NOT EXISTS idx_payment_order ON payment_callback(order_no);

CREATE TABLE IF NOT EXISTS group_customer_lead (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES sys_user(id) ON DELETE SET NULL,
    organization_name VARCHAR(200),
    contact_name VARCHAR(100),
    contact_phone VARCHAR(32),
    status VARCHAR(40),
    remark VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS partner_lead (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES sys_user(id) ON DELETE SET NULL,
    partner_name VARCHAR(200),
    partner_type VARCHAR(80),
    contact_name VARCHAR(100),
    contact_phone VARCHAR(32),
    status VARCHAR(40),
    remark VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);
