import { request } from '@/utils/request'

export const api = {
  home: () => request({ url: '/api/app/home' }),
  official: () => request({ url: '/api/app/home/official' }),
  announcements: () => request({ url: '/api/app/home/announcements' }),
  knowledge: () => request({ url: '/api/app/home/knowledge' }),
  login: (data) => request({ url: '/api/auth/login', method: 'POST', data }),
  register: (data) => request({ url: '/api/auth/register', method: 'POST', data }),
  wechatLogin: (data) => request({ url: '/api/auth/wechat-login', method: 'POST', data }),
  archive: () => request({ url: '/api/app/health/archive' }),
  saveArchive: (data) => request({ url: '/api/app/health/archive', method: 'PUT', data }),
  selfTests: () => request({ url: '/api/app/health/self-tests' }),
  submitSelfTest: (data) => request({ url: '/api/app/health/self-tests', method: 'POST', data }),
  behaviors: () => request({ url: '/api/app/health/behaviors' }),
  submitBehavior: (data) => request({ url: '/api/app/health/behaviors', method: 'POST', data }),
  createDetection: (data) => request({ url: '/api/app/detection/orders', method: 'POST', data }),
  detectionOrders: () => request({ url: '/api/app/detection/orders' }),
  detectionReports: () => request({ url: '/api/app/detection/reports' }),
  products: () => request({ url: '/api/app/mall/products' }),
  priceCompare: (id) => request({ url: `/api/app/mall/products/${id}/price-compare` }),
  createOrder: (data) => request({ url: '/api/app/mall/orders', method: 'POST', data }),
  points: () => request({ url: '/api/app/points' }),
  groupLead: (data) => request({ url: '/api/app/leads/group-customer', method: 'POST', data }),
  partnerLead: (data) => request({ url: '/api/app/leads/partner', method: 'POST', data }),
  merchantApply: (data) => request({ url: '/api/app/leads/merchant-application', method: 'POST', data }),
  payParams: (data) => request({ url: '/api/app/wechat/pay/params', method: 'POST', data }),
  subscribeMessage: (data) => request({ url: '/api/app/wechat/subscribe-message', method: 'POST', data })
}
