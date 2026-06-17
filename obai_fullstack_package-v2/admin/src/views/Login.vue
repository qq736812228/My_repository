<template>
  <div class="login-page">
    <el-card class="login-card">
      <h1>耦白 OBAI 管理端</h1>
      <p>肠道微生态稳态建设数据 AI 健康管理平台</p>
      <el-form :model="form" @keyup.enter="submit">
        <el-form-item><el-input v-model="form.username" placeholder="用户名" /></el-form-item>
        <el-form-item><el-input v-model="form.password" placeholder="密码" type="password" show-password /></el-form-item>
        <el-button type="success" :loading="loading" style="width:100%" @click="submit">登录</el-button>
      </el-form>
    </el-card>
  </div>
</template>
<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
const router = useRouter()
const auth = useAuthStore()
const loading = ref(false)
const form = reactive({ username: 'admin', password: 'admin123' })
async function submit() {
  loading.value = true
  try {
    await auth.login(form.username, form.password)
    router.push('/dashboard')
  } finally {
    loading.value = false
  }
}
</script>
<style scoped>
.login-page { height:100vh; display:flex; align-items:center; justify-content:center; background:linear-gradient(135deg,#e9fff0,#fff); }
.login-card { width:420px; border-radius:20px; }
h1 { margin:0; color:#073b23; }
p { color:#5c7d6a; margin-bottom:24px; }
</style>
