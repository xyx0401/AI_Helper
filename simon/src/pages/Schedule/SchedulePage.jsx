import React from 'react';
import { Layout, Menu, Typography } from 'antd';
import { SafetyOutlined, TeamOutlined, ScheduleOutlined, EnvironmentOutlined } from '@ant-design/icons';
import './SchedulePage.css';
import { useNavigate } from 'react-router-dom';

const { Header, Content } = Layout;
const { Title } = Typography;

const SchedulePage = () => {
  const navigate = useNavigate();

  const handleNavClick = (type) => {
    if (type === 'schedule') {
      navigate('/schedule');
    } else if (type === 'speakers') {
      navigate('/speakers');
    } else if (type === 'map') {
      navigate('/map');
    } else if (type === 'security') {
      navigate('/security');
    }
  };

  return (
    <Layout className="schedule-layout">
      <Header className="header">
        <div className="logo">
          <SafetyOutlined style={{ fontSize: '24px', color: '#fff' }} />
          <Title level={3} style={{ color: '#fff', margin: '0 0 0 16px' }}>
            西湖论剑数字安全大会
          </Title>
        </div>
        <Menu
          theme="dark"
          mode="horizontal"
          className="top-nav"
          onClick={({ key }) => handleNavClick(key)}
        >
          <Menu.Item key="schedule">
            <ScheduleOutlined /> 会议日程
          </Menu.Item>
          <Menu.Item key="speakers">
            <TeamOutlined /> 嘉宾介绍
          </Menu.Item>
          <Menu.Item key="map">
            <EnvironmentOutlined /> 会场地图
          </Menu.Item>
          <Menu.Item key="security">
            <SafetyOutlined /> 安全指南
          </Menu.Item>
        </Menu>
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