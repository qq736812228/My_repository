<template><view><view class="card"><view class="section-title">科普中心</view><view v-for="k in list" :key="k.title" class="notice">{{ k.title }}</view></view></view></template>
<script setup>import { ref,onMounted } from 'vue'; import { api } from '@/api/index'; const list=ref([]); onMounted(async()=>{ list.value=await api.knowledge() })</script>
<style scoped>.notice{background:#f8fcf9;border-radius:16rpx;padding:20rpx;margin-bottom:16rpx}</style>
