import React, { useState } from 'react';
import { Layout, Menu, Card } from 'antd';
import { EnvironmentOutlined, ScheduleOutlined, TeamOutlined, SafetyOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import './MapPage.css';

const { Header, Content } = Layout;

const MapPage = () => {
  const [currentMap, setCurrentMap] = useState(1); // 当前显示的地图编号
  const navigate = useNavigate();

  const handleClick = () => {
    // 切换地图
    setCurrentMap((prev) => (prev % 3) + 1); // 1 -> 2 -> 3 -> 1
  };

  // 处理导航点击
  const handleNavClick = ({ key }) => {
    switch(key) {
      case 'home':
        navigate('/home');
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
    <Layout className="map-layout">
      <Header className="map-header">
        <div className="logo">
          <SafetyOutlined style={{ fontSize: '24px', color: '#fff' }} />
          <span style={{ color: '#fff', fontSize: '20px', marginLeft: '16px' }}>西湖论剑数字安全大会</span>
        </div>
        <Menu
          theme="dark"
          mode="horizontal"
          onClick={handleNavClick}
          items={[
            {
              key: 'home',
              label: '返回首页',
              icon: <SafetyOutlined />,
            },
            {
              key: 'schedule',
              label: '会议日程',
              icon: <ScheduleOutlined />,
            },
            {
              key: 'speakers',
              label: '嘉宾介绍',
              icon: <TeamOutlined />,
            }
          ]}
        />
      </Header>
      <Content className="map-content">
        <Card
          title={
            <div className="map-header">
              <EnvironmentOutlined style={{ marginRight: 8 }} />
              <span>会场地图</span>
            </div>
          }
          bordered={false}
        >
          <div className="map-image" onClick={handleClick}>
            <img
              src={`${process.env.PUBLIC_URL}/pic/地图${currentMap}.png`}
              alt={`会场地图${currentMap}`}
              style={{ width: '100%', borderRadius: '8px', cursor: 'pointer' }}
            />
          </div>
        </Card>
      </Content>
    </Layout>
  );
};

export default MapPage;