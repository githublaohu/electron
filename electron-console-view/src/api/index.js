/**
 * easy api 模块导入
 */
import { globalFetchConfiguration } from "@/config/apiIntercept";
import { INJECTION_CONFIG } from "@/config";
import easyApi from "@/common/plugin/fetch"; // 初始化 easyApi 拦截器
// easy api 模块注册
const { registerModule } = easyApi();
// 手动统一模块注册
function exportEasyApi(options) {
  const { prefix, module } = options;
  return registerModule({
    ...globalFetchConfiguration, // 全局fetch策略
    prefix,
    module,
  });
}

// 业务api导入
import demo from "./modules/demo";

/**
 * 导出
 */
const demoApi = exportEasyApi({
  prefix: INJECTION_CONFIG.urlPrefix,
  module: demo,
});

export default {
  demoApi,
};
