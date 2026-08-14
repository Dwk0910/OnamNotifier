import axios, { type InternalAxiosRequestConfig } from 'axios';

// 백엔드 API 통신용 Axios 인스턴스 생성
export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  withCredentials: true,
});

api.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const csrfToken = localStorage.getItem('ONN_CSRF');
  if (csrfToken && config.headers) {
    config.headers['X-Csrf-Token'] = csrfToken;
  }
  return config;
});