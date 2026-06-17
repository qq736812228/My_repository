import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
  timeout: 20000
})

service.interceptors.request.use((config) => {
  const token = localStorage.getItem('OBAI_ADMIN_TOKEN')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  config.headers['X-Trace-Id'] = `admin-${Date.now()}-${Math.random().toString(16).slice(2)}`
  return config
})

service.interceptors.response.use(
  (response) => {
    const body = response.data
    if (body && typeof body.code !== 'undefined') {
      if (body.code !== 0) {
        if (body.code === 401 || body.code === 403) {
          localStorage.removeItem('OBAI_ADMIN_TOKEN')
          if (window.location.pathname !== '/login') window.location.href = '/login'
        }
        ElMessage.error(body.message || '接口异常')
        return Promise.reject(body)
      }
      return body.data
    }
    return body
  },
  (error) => {
    const status = error?.response?.status
    if (status === 401 || status === 403) {
      localStorage.removeItem('OBAI_ADMIN_TOKEN')
      if (window.location.pathname !== '/login') window.location.href = '/login'
    }
    ElMessage.error(error?.response?.data?.message || error.message || '网络异常')
    return Promise.reject(error)
  }
)

export default service
