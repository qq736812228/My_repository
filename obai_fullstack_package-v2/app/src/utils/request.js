const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

function redirectLogin() {
  uni.removeStorageSync('OBAI_APP_TOKEN')
  uni.showToast({ title: '请先登录', icon: 'none' })
  setTimeout(() => {
    uni.navigateTo({ url: '/pages/login/index' })
  }, 500)
}

export function request(options) {
  const token = uni.getStorageSync('OBAI_APP_TOKEN')
  return new Promise((resolve, reject) => {
    uni.request({
      url: `${BASE_URL}${options.url}`,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'content-type': 'application/json',
        'X-Trace-Id': `mini-${Date.now()}-${Math.random().toString(16).slice(2)}`,
        ...(token ? { Authorization: `Bearer ${token}` } : {})
      },
      success(res) {
        const body = res.data || {}
        if (res.statusCode === 401 || body.code === 401) {
          redirectLogin()
          reject(body)
          return
        }
        if (res.statusCode === 403 || body.code === 403) {
          uni.showToast({ title: body.message || '无访问权限', icon: 'none' })
          reject(body)
          return
        }
        if (body.code === 0) {
          resolve(body.data)
        } else {
          uni.showToast({ title: body.message || '请求失败', icon: 'none' })
          reject(body)
        }
      },
      fail(err) {
        uni.showToast({ title: '网络异常', icon: 'none' })
        reject(err)
      }
    })
  })
}
