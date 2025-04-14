import React from 'react';
import { Layout, Menu, Typography, Avatar, Dropdown } from 'antd';
import { SafetyOutlined, TeamOutlined, ScheduleOutlined, EnvironmentOutlined, UserOutlined, SettingOutlined, LogoutOutlined } from '@ant-design/icons';
import './SchedulePage.css';
import { useNavigate } from 'react-router-dom';
import { getCurrentUser, logout } from '../../services/auth';

const { Header, Content } = Layout;
const { Title } = Typography;

const SchedulePage = () => {
  const navigate = useNavigate();
  const user = getCurrentUser();

  const handleNavClick = ({ key }) => {
    switch(key) {
      case 'home':
        navigate('/home');
        break;
      case 'speakers':
        window.open('https://www.gcsis.cn/expert/', '_blank');
        break;
      case 'map':
        navigate('/map');
        break;
      case 'security':
        navigate('/security');
        break;
      default:
        break;
    }
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <Layout className="schedule-layout">
      <Header className="nav-header">
        <div className="nav-logo">
          <SafetyOutlined style={{ fontSize: '24px', color: '#fff' }} />
          <span className="nav-title">西湖论剑数字安全大会</span>
        </div>
        <div className="nav-right">
          <Menu
            theme="dark"
            mode="horizontal"
            className="nav-menu"
            onClick={handleNavClick}
            items={[
              { label: '返回首页', key: 'home', icon: <ScheduleOutlined /> },
              { label: '嘉宾介绍', key: 'speakers', icon: <TeamOutlined /> },
              { label: '会场地图', key: 'map', icon: <EnvironmentOutlined /> },
            ]}
          />
          <Dropdown
            menu={{
              items: [
                {
                  key: 'settings',
                  label: '个人设置',
                  icon: <SettingOutlined />,
                  onClick: () => navigate('/settings')
                },
                {
                  key: 'logout',
                  label: '退出登录',
                  icon: <LogoutOutlined />,
                  onClick: handleLogout
                }
              ]
            }}
            placement="bottomRight"
          >
            <Avatar
              style={{ backgroundColor: '#87d068', cursor: 'pointer' }}
              icon={<UserOutlined />}
            />
          </Dropdown>
        </div>
      </Header>
      <Content className="schedule-content">
        <div className="schedule-container">
          <h1>会议日程</h1>
          <div className="schedule-image">
            <img src={`${process.env.PUBLIC_URL}/pic/会议日程.webp`} alt="会议日程" />
          </div>
        </div>
      </Content>
    </Layout>
  );
};

export default SchedulePage; 