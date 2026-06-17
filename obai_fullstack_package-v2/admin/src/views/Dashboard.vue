<template>
  <div class="dashboard">
    <div class="stat-grid">
      <el-card v-for="item in stats" :key="item.key" class="stat-card">
        <div class="stat-label">{{ item.label }}</div>
        <div class="stat-value">{{ data[item.key] || 0 }}</div>
      </el-card>
    </div>
    <el-card class="chart-card">
      <template #header>近 7 日用户与订单趋势</template>
      <div ref="chartRef" class="chart" />
    </el-card>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import { dashboardOverview } from '@/api/modules'
const data = ref<Record<string, any>>({})
const chartRef = ref<HTMLDivElement>()
const stats = [
  { key: 'userCount', label: '用户数' },
  { key: 'productCount', label: '商品数' },
  { key: 'detectionOrderCount', label: '检测订单' },
  { key: 'selfTestCount', label: '自测记录' },
  { key: 'merchantCount', label: '商户数' }
]
onMounted(async () => {
  data.value = await dashboardOverview()
  const chart = echarts.init(chartRef.value!)
  const trend = data.value.trend || []
  chart.setOption({
    tooltip: {},
    legend: { data: ['用户', '订单'] },
    xAxis: { type: 'category', data: trend.map((x: any) => x.date) },
    yAxis: { type: 'value' },
    series: [
      { name: '用户', type: 'line', data: trend.map((x: any) => x.users) },
      { name: '订单', type: 'bar', data: trend.map((x: any) => x.orders) }
    ]
  })
})
</script>
<style scoped>
.stat-grid { display:grid; grid-template-columns: repeat(5,1fr); gap:16px; margin-bottom:16px; }
.stat-card { border-radius:14px; }
.stat-label { color:#5c7d6a; }
.stat-value { font-size:32px; font-weight:800; color:#073b23; margin-top:10px; }
.chart { height:360px; }
.chart-card { border-radius:14px; }
</style>
