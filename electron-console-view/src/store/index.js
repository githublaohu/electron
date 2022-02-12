/** *
 * 新增store可能，热更新无效，需要重启
 */
import Vue from "vue";
import Vuex from "vuex";
import getters from "./getters";

Vue.use(Vuex);

const modulesFiles = require.context("./modules", true, /\.js$/);
/* eslint-disable */
const modules = modulesFiles.keys().reduce((modules, modulePath) => {
  // set './app.js' => 'app'
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, "$1");
  const value = modulesFiles(modulePath);
  modules[moduleName] = value.default;
  return modules;
}, {});

const store = new Vuex.Store({
  modules,
  getters,
});

export default store;
