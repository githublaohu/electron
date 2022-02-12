/**
 * 工具函数
 */
const fileNameRE = /^\.\/([^\s]+)\.js$/;

/**
 * 加载模块文件
 * @param modulesContext
 * @returns Object
 */
export function importAll(modulesContext) {
  return modulesContext.keys().map((modulesPath) => {
    const moduleName = modulesPath.match(fileNameRE);
    return {
      moduleName,
      camelModuleName: moduleName[1].replace(/-(\w)/g, (_, c) =>
        c ? c.toUpperCase() : ""
      ),
      module: modulesContext(modulesPath).default,
    };
  });
}

/**
 * 环境切换应用注入配置
 * @param config 默认配置
 * @param injectionConfig 注入配置
 * @returns {any}
 */
export function switchAppEnv(config, injectionConfig) {
  const currentEnv = process.env.APP_ENV;
  if (currentEnv && config[currentEnv]) {
    // development 环境不合并html注入值
    if (currentEnv === "development") {
      return { ...config[currentEnv] };
    } else {
      return { ...config[currentEnv], ...injectionConfig };
    }
  } else {
    console.warn("switchAppEnv 默认环境配置有误", currentEnv);
    return { ...injectionConfig };
  }
}

/**
 * 字符串'false' 转 boolean
 */
export function strToBoolean(str, key = "FALSE") {
  if (str && Object.prototype.toString.call(str) === "[object String]") {
    return str.toUpperCase() !== key;
  } else {
    return str;
  }
}

/**
 * boolean 转 字符串
 */
export function boolToStr(str) {
  if (str && Object.prototype.toString.call(str) === "[object Boolean]") {
    return str ? "TRUE" : "FALSE";
  } else {
    return "FALSE";
  }
}

/**
 * 清除空字段
 * @param obj
 * @returns {any}
 */
export const clearObjectNullField = (obj) => {
  const newObj = JSON.parse(JSON.stringify(obj));
  const specialList = [null, undefined, ""];

  Object.keys(newObj).forEach((k) => {
    if (specialList.includes(obj[k])) {
      delete newObj[k];
    }
  });

  return newObj;
};

/**
 * 动态创建加载js 需要优化重复加载相同js问题
 * @param url
 * @returns {Promise<any>}
 */
export function loadJs(url) {
  return new Promise((resolve, reject) => {
    try {
      const script = document.createElement("script");
      script.type = "text/javascript";
      if (script.readyState) {
        script.onreadystatechange = function () {
          if (
            script.readyState === "loaded" ||
            script.readyState === "complete"
          ) {
            script.onreadystatechange = null;
            resolve();
          }
        };
      } else {
        script.onload = function () {
          resolve();
        };
      }
      script.src = url;
      document.body.appendChild(script);
    } catch (e) {
      reject(e);
    }
  });
}
