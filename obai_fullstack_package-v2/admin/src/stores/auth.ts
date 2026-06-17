import { defineStore } from 'pinia'
import { login } from '@/api/modules'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('OBAI_ADMIN_TOKEN') || '',
    user: JSON.parse(localStorage.getItem('OBAI_ADMIN_USER') || 'null')
  }),
  actions: {
    async login(username: string, password: string) {
      const data = await login({ username, password })
      this.token = data.token
      this.user = data
      localStorage.setItem('OBAI_ADMIN_TOKEN', data.token)
      localStorage.setItem('OBAI_ADMIN_USER', JSON.stringify(data))
    },
    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('OBAI_ADMIN_TOKEN')
      localStorage.removeItem('OBAI_ADMIN_USER')
    }
  }
})
