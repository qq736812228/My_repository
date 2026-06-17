<template>
  <el-container class="layout">
    <el-aside width="240px" class="aside">
      <div class="brand">耦白 OBAI</div>
      <el-menu router :default-active="route.path" background-color="#073b23" text-color="#d7f5e3" active-text-color="#ffffff">
        <el-menu-item index="/dashboard">控制台</el-menu-item>
        <el-sub-menu index="system"><template #title>系统管理</template>
          <el-menu-item index="/system/users">用户管理</el-menu-item>
          <el-menu-item index="/system/roles">角色管理</el-menu-item>
          <el-menu-item index="/system/menus">菜单管理</el-menu-item>
          <el-menu-item index="/system/dict-items">字典管理</el-menu-item>
          <el-menu-item index="/system/regions">行政区划</el-menu-item>
          <el-menu-item index="/system/logs">操作日志</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="health"><template #title>健康业务</template>
          <el-menu-item index="/health/archives">健康档案</el-menu-item>
          <el-menu-item index="/health/self-tests">自测记录</el-menu-item>
          <el-menu-item index="/health/behaviors">行为记录</el-menu-item>
          <el-menu-item index="/health/detection-orders">检测订单</el-menu-item>
          <el-menu-item index="/health/detection-reports">检测报告</el-menu-item>
          <el-menu-item index="/health/microbe-species">菌种基础库</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="mall"><template #title>菌淘商城</template>
          <el-menu-item index="/mall/products">商品管理</el-menu-item>
          <el-menu-item index="/mall/price-snapshots">价格快照</el-menu-item>
          <el-menu-item index="/mall/reviews">商品评价</el-menu-item>
          <el-menu-item index="/mall/orders">订单管理</el-menu-item>
          <el-menu-item index="/mall/order-items">订单明细</el-menu-item>
          <el-menu-item index="/mall/merchants">商户管理</el-menu-item>
          <el-menu-item index="/mall/merchant-applications">商户申请</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="growth"><template #title>积分共建</template>
          <el-menu-item index="/growth/point-accounts">积分账户</el-menu-item>
          <el-menu-item index="/growth/point-transactions">积分流水</el-menu-item>
          <el-menu-item index="/growth/tasks">任务中心</el-menu-item>
          <el-menu-item index="/growth/invitations">邀请记录</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="content"><template #title>内容与报告</template>
          <el-menu-item index="/content/messages">消息管理</el-menu-item>
          <el-menu-item index="/content/payment-callbacks">支付回调</el-menu-item>
          <el-menu-item index="/reports/ai-reports">AI 报告</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="leads"><template #title>线索管理</template>
          <el-menu-item index="/leads/group">团体客户</el-menu-item>
          <el-menu-item index="/leads/partner">合作伙伴</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div>{{ route.meta.title || '管理端' }}</div>
        <el-button @click="logout">退出</el-button>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>
<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
const route = useRoute()
const router = useRouter()
const auth = useAuthStore()
function logout() {
  auth.logout()
  router.push('/login')
}
</script>
<style scoped>
.layout { height: 100vh; }
.aside { background: #073b23; color: #fff; }
.brand { height: 58px; display:flex; align-items:center; padding-left: 22px; font-weight: 800; font-size: 20px; }
.header { display:flex; align-items:center; justify-content:space-between; background:#fff; border-bottom:1px solid #e6eee9; }
</style>
