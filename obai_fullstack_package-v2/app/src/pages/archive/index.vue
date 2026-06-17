<template><view><view class="card"><view class="section-title">健康档案</view><view class="muted">长期记录、趋势追踪、档案报告。</view><button class="green-btn" @tap="load">查看档案</button><pre>{{ archive }}</pre></view></view></template>
<script setup>import { ref,onMounted } from 'vue'; import { api } from '@/api/index'; const archive=ref({}); async function load(){ archive.value=await api.archive() } onMounted(load)</script>
<style scoped>pre{white-space:pre-wrap;font-size:22rpx;color:#31533b}</style>
