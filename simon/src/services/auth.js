import api from './api';
import { jwtDecode } from 'jwt-decode';

// 登录
export const login = async (credentials) => {
  try {
    console.log('开始登录请求:', credentials);
    let loginParams = { password: credentials.password };
    
    // 根据输入判断是邮箱还是手机号
    if (credentials.username) {
      const isEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(credentials.username);
      if (isEmail) {
        loginParams.email = credentials.username;
      } else {
        loginParams.phone = credentials.username;
      }
    }
    
    console.log('发送登录请求参数:', loginParams);
    const response = await api.post('/auth/login', loginParams);
    console.log('登录响应数据:', response);
    
    if (response.code === 200 && response.success) {
      // 存储token和用户信息
      if (response.data) {
        localStorage.setItem('token', response.data.token || '');
        localStorage.setItem('user', JSON.stringify(response.data));
      }
      
      // 修改跳转路径为 /home
      window.location.replace('/home');
      return response.data;
    }
    return null;
  } catch (error) {
    console.error('登录错误:', error);
    throw error;
  }
};

// 注册
export const register = async (userData) => {
  try {
    const response = await api.post('/auth/register', userData);
    return response;
  } catch (error) {
    console.error('注册错误:', error);
    throw error;
  }
};

// 退出登录
export const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  window.location.href = '/login';
};

// 获取当前用户
export const getCurrentUser = () => {
  const userStr = localStorage.getItem('user');
  if (userStr) {
    try {
      return JSON.parse(userStr);
    } catch (error) {
      console.error('解析用户信息失败:', error);
      return null;
    }
  }
  return null;
};

// 检查是否已认证
export const isAuthenticated = () => {
  const token = localStorage.getItem('token');
  if (!token) return false;
  
  try {
    const decoded = jwtDecode(token);
    const currentTime = Date.now() / 1000;
    return decoded.exp > currentTime;
  } catch (error) {
    console.error('Token验证失败:', error);
    return false;
  }
};

// 获取token
export const getToken = () => {
  return localStorage.getItem('token');
};

// 检查用户是否是管理员
export const isAdmin = () => {
  const user = getCurrentUser();
  return user && user.role === 'ADMIN';
}; 