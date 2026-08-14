import axios, { type InternalAxiosRequestConfig } from 'axios';

const getCookie = (name: string): string | null => {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop()?.split(';').shift() || null;
  return null;
};

// 백엔드 API 통신용 Axios 인스턴스 생성
export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  withCredentials: true,
});

api.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const csrfToken = getCookie('ONN_CSRF');
  if (csrfToken && config.headers) {
    config.headers['X-Csrf-Token'] = csrfToken;
  }
  return config;
});