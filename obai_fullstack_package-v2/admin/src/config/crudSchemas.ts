export type CrudFieldType = 'text' | 'textarea' | 'number' | 'date' | 'datetime' | 'switch'

export interface CrudField {
  key: string
  label: string
  type?: CrudFieldType
  readonly?: boolean
}

export interface CrudSchema {
  fields: CrudField[]
}

const auditFields: CrudField[] = [
  { key: 'createdAt', label: '创建时间', type: 'datetime', readonly: true },
  { key: 'updatedAt', label: '更新时间', type: 'datetime', readonly: true }
]

export const crudSchemas: Record<string, CrudSchema> = {
  users: {
    fields: [
      { key: 'username', label: '用户名' },
      { key: 'nickname', label: '昵称' },
      { key: 'phone', label: '手机号' },
      { key: 'openid', label: '微信 OpenID', readonly: true },
      { key: 'status', label: '状态' },
      ...auditFields
    ]
  },
  roles: {
    fields: [
      { key: 'code', label: '角色编码' },
      { key: 'name', label: '角色名称' },
      { key: 'description', label: '描述', type: 'textarea' },
      { key: 'enabled', label: '启用', type: 'switch' },
      ...auditFields
    ]
  },
  menus: {
    fields: [
      { key: 'parentId', label: '父级 ID', type: 'number' },
      { key: 'title', label: '菜单标题' },
      { key: 'path', label: '路由路径' },
      { key: 'component', label: '组件路径' },
      { key: 'icon', label: '图标' },
      { key: 'sortNo', label: '排序', type: 'number' },
      { key: 'permissionCode', label: '权限标识' },
      { key: 'visible', label: '可见', type: 'switch' },
      ...auditFields
    ]
  },

  'dict-items': {
    fields: [
      { key: 'dictCode', label: '字典编码' },
      { key: 'itemKey', label: '条目键' },
      { key: 'itemValue', label: '条目值' },
      { key: 'sortNo', label: '排序', type: 'number' },
      { key: 'enabled', label: '启用', type: 'switch' },
      ...auditFields
    ]
  },
  regions: {
    fields: [
      { key: 'code', label: '行政区编码' },
      { key: 'name', label: '名称' },
      { key: 'parentCode', label: '父级编码' },
      { key: 'level', label: '级别' },
      { key: 'sortNo', label: '排序', type: 'number' },
      ...auditFields
    ]
  },
  'operation-logs': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number', readonly: true },
      { key: 'username', label: '用户', readonly: true },
      { key: 'module', label: '模块', readonly: true },
      { key: 'action', label: '动作', readonly: true },
      { key: 'method', label: '方法', readonly: true },
      { key: 'path', label: '路径', readonly: true },
      { key: 'clientIp', label: 'IP', readonly: true },
      { key: 'statusCode', label: '状态码', type: 'number', readonly: true },
      { key: 'costMs', label: '耗时(ms)', type: 'number', readonly: true },
      { key: 'requestId', label: 'TraceId', readonly: true },
      { key: 'operatedAt', label: '操作时间', type: 'datetime', readonly: true }
    ]
  },
  'health-archives': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'gender', label: '性别' },
      { key: 'birthday', label: '生日', type: 'date' },
      { key: 'heightCm', label: '身高(cm)' },
      { key: 'weightKg', label: '体重(kg)' },
      { key: 'dietPreference', label: '饮食偏好', type: 'textarea' },
      { key: 'sleepPattern', label: '睡眠模式', type: 'textarea' },
      { key: 'bowelHabit', label: '排便习惯', type: 'textarea' },
      { key: 'chronicHistory', label: '慢病史', type: 'textarea' },
      { key: 'allergyHistory', label: '过敏史', type: 'textarea' },
      { key: 'redFlagNote', label: '风险备注', type: 'textarea' },
      { key: 'completenessScore', label: '完整度', type: 'number' },
      ...auditFields
    ]
  },
  'self-tests': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'version', label: '版本' },
      { key: 'symptomTags', label: '症状标签', type: 'textarea' },
      { key: 'behaviorTags', label: '行为标签', type: 'textarea' },
      { key: 'stoolStatus', label: '排便状态' },
      { key: 'sleepStatus', label: '睡眠状态' },
      { key: 'pressureLevel', label: '压力等级' },
      { key: 'answerJson', label: '答案 JSON', type: 'textarea' },
      { key: 'score', label: '评分', type: 'number' },
      { key: 'riskLevel', label: '风险等级' },
      { key: 'adviceSummary', label: '建议摘要', type: 'textarea' },
      ...auditFields
    ]
  },
  behaviors: {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'recordDate', label: '记录日期', type: 'date' },
      { key: 'dietTags', label: '饮食标签', type: 'textarea' },
      { key: 'exerciseTags', label: '运动标签', type: 'textarea' },
      { key: 'sleepHours', label: '睡眠小时' },
      { key: 'stoolFrequency', label: '排便次数' },
      { key: 'pressureLevel', label: '压力等级' },
      { key: 'note', label: '备注', type: 'textarea' },
      ...auditFields
    ]
  },
  'detection-orders': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'orderNo', label: '订单号' },
      { key: 'sampleNo', label: '样本号' },
      { key: 'testType', label: '检测类型' },
      { key: 'status', label: '状态' },
      { key: 'institutionName', label: '检测机构' },
      { key: 'sampledAt', label: '采样时间', type: 'datetime' },
      { key: 'reportedAt', label: '报告时间', type: 'datetime' },
      ...auditFields
    ]
  },
  'detection-reports': {
    fields: [
      { key: 'orderId', label: '订单 ID', type: 'number' },
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'reportNo', label: '报告号' },
      { key: 'healthScore', label: '健康评分', type: 'number' },
      { key: 'dominantSpecies', label: '优势菌群', type: 'textarea' },
      { key: 'diversityLevel', label: '多样性等级' },
      { key: 'summary', label: '摘要', type: 'textarea' },
      { key: 'rawJson', label: '原始 JSON', type: 'textarea' },
      ...auditFields
    ]
  },
  'microbe-species': {
    fields: [
      { key: 'latinName', label: '拉丁名' },
      { key: 'cnName', label: '中文名' },
      { key: 'functionTag', label: '功能标签' },
      { key: 'evidenceLevel', label: '证据等级' },
      { key: 'description', label: '说明', type: 'textarea' },
      ...auditFields
    ]
  },
  products: {
    fields: [
      { key: 'merchantId', label: '商户 ID', type: 'number' },
      { key: 'name', label: '商品名称' },
      { key: 'category', label: '分类' },
      { key: 'brand', label: '品牌' },
      { key: 'probioticStrains', label: '菌株/成分', type: 'textarea' },
      { key: 'price', label: '平台价', type: 'number' },
      { key: 'marketMinPrice', label: '市场最低价', type: 'number' },
      { key: 'marketMaxPrice', label: '市场最高价', type: 'number' },
      { key: 'pointDeductionLimit', label: '积分抵扣上限', type: 'number' },
      { key: 'status', label: '状态' },
      { key: 'qualityStatus', label: '质量状态' },
      { key: 'imageUrl', label: '图片 URL' },
      { key: 'description', label: '描述', type: 'textarea' },
      ...auditFields
    ]
  },
  'price-snapshots': {
    fields: [
      { key: 'productId', label: '商品 ID', type: 'number' },
      { key: 'channelName', label: '渠道' },
      { key: 'price', label: '价格', type: 'number' },
      { key: 'url', label: '链接' },
      { key: 'capturedAt', label: '采集时间', type: 'datetime' },
      ...auditFields
    ]
  },
  'product-reviews': {
    fields: [
      { key: 'productId', label: '商品 ID', type: 'number' },
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'rating', label: '评分', type: 'number' },
      { key: 'tags', label: '标签' },
      { key: 'content', label: '内容', type: 'textarea' },
      { key: 'verifiedPurchase', label: '已核验购买', type: 'switch' },
      ...auditFields
    ]
  },
  orders: {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'orderNo', label: '订单号' },
      { key: 'totalAmount', label: '总金额', type: 'number' },
      { key: 'pointsUsed', label: '使用积分', type: 'number' },
      { key: 'status', label: '状态' },
      { key: 'payChannel', label: '支付渠道' },
      { key: 'transactionId', label: '交易号' },
      ...auditFields
    ]
  },

  'order-items': {
    fields: [
      { key: 'orderId', label: '订单 ID', type: 'number' },
      { key: 'productId', label: '商品 ID', type: 'number' },
      { key: 'productName', label: '商品名称' },
      { key: 'quantity', label: '数量', type: 'number' },
      { key: 'unitPrice', label: '单价', type: 'number' },
      ...auditFields
    ]
  },
  merchants: {
    fields: [
      { key: 'name', label: '商户名称' },
      { key: 'contactName', label: '联系人' },
      { key: 'contactPhone', label: '联系电话' },
      { key: 'licenseNo', label: '营业执照号' },
      { key: 'status', label: '状态' },
      { key: 'creditScore', label: '信用分', type: 'number' },
      { key: 'auditRemark', label: '审核备注', type: 'textarea' },
      ...auditFields
    ]
  },
  'merchant-applications': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'merchantName', label: '商户名称' },
      { key: 'contactName', label: '联系人' },
      { key: 'contactPhone', label: '联系电话' },
      { key: 'licenseNo', label: '营业执照号' },
      { key: 'status', label: '状态' },
      { key: 'auditRemark', label: '审核备注', type: 'textarea' },
      ...auditFields
    ]
  },
  'point-accounts': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'balance', label: '余额', type: 'number' },
      { key: 'totalEarned', label: '累计获得', type: 'number' },
      { key: 'totalUsed', label: '累计使用', type: 'number' },
      ...auditFields
    ]
  },
  'point-transactions': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'amount', label: '积分变动', type: 'number' },
      { key: 'type', label: '类型' },
      { key: 'source', label: '来源' },
      { key: 'refNo', label: '关联编号' },
      { key: 'description', label: '说明', type: 'textarea' },
      ...auditFields
    ]
  },
  tasks: {
    fields: [
      { key: 'code', label: '任务编码' },
      { key: 'title', label: '任务标题' },
      { key: 'description', label: '说明', type: 'textarea' },
      { key: 'rewardPoints', label: '奖励积分', type: 'number' },
      { key: 'enabled', label: '启用', type: 'switch' },
      ...auditFields
    ]
  },
  invitations: {
    fields: [
      { key: 'inviterUserId', label: '邀请人 ID', type: 'number' },
      { key: 'inviteeUserId', label: '被邀请人 ID', type: 'number' },
      { key: 'inviteCode', label: '邀请码' },
      { key: 'status', label: '状态' },
      { key: 'rewardPoints', label: '奖励积分', type: 'number' },
      ...auditFields
    ]
  },
  messages: {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'title', label: '标题' },
      { key: 'content', label: '内容', type: 'textarea' },
      { key: 'type', label: '类型' },
      { key: 'readFlag', label: '已读', type: 'switch' },
      ...auditFields
    ]
  },

  'payment-callbacks': {
    fields: [
      { key: 'orderNo', label: '订单号' },
      { key: 'payChannel', label: '支付渠道' },
      { key: 'transactionId', label: '交易号' },
      { key: 'status', label: '状态' },
      { key: 'rawPayload', label: '原始报文', type: 'textarea' },
      ...auditFields
    ]
  },
  'ai-reports': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'reportNo', label: '报告号' },
      { key: 'reportType', label: '报告类型' },
      { key: 'stabilityScore', label: '稳态评分', type: 'number' },
      { key: 'riskLevel', label: '风险等级' },
      { key: 'conclusion', label: '结论', type: 'textarea' },
      { key: 'suggestions', label: '建议', type: 'textarea' },
      { key: 'status', label: '状态' },
      ...auditFields
    ]
  },
  'group-leads': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'organizationName', label: '企业/机构名称' },
      { key: 'contactName', label: '联系人' },
      { key: 'contactPhone', label: '联系电话' },
      { key: 'status', label: '状态' },
      { key: 'remark', label: '备注', type: 'textarea' },
      ...auditFields
    ]
  },
  'partner-leads': {
    fields: [
      { key: 'userId', label: '用户 ID', type: 'number' },
      { key: 'partnerName', label: '伙伴名称' },
      { key: 'partnerType', label: '伙伴类型' },
      { key: 'contactName', label: '联系人' },
      { key: 'contactPhone', label: '联系电话' },
      { key: 'status', label: '状态' },
      { key: 'remark', label: '备注', type: 'textarea' },
      ...auditFields
    ]
  }
}
