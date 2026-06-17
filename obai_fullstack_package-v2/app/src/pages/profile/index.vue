<template><view><view class="card"><view class="section-title">我的</view><button class="green-btn" @tap="goLogin">注册 / 登录</button><button class="plain" @tap="goArchive">我的档案</button><button class="plain" @tap="goPoints">我的积分</button></view></view></template>
<script setup>function goLogin(){uni.navigateTo({url:'/pages/login/index'})} function goArchive(){uni.switchTab({url:'/pages/archive/index'})} function goPoints(){uni.navigateTo({url:'/pages/points/index'})}</script>
<style scoped>.plain{margin-top:20rpx;background:#fff;border:1rpx solid #dceee3;color:#073b23;border-radius:999rpx}</style>
