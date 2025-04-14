import React from 'react';
import { Menu, Button } from 'antd';
import { ScheduleOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';

const NavBar = () => {
  const navigate = useNavigate();

  const handleNavClick = (key) => {
    if (key === 'schedule') {
      navigate('/schedule');
    }
    // 其他导航逻辑保持不变
  };

  return (
    <Menu
      theme="dark"
      mode="horizontal"
      className="top-nav"
      onClick={({ key }) => handleNavClick(key)}
    >
      <Menu.Item key="schedule">
        <ScheduleOutlined /> 会议日程
      </Menu.Item>
      <Menu.Item key="alerts">安全告警</Menu.Item>
      <Menu.Item key="devices">接入设备</Menu.Item>
    </Menu>
  );
};

export default NavBar; 