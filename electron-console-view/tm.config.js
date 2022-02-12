module.exports = {
  "devServer": {
    "proxy": {
      "/api": {
        "target": "http://10.0.75.1:8900",
        "pathRewrite": {
          '^/api': '',
          "changeOrigin": true
        }
      }
    }
  }
};
