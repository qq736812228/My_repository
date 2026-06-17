<template><view><view class="card"><view class="section-title">官方入口</view><view class="muted">平台管理、政策公告、官方文档和服务入口。</view><button class="green-btn" @tap="load">刷新官方信息</button><pre>{{ info }}</pre></view></view></template>
<script setup>import { ref,onMounted } from 'vue'; import { api } from '@/api/index'; const info=ref({}); async function load(){ info.value=await api.official() } onMounted(load)</script>
<style scoped>pre{white-space:pre-wrap;font-size:22rpx}</style>
