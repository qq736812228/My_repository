import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import AdminLayout from '@/layout/AdminLayout.vue'
import Login from '@/views/Login.vue'
import Dashboard from '@/views/Dashboard.vue'
import GenericCrud from '@/views/GenericCrud.vue'

const crudRoutes = [
  ['用户管理', 'users', '/system/users'],
  ['角色管理', 'roles', '/system/roles'],
  ['菜单管理', 'menus', '/system/menus'],
  ['字典管理', 'dict-items', '/system/dict-items'],
  ['行政区划', 'regions', '/system/regions'],
  ['操作日志', 'operation-logs', '/system/logs'],
  ['健康档案', 'health-archives', '/health/archives'],
  ['自测记录', 'self-tests', '/health/self-tests'],
  ['行为记录', 'behaviors', '/health/behaviors'],
  ['检测订单', 'detection-orders', '/health/detection-orders'],
  ['检测报告', 'detection-reports', '/health/detection-reports'],
  ['菌种基础库', 'microbe-species', '/health/microbe-species'],
  ['商品管理', 'products', '/mall/products'],
  ['价格快照', 'price-snapshots', '/mall/price-snapshots'],
  ['商品评价', 'product-reviews', '/mall/reviews'],
  ['订单管理', 'orders', '/mall/orders'],
  ['订单明细', 'order-items', '/mall/order-items'],
  ['商户管理', 'merchants', '/mall/merchants'],
  ['商户申请', 'merchant-applications', '/mall/merchant-applications'],
  ['积分账户', 'point-accounts', '/growth/point-accounts'],
  ['积分流水', 'point-transactions', '/growth/point-transactions'],
  ['任务中心', 'tasks', '/growth/tasks'],
  ['邀请记录', 'invitations', '/growth/invitations'],
  ['消息管理', 'messages', '/content/messages'],
  ['支付回调', 'payment-callbacks', '/content/payment-callbacks'],
  ['AI 报告', 'ai-reports', '/reports/ai-reports'],
  ['团体客户线索', 'group-leads', '/leads/group'],
  ['合作伙伴线索', 'partner-leads', '/leads/partner']
]

const routes: RouteRecordRaw[] = [
  { path: '/login', component: Login },
  {
    path: '/',
    component: AdminLayout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: Dashboard, meta: { title: '控制台' } },
      ...crudRoutes.map(([title, endpoint, path]) => ({
        path,
        component: GenericCrud,
        meta: { title, endpoint }
      }))
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })
router.beforeEach((to) => {
  if (to.path !== '/login' && !localStorage.getItem('OBAI_ADMIN_TOKEN')) {
    return '/login'
  }
  return true
})

export default router
