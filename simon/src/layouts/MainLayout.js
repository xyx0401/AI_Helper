import React, { useState } from 'react';
import { Layout, Menu, Breadcrumb, Avatar, Dropdown, Button } from 'antd';
import { Link, Outlet, useLocation, useNavigate } from 'react-router-dom';
import {
  UserOutlined,
  VideoCameraOutlined,
  LogoutOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  HomeOutlined,
} from '@ant-design/icons';
import { logout, getCurrentUser } from '../services/auth';

const { Header, Sider, Content } = Layout;

const MainLayout = () => {
  const [collapsed, setCollapsed] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();
  const currentUser = getCurrentUser();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const userMenu = (
    <Menu>
      <Menu.Item key="profile" icon={<UserOutlined />}>
        <Link to="/profile">个人设置</Link>
      </Menu.Item>
      <Menu.Divider />
      <Menu.Item key="logout" icon={<LogoutOutlined />} onClick={handleLogout}>
        退出登录
      </Menu.Item>
    </Menu>
  );

  // 根据当前路径确定选中的菜单项
  const getSelectedKey = () => {
    const path = location.pathname;
    if (path.includes('/conferences')) return ['2'];
    if (path.includes('/profile')) return ['3'];
    return ['1'];
  };

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Sider trigger={null} collapsible collapsed={collapsed} theme="light">
        <div className="logo" style={{ height: 64, display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <h2 style={{ color: '#001529', margin: 0, fontSize: collapsed ? 14 : 20 }}>
            {collapsed ? '会议' : '会议助手'}
          </h2>
        </div>
        <Menu mode="inline" selectedKeys={getSelectedKey()} style={{ borderRight: 0 }}>
          <Menu.Item key="1" icon={<HomeOutlined />}>
            <Link to="/dashboard">问答助手</Link>
          </Menu.Item>
          <Menu.Item key="2" icon={<VideoCameraOutlined />}>
            <Link to="/conferences">会议管理</Link>
          </Menu.Item>
          <Menu.Item key="3" icon={<UserOutlined />}>
            <Link to="/profile">个人设置</Link>
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout className="site-layout">
        <Header className="site-layout-background" style={{ padding: 0, background: '#fff', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <Button
            type="text"
            icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
            onClick={() => setCollapsed(!collapsed)}
            style={{ fontSize: '16px', width: 64, height: 64 }}
          />
          <div style={{ marginRight: 20 }}>
            <Dropdown overlay={userMenu} placement="bottomRight">
              <span style={{ cursor: 'pointer', display: 'flex', alignItems: 'center' }}>
                <Avatar icon={<UserOutlined />} style={{ marginRight: 8 }} />
                <span>{currentUser?.username || '用户'}</span>
              </span>
            </Dropdown>
          </div>
        </Header>
        <Content
          className="site-layout-content"
          style={{
            margin: '16px',
            padding: 24,
            minHeight: 280,
            overflow: 'auto',
          }}
        >
          {location.pathname !== '/dashboard' && (
            <Breadcrumb style={{ marginBottom: 16 }}>
              <Breadcrumb.Item>
                <Link to="/">首页</Link>
              </Breadcrumb.Item>
              {location.pathname.includes('/conferences') && (
                <Breadcrumb.Item>
                  <Link to="/conferences">会议管理</Link>
                </Breadcrumb.Item>
              )}
              {location.pathname.includes('/profile') && (
                <Breadcrumb.Item>个人设置</Breadcrumb.Item>
              )}
              {location.pathname.includes('/create') && (
                <Breadcrumb.Item>创建会议</Breadcrumb.Item>
              )}
              {location.pathname.includes('/edit') && (
                <Breadcrumb.Item>编辑会议</Breadcrumb.Item>
              )}
            </Breadcrumb>
          )}
          <Outlet />
        </Content>
      </Layout>
    </Layout>
  );
};

export default MainLayout; 