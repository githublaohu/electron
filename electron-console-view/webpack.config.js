/**
 * 虚假的webpack配置,不影响项目,仅为ide路径识别
 */
module.exports = {
  resolve: {
    extensions: [".vue", ".js", ".scss", ".sass", "index.vue"],
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
};
