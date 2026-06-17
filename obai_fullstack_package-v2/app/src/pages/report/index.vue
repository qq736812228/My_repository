<template><view><view class="card"><view class="section-title">档案报告</view><view v-for="r in reports" :key="r.id" class="record"><strong>{{ r.reportNo }}</strong><p>{{ r.summary }}</p></view></view></view></template>
<script setup>import { ref,onMounted } from 'vue'; import { api } from '@/api/index'; const reports=ref([]); onMounted(async()=>{ reports.value=await api.detectionReports() })</script>
<style scoped>.record{background:#f8fcf9;border-radius:18rpx;padding:20rpx;margin-top:16rpx}</style>
