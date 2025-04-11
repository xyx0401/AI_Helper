import api from './api';

// 发送问题到AI并获取回答
export const askQuestion = async (question) => {
  try {
    console.log('发送AI问题:', question);
    
    // 调用后端AI对话接口
    const response = await api.post('/qa/ask', { 
      question,
      conferenceId: 'default'  // 使用默认会议ID
    });
    
    console.log('AI原始响应:', response);
    
    // 处理响应数据
    if (response.code === 200) {
      // 优先使用data.answer，如果没有则使用msg
      let answer = '';
      if (response.data && response.data.answer) {
        answer = response.data.answer;
      } else if (response.msg && response.msg !== '操作成功') {
        answer = response.msg;
      }
      
      // 清理回答内容
      answer = answer.trim();
      if (answer.startsWith('"') && answer.endsWith('"')) {
        answer = answer.slice(1, -1);
      }
      
      // 如果没有获取到有效回答
      if (!answer || answer === 'null' || answer === 'undefined') {
        throw new Error('未获取到有效的AI回答');
      }
      
      return {
        success: true,
        answer: answer
      };
    } else {
      throw new Error(response.msg || '请求失败');
    }
  } catch (error) {
    console.error('AI问答请求错误:', error);
    return {
      success: false,
      error: error.message || '请求失败'
    };
  }
}; 