<template><view><view class="card"><view class="section-title">邀请共建</view><view>邀请好友加入，共享健康未来。</view><button class="green-btn" open-type="share">立即邀请</button></view></view></template>
<script setup>import { onShareAppMessage } from '@dcloudio/uni-app'; onShareAppMessage(()=>({title:'加入耦白 OBAI，共建长期健康数据', path:'/pages/home/index'}))</script>
