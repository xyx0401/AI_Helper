import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { login } from '../../services/auth';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values) => {
    setLoading(true);
    try {
      const response = await login({
        username: values.username,
        password: values.password
      });

      if (response.code === 200 && response.success) {
        message.success('登录成功！');
        setTimeout(() => {
          window.location.replace('/home');
        }, 1000);
      } else {
        message.error(response.message || '登录失败，请检查用户名和密码');
      }
    } catch (error) {
      console.error('登录错误:', error);
      message.error('登录失败: ' + (error.response?.data?.message || '请检查用户名和密码'));
    } finally {
      setLoading(false);
    }
  };

  const handleRegisterClick = () => {
    navigate('/register');
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <div className="login-header">
          <h1>欢迎登录</h1>
          <p>AI智能助手，为您服务</p>
        </div>
        <Form
          name="login"
          onFinish={onFinish}
          layout="vertical"
          size="large"
        >
          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名/邮箱/手机号' }]}
          >
            <Input 
              prefix={<UserOutlined className="site-form-item-icon" />} 
              placeholder="邮箱/手机号" 
              className="login-input"
            />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码' }]}
          >
            <Input.Password 
              prefix={<LockOutlined className="site-form-item-icon" />} 
              placeholder="密码" 
              className="login-input"
            />
          </Form.Item>

          <Form.Item>
            <Button 
              type="primary" 
              htmlType="submit" 
              loading={loading} 
              block
              className="login-button"
            >
              登录
            </Button>
          </Form.Item>

          <div className="register-link">
            <Button 
              type="link" 
              onClick={handleRegisterClick}
              className="register-button"
            >
              快速注册
            </Button>
          </div>
        </Form>
      </div>
    </div>
  );
};

export default Login; 