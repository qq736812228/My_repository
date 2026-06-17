import request from './request'

export interface LoginPayload {
  username: string
  password: string
}

// The response interceptor unwraps the envelope and resolves to `body.data`,
// so the second axios generic (the resolved value type) is `any`, not AxiosResponse.
export const login = (data: LoginPayload) => request.post<any, any>('/api/auth/login', data)
export const dashboardOverview = () => request.get<any, any>('/api/admin/dashboard/overview')

export const crud = {
  list: (endpoint: string) => request.get<any, any>(`/api/admin/${endpoint}`),
  detail: (endpoint: string, id: number) => request.get<any, any>(`/api/admin/${endpoint}/${id}`),
  create: (endpoint: string, data: Record<string, unknown>) => request.post<any, any>(`/api/admin/${endpoint}`, data),
  update: (endpoint: string, id: number, data: Record<string, unknown>) => request.put<any, any>(`/api/admin/${endpoint}/${id}`, data),
  remove: (endpoint: string, id: number) => request.delete<any, any>(`/api/admin/${endpoint}/${id}`)
}
