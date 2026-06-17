<template>
  <scroll-view scroll-y class="page">
    <view class="hero">
      <view>
        <view class="brand">耦白 OBAI</view>
        <view class="sub">Omni Biome Artificial Intelligence</view>
        <view class="headline">肠道微生态稳态建设数据<br />AI 健康管理平台</view>
        <view class="slogan">坚持长期主义，奖励健康，奖励贡献。</view>
        <view class="value-row">
          <view v-for="v in home.values" :key="v" class="pill">✓ {{ v }}</view>
        </view>
      </view>
      <view class="orb">♻</view>
    </view>

    <view class="card">
      <view class="section-title">快速入口</view>
      <view class="grid four">
        <view class="entry" @tap="goGroup"><view class="icon">👥</view><text>团体客户入口</text><small>企业/机构</small></view>
        <view class="entry" @tap="goPartner"><view class="icon">🤝</view><text>合作伙伴入口</text><small>共建生态</small></view>
        <view class="entry" @tap="goLogin"><view class="icon">👤</view><text>注册 / 登录</text><small>开启健康之旅</small></view>
        <view class="entry" @tap="goOfficial"><view class="icon">🛡</view><text>官方入口</text><small>平台管理</small></view>
      </view>
    </view>

    <view class="card">
      <view class="section-title">🌿 身体可知 <text class="muted">科学了解自身微生态状态</text></view>
      <view class="grid three">
        <view class="feature" @tap="startDetection"><text>专业检测</text><small>权威检测 深度分析</small><button>去检测</button></view>
        <view class="feature" @tap="goArchive"><text>档案报告</text><small>长期记录 趋势追踪</small><button>查看档案</button></view>
        <view class="feature" @tap="goSelfTest"><text>微态自测</text><small>每日记录 关注变化</small><button>开始记录</button></view>
      </view>
    </view>

    <view class="split">
      <view class="card points" @tap="goPoints">
        <view class="section-title">OB 积分</view>
        <view class="point-box"><text>当前积分</text><strong>{{ home.points || 2680 }}</strong></view>
        <view class="point-box"><text>今日变动</text><strong>+{{ home.pointDeltaToday || 36 }}</strong></view>
        <view class="mini-row"><text>积分明细</text><text>贡献记录</text><text>权益中心</text><text>任务中心</text></view>
      </view>
      <view class="card invite" @tap="goInvite">
        <view class="section-title">邀请共建</view>
        <view>邀请好友加入<br />共享健康未来</view>
        <button class="orange">立即邀请</button>
      </view>
    </view>

    <view class="card mall" @tap="goMall">
      <view class="section-title">菌淘商城 <text class="muted right">进入商城 ></text></view>
      <view class="tags"><text>✓ 商品可信</text><text>✓ 价格可比</text><text>✓ 安心选购</text></view>
      <view class="mall-row"><view v-for="p in home.products" :key="p.id" class="product">{{ p.name }}</view></view>
    </view>

    <view class="banner" @tap="goMerchant">🛡 商家发布 · 平台审核 <text>立即查看 ></text></view>
    <view class="notice" @tap="goMessage">📢 平台公告 <text>OBAI 平台隐私政策更新说明</text></view>
    <view class="notice" @tap="goKnowledge">💡 科普中心 <text>肠道菌群小知识：益生菌与益生元的区别</text></view>
  </scroll-view>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { api } from '@/api/index'
const home = ref({ values: [], products: [] })
onMounted(load)
async function load() { home.value = await api.home() }
function nav(url) { uni.navigateTo({ url }) }
function goGroup() { nav('/pages/group/index') }
function goPartner() { nav('/pages/partner/index') }
function goLogin() { nav('/pages/login/index') }
async function goOfficial() { await api.official(); nav('/pages/official/index') }
async function startDetection() { await api.createDetection({ testType: '16S' }); nav('/pages/detection/index') }
async function goArchive() { await api.archive(); nav('/pages/archive/index') }
function goSelfTest() { nav('/pages/selftest/index') }
async function goPoints() { await api.points(); nav('/pages/points/index') }
function goInvite() { nav('/pages/invite/index') }
function goMall() { uni.switchTab({ url: '/pages/mall/index' }) }
function goMerchant() { nav('/pages/merchant/index') }
function goMessage() { uni.switchTab({ url: '/pages/message/index' }) }
function goKnowledge() { nav('/pages/knowledge/index') }
</script>
<style scoped>
.page { min-height:100vh; padding-bottom:40rpx; }
.hero { margin:0 20rpx 20rpx; padding:48rpx 28rpx 28rpx; background:linear-gradient(135deg,#ffffff,#edfff3); border-radius:0 0 32rpx 32rpx; display:flex; justify-content:space-between; }
.brand { font-size:48rpx; font-weight:900; color:#079844; }
.sub { font-size:22rpx; color:#333; margin-top:6rpx; }
.headline { font-size:44rpx; font-weight:800; color:#073b23; margin-top:38rpx; line-height:1.3; }
.slogan { margin-top:18rpx; color:#3f5d48; }
.value-row { display:flex; gap:16rpx; margin-top:28rpx; }
.pill { background:#fff; border-radius:999rpx; padding:12rpx 18rpx; color:#086c33; font-weight:700; box-shadow:0 8rpx 20rpx rgba(0,0,0,.06); }
.orb { width:180rpx; height:180rpx; border-radius:50%; background:radial-gradient(circle,#44df86,#04913e); color:white; display:flex; align-items:center; justify-content:center; font-size:82rpx; align-self:center; }
.grid { display:grid; gap:20rpx; }
.four { grid-template-columns: repeat(4, 1fr); }
.three { grid-template-columns: repeat(3, 1fr); }
.entry,.feature { background:#f8fcf9; border:1rpx solid #e4f1e7; border-radius:22rpx; padding:18rpx; min-height:140rpx; display:flex; flex-direction:column; align-items:center; justify-content:center; text-align:center; }
.entry text,.feature text { font-weight:700; font-size:26rpx; }
.entry small,.feature small { color:#6b7f71; font-size:20rpx; margin-top:10rpx; }
.icon { font-size:48rpx; margin-bottom:8rpx; }
.feature button { margin-top:20rpx; background:#08a64b; color:white; border-radius:999rpx; font-size:22rpx; line-height:1; padding:14rpx 24rpx; }
.split { display:grid; grid-template-columns: 1fr 1fr; gap:20rpx; margin:0 20rpx; }
.split .card { margin:0; }
.point-box { display:inline-flex; flex-direction:column; background:#f8fcf9; border-radius:18rpx; padding:18rpx 28rpx; margin-right:14rpx; }
.point-box strong { font-size:36rpx; color:#087c39; }
.mini-row { display:flex; justify-content:space-between; margin-top:28rpx; color:#31533b; font-size:22rpx; }
.invite { background:#fff7e9; }
.orange { background:#ff9a18; color:white; border-radius:999rpx; margin-top:22rpx; }
.tags { display:flex; gap:28rpx; color:#09843c; font-weight:700; }
.right { float:right; }
.mall-row { display:flex; gap:16rpx; margin-top:20rpx; }
.product { background:#edfff3; border-radius:16rpx; padding:16rpx; font-size:22rpx; }
.banner { margin:20rpx; padding:28rpx; border-radius:24rpx; background:linear-gradient(135deg,#0aa64f,#00672f); color:#fff; font-size:32rpx; font-weight:800; display:flex; justify-content:space-between; }
.notice { margin:16rpx 20rpx; padding:22rpx; background:#fff; border-radius:18rpx; display:flex; justify-content:space-between; color:#173b24; }
.notice text { color:#4f6d58; }
</style>
