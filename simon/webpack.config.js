module.exports = {
  // ...
  devServer: {
    static: {
      directory: path.join(__dirname, '../pic'),
      publicPath: '/pic',
    },
  }
} 