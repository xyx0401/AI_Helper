import React, { useState } from 'react';
import { Layout, Menu, Card, Avatar, Dropdown } from 'antd';
import { 
  EnvironmentOutlined, 
  ScheduleOutlined, 
  TeamOutlined, 
  SafetyOutlined,
  UserOutlined
} from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import './MapPage.css';

const { Header, Content } = Layout;

const MapPage = () => {
  const [currentMap, setCurrentMap] = useState(1); // 当前显示的地图编号
  const navigate = useNavigate();

  const handleMapClick = () => {
    // 切换地图
    setCurrentMap((prev) => (prev % 3) + 1); // 1 -> 2 -> 3 -> 1
  };

  const handleNavClick = ({ key }) => {
    switch(key) {
      case 'home':
        navigate('/');
        break;
      case 'schedule':
        navigate('/schedule');
        break;
      case 'speakers':
        window.open('https://www.gcsis.cn/expert/', '_blank');
        break;
      case 'security':
        navigate('/security');
        break;
      default:
        break;
    }
  };

  return (
    <Layout className="map-container">
      <Header className="nav-header">
        <div className="nav-logo">
          <SafetyOutlined style={{ fontSize: '24px', color: '#fff' }} />
          <span className="nav-title">西湖论剑数字安全大会</span>
        </div>
        <div className="nav-right">
          <Menu
            theme="dark"
            mode="horizontal"
            className="top-nav"
            onClick={handleNavClick}
            items={[
              { label: '返回首页', key: 'home', icon: <SafetyOutlined /> },
              { label: '会议日程', key: 'schedule', icon: <ScheduleOutlined /> },
              { label: '嘉宾介绍', key: 'speakers', icon: <TeamOutlined /> },
            ]}
          />
          <Dropdown
            menu={{
              items: [
                {
                  key: 'settings',
                  label: '个人设置',
                  icon: <SafetyOutlined />,
                  onClick: () => navigate('/settings')
                },
                {
                  key: 'logout',
                  label: '退出登录',
                  icon: <SafetyOutlined />,
                  onClick: () => {
                    // Handle logout
                  }
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
      
      <Content className="map-content">
        <Card
          title={
            <div className="card-title">
              <EnvironmentOutlined />
              <span>会场地图导航</span>
            </div>
          }
          bordered={false}
        >
          <div className="map-wrapper" onClick={handleMapClick}>
            <img
              src={`/pic/地图${currentMap}.png`}
              alt={`会场地图${currentMap}`}
              className="map-image"
            />
          </div>
        </Card>
      </Content>
    </Layout>
  );
};

export default MapPage;