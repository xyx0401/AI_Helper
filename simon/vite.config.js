export default defineConfig({
  // 添加以下配置
  server: {
    fs: {
      strict: false, // 允许访问项目根目录外的文件
      allow: ['..'] // 允许访问上级目录
    }
  }
}) 