// 新增antd样式导入
import 'antd/dist/reset.css';
import React, { useState, useEffect } from 'react';
import { Layout, Menu, Card, Typography, Button, Input, Modal, Avatar, Dropdown, List, Tag, Select } from 'antd';
import { SafetyOutlined, TeamOutlined, ScheduleOutlined, EnvironmentOutlined, SendOutlined, UserOutlined, SettingOutlined, LogoutOutlined } from '@ant-design/icons';
import './Home.css';
import { useNavigate } from 'react-router-dom';
import { getCurrentUser, logout } from '../../services/auth';
import { askQuestion } from '../../services/ai';

const { Header, Content } = Layout;
const { Title } = Typography;

export default function HomePage() {
  // 新增状态管理
  const [messages, setMessages] = useState([]);
  const [inputText, setInputText] = useState('');
  const [welcomeVisible, setWelcomeVisible] = useState(true);
  const [isLoading, setIsLoading] = useState(false);

  const navigate = useNavigate();

  const messageEndRef = React.useRef(null);

  useEffect(() => {
    const timer = setTimeout(() => {
      setWelcomeVisible(true);
    }, 500);
    return () => clearTimeout(timer);
  }, []);

  useEffect(() => {
    if (messageEndRef.current) {
      messageEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages]);

  const handleSend = async () => {
    if (!inputText.trim() || isLoading) return;

    setIsLoading(true);

    // 添加用户消息
    const userMessage = {
      type: 'user',
      text: inputText.trim(),
      time: new Date().toLocaleTimeString()
    };
    
    setMessages(prev => [...prev, userMessage]);
    
    // 添加一个临时的AI消息，表示正在处理
    const tempAiMessage = {
      type: 'ai',
      text: '正在思考中...',
      time: new Date().toLocaleTimeString(),
      loading: true
    };
    
    setMessages(prev => [...prev, tempAiMessage]);
    setInputText(''); // 清空输入框

    try {
      // 调用AI服务
      const response = await askQuestion(userMessage.text);
      console.log('AI响应:', response);

      // 移除临时消息，添加实际回答
      if (response.success) {
        setMessages(prev => 
          prev.filter(msg => !msg.loading).concat({
            type: 'ai',
            text: response.answer,
            time: new Date().toLocaleTimeString()
          })
        );
      } else {
        // 处理错误情况
        setMessages(prev => 
          prev.filter(msg => !msg.loading).concat({
            type: 'ai',
            text: '抱歉，我暂时无法回答这个问题。请稍后再试。',
            time: new Date().toLocaleTimeString(),
            isError: true
          })
        );
      }
    } catch (error) {
      console.error('AI对话错误:', error);
      // 处理错误情况
      setMessages(prev => 
        prev.filter(msg => !msg.loading).concat({
          type: 'ai',
          text: '抱歉，服务出现了问题。请稍后再试。',
          time: new Date().toLocaleTimeString(),
          isError: true
        })
      );
    } finally {
      setIsLoading(false);
    }
  };
  
  // 辅助函数：根据问题获取备选回答
  const getFallbackAnswer = (question) => {
    // 为常见问题提供本地回复
    const lowerQuestion = question.toLowerCase();
    
    if (lowerQuestion.includes('你是谁') || lowerQuestion.includes('你叫什么')) {
      return '我是西湖论剑数字安全大会的AI助手，很高兴为您服务！';
    } else if (lowerQuestion.includes('123')) {
      return '您好，这是一个简单的测试回复。我正常工作中！';
    } else if (lowerQuestion.includes('5月15')) {
      return '西湖论剑数字安全大会将于2024年5月15日至17日在杭州国际博览中心举行。5月15日为开幕式与主旨演讲。';
    } else if (lowerQuestion.includes('时间') || lowerQuestion.includes('几号')) {
      return '西湖论剑数字安全大会将于2024年5月15日至17日举办，共持续三天。';
    } else if (lowerQuestion.includes('地点') || lowerQuestion.includes('在哪里')) {
      return '西湖论剑数字安全大会将在杭州国际博览中心举办，地址是浙江省杭州市萧山区奔竞大道353号。';
    }
    
    // 默认回答
    return '我是西湖论剑数字安全大会的AI助手，很高兴为您服务！请问有什么可以帮助您的吗？';
  };

  const handleNavClick = (type) => {
    const messages = {
      schedule: '正在跳转至会议日程...',
      speakers: '正在加载嘉宾信息...',
      map: '正在打开会场地图...',
      security: '正在获取安全指南...'
    };
    setMessages(prev => [...prev, {
      type: 'system',
      text: messages[type],
      time: new Date().toLocaleTimeString()
    }]);
  };

  const handleLogout = () => {
    logout();
  };

  const user = getCurrentUser();
  const username = user ? user.username : '访客';

  return (
    <Layout className="home-layout">
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
          onClick={({ key }) => {
            const navMap = {
              schedule: () => handleNavClick('schedule'),
              speakers: () => handleNavClick('speakers'),
              map: () => navigate('/map'),
              security: () => handleNavClick('security')
            };
            navMap[key]?.();
          }}
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
        <Dropdown
          overlay={
            <Menu className="user-menu">
              <Menu.Item key="info" className="user-info">
                <div className="user-id">ID: {user ? user.userId : 'Guest'}</div>
                <div className="user-name">用户名: {username}</div>
              </Menu.Item>
              <Menu.Divider />
              <Menu.Item 
                key="settings" 
                icon={<SettingOutlined />}
                onClick={() => navigate('/settings')}
              >
                用户设置
              </Menu.Item>
              <Menu.Item 
                key="logout" 
                icon={<LogoutOutlined />}
                onClick={handleLogout}
              >
                退出登录
              </Menu.Item>
            </Menu>
          }
          trigger={['hover']}
          placement="bottomRight"
        >
          <Avatar 
            className="user-avatar"
            icon={<UserOutlined />}
            style={{ backgroundColor: '#1890ff', cursor: 'pointer' }}
          />
        </Dropdown>
      </Header>

      <Content className="content">
        <div className="main-layout">
          {/* 左侧排行面板 */}
          <div className="left-panel">
            <Card 
              title="论坛关注度实时排行" 
              bordered={false}
              extra={
                <Select defaultValue="realtime" style={{ width: 120 }}>
                  <Option value="realtime">实时热度</Option>
                  <Option value="daily">今日排行</Option>
                  <Option value="weekly">本周排行</Option>
                </Select>
              }
            >
              <div className="ranking-chart">
                {/* 环形进度图占位 */}
                <div className="chart-placeholder" />
              </div>
              <List
                dataSource={[
                  { id: 1, name: '云安全架构设计', hot: 2453, trend: 'up' },
                  { id: 2, name: 'AI安全攻防', hot: 1987, trend: 'down' },
                  { id: 3, name: '数据隐私保护', hot: 1823, trend: 'up' },
                  { id: 4, name: '物联网安全', hot: 1654, trend: 'up' },
                  { id: 5, name: '区块链安全', hot: 1421, trend: 'steady' },
                ]}
                renderItem={(item) => (
                  <List.Item className="ranking-item">
                    <div className="rank-info">
                      <span className="trend-icon">
                        {item.trend === 'up' ? '↑' : item.trend === 'down' ? '↓' : '→'}
                      </span>
                      <span className="forum-name">{item.name}</span>
                    </div>
                    <Tag color={item.trend === 'up' ? 'green' : 'volcano'}>
                      {item.hot.toLocaleString()}
                    </Tag>
                  </List.Item>
                )}
              />
            </Card>
          </div>

          {/* 右侧主内容区 */}
          <div className="right-panel">
            {/* 新增导航面板 */}
            <div className="navigation-panel">
              <Card 
                title="大会导航"
                bordered={false}
                className="navigation-card"
              >
                <div className="nav-grid">
                  <div className="nav-item" onClick={() => handleNavClick('schedule')}>
                    <ScheduleOutlined className="nav-icon" />
                    <span>会议日程</span>
                  </div>
                  <div className="nav-item" onClick={() => handleNavClick('speakers')}>
                    <TeamOutlined className="nav-icon" />
                    <span>嘉宾介绍</span>
                  </div>
                  <div className="nav-item" onClick={() => navigate('/map')}>
                    <EnvironmentOutlined className="nav-icon" />
                    <span>会场地图</span>
                  </div>
                  <div className="nav-item" onClick={() => handleNavClick('security')}>
                    <SafetyOutlined className="nav-icon" />
                    <span>安全指南</span>
                  </div>
                </div>
              </Card>
            </div>

            {/* 欢迎信息模态框 */}
            <Modal
              title="欢迎来到2024西湖论剑数字安全大会"
              visible={welcomeVisible}
              onOk={() => setWelcomeVisible(false)}
              onCancel={() => setWelcomeVisible(false)}
              okText="开始使用"
              cancelText="稍后再说"
              width={600}
            >
              <div className="welcome-modal-content">
                <p>本次大会将聚焦数字安全领域的最新发展和挑战，汇聚行业专家共同探讨前沿技术。</p>
                <p>您可以通过以下方式获取帮助：</p>
                <ul>
                  <li>使用右侧AI智能助手进行实时问答</li>
                  <li>查看上方导航栏的会议日程和嘉宾信息</li>
                  <li>访问各展台的互动演示系统</li>
                </ul>
              </div>
            </Modal>

            {/* 常驻AI对话框 */}
            <div className="chat-panel">
              <div className="chat-header">
                <h3>AI智能助手</h3>
              </div>
              
              <div className="message-area" style={{ 
                height: '350px', 
                overflowY: 'auto', 
                padding: '10px',
                scrollBehavior: 'smooth' 
              }}>
                {messages.map((msg, index) => (
                  <div key={index} className={`message-bubble ${msg.type}`}>
                    <div className="message-header">
                      <span className="message-type">{msg.type === 'user' ? '您' : 'AI助手'}</span>
                      <span className="message-time">{msg.time}</span>
                    </div>
                    <div className="message-text">{msg.text}</div>
                  </div>
                ))}
                <div ref={messageEndRef} />
              </div>

              <div className="input-area">
                <div className="input-wrapper">
                  <Input.TextArea
                    rows={1}
                    autoSize={{ minRows: 1, maxRows: 4 }}
                    placeholder="输入您的问题..."
                    value={inputText}
                    onChange={(e) => setInputText(e.target.value)}
                    onPressEnter={(e) => {
                      if (!e.shiftKey) {
                        e.preventDefault();
                        handleSend();
                      }
                    }}
                    className="chat-input"
                    style={{ width: '100%' }}
                  />
                  <Button 
                    type="primary" 
                    shape="circle"
                    onClick={handleSend}
                    className="send-button"
                    icon={<SendOutlined />}
                    disabled={!inputText.trim()}
                    style={{ 
                      backgroundColor: inputText.trim() ? '#1677ff' : '#bfbfbf',
                      borderColor: inputText.trim() ? '#1677ff' : '#bfbfbf'
                    }}
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </Content>
    </Layout>
  );
}