import axios from 'axios';

// 修改为8081端口
export const API_BASE_URL = 'http://localhost:8081/api';

// 创建axios实例
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  },
  timeout: 30000 // 30秒超时
});

// 请求拦截器
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    console.error('请求拦截器错误:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
api.interceptors.response.use(
  (response) => {
    // 打印完整响应日志
    console.log('API响应数据:', {
      status: response.status,
      statusText: response.statusText,
      data: response.data,
      headers: response.headers
    });

    // 处理成功响应
    const responseData = response.data;
    
    // 如果响应中包含token，自动更新存储
    if (responseData.data && responseData.data.token) {
      localStorage.setItem('token', responseData.data.token);
    }
    
    return responseData;
  },
  (error) => {
    console.error('API响应错误:', error);

    // 处理错误响应
    if (error.response) {
      const status = error.response.status;
      const data = error.response.data;

      // 处理特定状态码
      switch (status) {
        case 401:
          // 未授权，清除token并重定向到登录页
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          if (window.location.pathname !== '/login') {
            window.location.href = '/login';
          }
          break;
        case 403:
          console.error('没有权限访问该资源');
          break;
        case 404:
          console.error('请求的资源不存在');
          break;
        case 500:
          console.error('服务器内部错误');
          break;
        default:
          console.error(`未处理的错误状态码: ${status}`);
      }

      return Promise.reject({
        code: status,
        message: data.message || '请求失败',
        data: data
      });
    } else if (error.request) {
      // 请求已发送但没有收到响应
      return Promise.reject({
        code: 'NETWORK_ERROR',
        message: '服务器无响应，请检查网络连接',
        error: error
      });
    } else {
      // 请求设置时出错
      return Promise.reject({
        code: 'REQUEST_ERROR',
        message: '请求配置错误',
        error: error
      });
    }
  }
);

export default api; 