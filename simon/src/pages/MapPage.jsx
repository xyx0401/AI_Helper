import React, { useState } from 'react';
import { Layout, Menu, Card, Avatar } from 'antd';
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
        navigate('/speakers');
        break;
      default:
        break;
    }
  };

  return (
    <Layout className="map-container">
      <Header className="map-header">
        <div className="header-left">
          <SafetyOutlined className="logo-icon" />
          <span className="conference-title">西湖论剑数字安全大会</span>
        </div>
        <Menu
          theme="dark"
          mode="horizontal"
          className="nav-menu"
          onClick={handleNavClick}
          items={[
            { label: '返回首页', key: 'home', icon: <SafetyOutlined /> },
            { label: '会议日程', key: 'schedule', icon: <ScheduleOutlined /> },
            { label: '嘉宾介绍', key: 'speakers', icon: <TeamOutlined /> }
          ]}
        />
        <Avatar 
          className="user-avatar"
          icon={<UserOutlined />}
          style={{ backgroundColor: '#87d068' }}
        />
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
              src={`${process.env.PUBLIC_URL}/pic/地图${currentMap}.png`} // 使用PUBLIC_URL确保路径正确
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