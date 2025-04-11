package com.helperserver.service;

import com.helperserver.bean.QaKnowledge;
import java.util.List;

public interface QaService {
    /**
     * 获取问题答案
     * @param question 用户问题
     * @param conferenceId 关联会议ID
     * @return 问答结果
     */
    QaKnowledge getAnswer(String question, String conferenceId);

    /**
     * 获取问答历史
     * @param conferenceId 关联会议ID
     * @return 历史记录列表
     */
    List<QaKnowledge> getHistory(String conferenceId);
}