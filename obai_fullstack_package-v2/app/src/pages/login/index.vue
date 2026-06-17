<template>
  <view>
    <view class="card">
      <view class="section-title">注册 / 登录</view>
      <input v-model="username" placeholder="用户名" />
      <input v-model="password" password placeholder="密码" />
      <input v-if="mode === 'register'" v-model="phone" placeholder="手机号，可选" />
      <button class="green-btn" @tap="submit">{{ mode === 'login' ? '账号登录' : '注册并登录' }}</button>
      <button class="plain" @tap="toggleMode">{{ mode === 'login' ? '没有账号？去注册' : '已有账号？去登录' }}</button>
      <button class="wx" @tap="wechat">微信一键登录</button>
    </view>
  </view>
</template>
<script setup>
import { ref } from 'vue'
import { api } from '@/api/index'

const mode = ref('login')
const username = ref('')
const password = ref('')
const phone = ref('')

function toggleMode() {
  mode.value = mode.value === 'login' ? 'register' : 'login'
}

async function submit() {
  if (!username.value || !password.value) {
    uni.showToast({ title: '请输入用户名和密码', icon: 'none' })
    return
  }
  const data = mode.value === 'login'
    ? await api.login({ username: username.value, password: password.value })
    : await api.register({ username: username.value, password: password.value, nickname: username.value, phone: phone.value })
  uni.setStorageSync('OBAI_APP_TOKEN', data.token)
  uni.showToast({ title: '已登录' })
  uni.switchTab({ url: '/pages/home/index' })
}

function wechat() {
  uni.login({
    provider: 'weixin',
    success: async (res) => {
      const data = await api.wechatLogin({ code: res.code, nickname: '微信用户' })
      uni.setStorageSync('OBAI_APP_TOKEN', data.token)
      uni.switchTab({ url: '/pages/home/index' })
    },
    fail: () => uni.showToast({ title: '微信登录失败', icon: 'none' })
  })
}
</script>
<style scoped>
input { background:#f8fcf9; border-radius:16rpx; padding:20rpx; margin:16rpx 0; }
.wx { margin-top:20rpx; background:#07c160; color:#fff; border-radius:999rpx; }
.plain { margin-top:20rpx; background:#fff; border:1rpx solid #dceee3; color:#073b23; border-radius:999rpx; }
</style>
