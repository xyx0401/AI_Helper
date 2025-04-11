CREATE TABLE user (
    user_id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    role VARCHAR(20) DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户偏好表
CREATE TABLE user_preference (
    preference_id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    interests TEXT,
    language VARCHAR(10) DEFAULT 'zh-CN',
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- 会议表
CREATE TABLE conference (
    conference_id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    location_id VARCHAR(36) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    description TEXT,
    FOREIGN KEY (location_id) REFERENCES location(location_id)
);

-- 演讲嘉宾表
CREATE TABLE speaker (
    speaker_id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    title VARCHAR(100),
    bio TEXT
);

-- 会议议程表
CREATE TABLE agenda (
    agenda_id VARCHAR(36) PRIMARY KEY,
    conference_id VARCHAR(36) NOT NULL,
    session_title VARCHAR(200) NOT NULL,
    speaker_id VARCHAR(36),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    FOREIGN KEY (conference_id) REFERENCES conference(conference_id) ON DELETE CASCADE,
    FOREIGN KEY (speaker_id) REFERENCES speaker(speaker_id) ON DELETE SET NULL
);

-- 问答知识库表
CREATE TABLE qa_knowledge (
    qa_id VARCHAR(36) PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    score FLOAT DEFAULT 0.9,
    conference_id VARCHAR(36),
    FOREIGN KEY (conference_id) REFERENCES conference(conference_id) ON DELETE CASCADE
);

-- 推荐记录表
CREATE TABLE recommendation (
    recommendation_id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    conference_id VARCHAR(36) NOT NULL,
    agenda_id VARCHAR(36),
    reason TEXT,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (conference_id) REFERENCES conference(conference_id) ON DELETE CASCADE,
    FOREIGN KEY (agenda_id) REFERENCES agenda(agenda_id) ON DELETE SET NULL
);

-- 会议总结表
CREATE TABLE meeting_summary (
    summary_id VARCHAR(36) PRIMARY KEY,
    conference_id VARCHAR(36) NOT NULL,
    key_points TEXT,
    todo_list TEXT,
    generated_by VARCHAR(36),
    FOREIGN KEY (conference_id) REFERENCES conference(conference_id) ON DELETE CASCADE
);

-- 会议资料表
CREATE TABLE resource (
    resource_id VARCHAR(36) PRIMARY KEY,
    conference_id VARCHAR(36) NOT NULL,
    agenda_id VARCHAR(36),
    file_url VARCHAR(255) NOT NULL,
    file_type VARCHAR(20),
    FOREIGN KEY (conference_id) REFERENCES conference(conference_id) ON DELETE CASCADE,
    FOREIGN KEY (agenda_id) REFERENCES agenda(agenda_id) ON DELETE SET NULL
);

-- 访问日志表
CREATE TABLE access_log (
    log_id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    action VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_address VARCHAR(45),
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- 加密密钥表
CREATE TABLE encryption_key (
    key_id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    encrypted_key TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

-- 会议地点详情表
CREATE TABLE location (
    location_id VARCHAR(36) PRIMARY KEY,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    address VARCHAR(255)
);

-- 论坛议题热度表
CREATE TABLE forum_topic (
    topic_id VARCHAR(36) PRIMARY KEY,
    conference_id VARCHAR(36) NOT NULL,
    title VARCHAR(200) NOT NULL,
    view_count INT DEFAULT 0,
    vote_count INT DEFAULT 0,
    FOREIGN KEY (conference_id) REFERENCES conference(conference_id) ON DELETE CASCADE
);

-- 语音转写记录表
CREATE TABLE speech_transcript (
    transcript_id VARCHAR(36) PRIMARY KEY,
    conference_id VARCHAR(36) NOT NULL,
    speaker_id VARCHAR(36),
    original_text TEXT NOT NULL,
    translated_text TEXT,
    FOREIGN KEY (conference_id) REFERENCES conference(conference_id) ON DELETE CASCADE,
    FOREIGN KEY (speaker_id) REFERENCES speaker(speaker_id) ON DELETE SET NULL
);

-- 创建用户信息表
CREATE TABLE IF NOT EXISTS user_info (
    user_id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email)
);

-- 创建资源下载表
CREATE TABLE IF NOT EXISTS resource_download (
    download_id VARCHAR(36) PRIMARY KEY,
    resource_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(100),
    file_size BIGINT,
    download_url VARCHAR(255) NOT NULL,
    download_count INT DEFAULT 0,
    last_download_time TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_info(user_id),
    INDEX idx_resource_id (resource_id),
    INDEX idx_user_id (user_id)
);

-- 创建论坛统计表
CREATE TABLE IF NOT EXISTS forum_statistics (
    statistics_id VARCHAR(36) PRIMARY KEY,
    forum_id VARCHAR(36) NOT NULL,
    forum_title VARCHAR(200) NOT NULL,
    view_count INT DEFAULT 0,
    favorite_count INT DEFAULT 0,
    comment_count INT DEFAULT 0,
    share_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_forum_id (forum_id)
);

-- 创建论坛统计表
CREATE TABLE IF NOT EXISTS forum_statistics (
    statistics_id VARCHAR(36) PRIMARY KEY,
    forum_id VARCHAR(36) NOT NULL,
    forum_title VARCHAR(200) NOT NULL,
    view_count INT DEFAULT 0,
    favorite_count INT DEFAULT 0,
    comment_count INT DEFAULT 0,
    share_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_forum_id (forum_id)
);

-- 创建索引
CREATE UNIQUE INDEX idx_user_username ON user(username);
CREATE UNIQUE INDEX idx_user_email ON user(email);
CREATE INDEX idx_conference_start_time ON conference(start_time);
CREATE FULLTEXT INDEX idx_qa_question ON qa_knowledge(question);
```