/**
 * element ui表单通用验证规则
 * */

import { validateSpecial, validateChineseID } from "./validate";

/**
 * 验证手机号码 必填
 */
export const validatePhone = (isMust = false) => {
  return (rule, value, callback) => {
    const reg = /^1[3456789]\d{9}$/;
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入手机号码"));
      return;
    }
    if (value && !reg.test(value)) {
      callback(new Error("请输入有效手机号码!"));
    } else {
      callback();
    }
  };
};

/**
 * 普通input字符串验证 过滤非法字符串
 */
export const validateSpecialInput = (isMust = false) => {
  return (rule, value, callback) => {
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    if (value && validateSpecial(value)) {
      callback(new Error("非法字符,请重新输入!"));
    } else {
      callback();
    }
  };
};
export const validateSpecialInputLimit = (isMust = false, maxWord = 100) => {
  return (rule, value, callback) => {
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    if (value && validateSpecial(value)) {
      callback(new Error("非法字符,请重新输入!"));
    }
    if (value && !validateSpecial(value) && value.length > maxWord) {
      callback(new Error(`最多可输入${maxWord}字!`));
    } else {
      callback();
    }
  };
};
/**
 * 填写中文和英文大小写+符号+数字 最多100个字
 */
export const validateInputLimit = (isMust = false, maxWord = 100) => {
  return (rule, value, callback) => {
    const reg = /[\u4e00-\u9fa5]|[A-Z]|[a-z]|[0-9]+$/i;
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    if (value && (validateSpecial(value) || !reg.test(value))) {
      callback(new Error("非法字符,请重新输入!"));
    }
    if (value && reg.test(value) && value.length > maxWord) {
      callback(new Error(`最多可输入${maxWord}字!`));
    } else {
      callback();
    }
  };
};

/**
 *  验证输入名称 中英文 必填
 */
export const validateCNENLimit = (isMust = false, maxWord = 10) => {
  return (rule, value, callback) => {
    const reg = /[\u4e00-\u9fa5]|[A-Z]|[a-z]+$/i;
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    if (value && (validateSpecial(value) || !reg.test(value))) {
      callback(new Error("非法字符,请重新输入!"));
    } else if (value && reg.test(value) && value.length > maxWord) {
      callback(new Error(`最多可输入${maxWord}字!`));
    } else {
      callback();
    }
  };
};
/**
 * 验证身份证ID 必填
 */
export const validateChinaID = (isMust = false) => {
  return (rule, value, callback) => {
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    if (value && !validateChineseID(value)) {
      callback(new Error("请正确输入身份证号码！"));
    } else {
      callback();
    }
  };
};

/**
 * 验证电子邮箱
 */
export const validateEmail = (isMust = false) => {
  return (rule, value, callback) => {
    const reg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    if (value && !reg.test(value)) {
      callback(new Error("请正确输入邮箱！"));
    } else {
      callback();
    }
  };
};

/**
 * 只验证长度
 */
export const validateLengthLimit = (
  isMust = false,
  maxWord = 50,
  name = "此字段",
  mimWord = 1
) => {
  return (rule, value, callback) => {
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入" + name));
      return;
    }
    if (isMust && value && value.length < mimWord) {
      callback(new Error(`最少输入${mimWord}字!`));
      return;
    }
    if (value && value.length > maxWord) {
      callback(new Error(`最多可输入${maxWord}字!`));
    } else {
      callback();
    }
  };
};
/**
 * 只验证数字
 */
export const validateNumberLengthLimit = (
  isMust = false,
  maxWord = 50,
  mimWord = 1
) => {
  return (rule, value, callback) => {
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    const reg = /^\d+$/;
    if (value && !reg.test(value)) {
      callback(new Error("请输入数字"));
      return;
    }
    if (value && value.length < mimWord) {
      callback(new Error(`最少输入${mimWord}字!`));
      return;
    }
    if (value && value.length > maxWord) {
      callback(new Error(`最多可输入${maxWord}字!`));
    } else {
      callback();
    }
  };
};
/**
 * 验证微信号
 */
export const validateWechatNumber = (
  isMust = false,
  maxWord = 50,
  mimWord = 1
) => {
  return (rule, value, callback) => {
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    const reg = /^[-_a-zA-Z0-9]+$/;
    if (value && !reg.test(value)) {
      callback(new Error("请输入正确的微信号码"));
      return;
    }
    if (value && value.length < mimWord) {
      callback(new Error(`最少输入${mimWord}字!`));
      return;
    }
    if (value && value.length > maxWord) {
      callback(new Error(`最多可输入${maxWord}字!`));
    } else {
      callback();
    }
  };
};
/**
 * 只验证汉字
 */
export const validateChineseLimit = (
  isMust = false,
  maxWord = 50,
  mimWord = 1,
  filed = ""
) => {
  return (rule, value, callback) => {
    if (isMust && (!value || value.length === 0)) {
      callback(new Error(`请输入${filed}`));
      return;
    }
    const reg = /^[\u4e00-\u9fa5·]+$/g;
    if (value && !reg.test(value)) {
      callback(new Error(`${filed}请输入汉字`));
      return;
    }
    if (value && value.length < mimWord) {
      callback(new Error(`${filed}最少输入${mimWord}字!`));
      return;
    }
    if (value && value.length > maxWord) {
      callback(new Error(`${filed}最多可输入${maxWord}字!`));
    } else {
      callback();
    }
  };
};

/**
 * 只验证中+英文+数字
 * @param isMust
 * @param maxWord
 * @returns {Function}
 */
export const validateNumberWordChinaLimit = (
  isMust = false,
  maxWord = 100,
  mimWord = 1
) => {
  return (rule, value, callback) => {
    const reg = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/i;
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    if (isMust && value && reg.test(value) && value.length < mimWord) {
      callback(new Error(`最少输入${mimWord}字!`));
      return;
    }
    if (value && reg.test(value) && value.length > maxWord) {
      callback(new Error(`最多可输入${maxWord}字!`));
    } else if (value && !reg.test(value)) {
      callback(new Error("非法字符,请重新输入!"));
    } else {
      callback();
    }
  };
};
/**
 * 只验证中文+数字
 * @param isMust
 * @param maxWord
 * @returns {Function}
 */
export const validateChineseNumberLimit = (
  isMust = false,
  maxWord = 100,
  mimWord = 1
) => {
  return (rule, value, callback) => {
    const reg = /^[0-9\u4e00-\u9fa5]+$/i;
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    if (isMust && value && reg.test(value) && value.length < mimWord) {
      callback(new Error(`最少输入${mimWord}字!`));
      return;
    }
    if (value && reg.test(value) && value.length > maxWord) {
      callback(new Error(`最多可输入${maxWord}字!`));
    } else if (value && !reg.test(value)) {
      callback(new Error("只可输入中文和阿拉伯数字!"));
    } else {
      callback();
    }
  };
};
/**
 * 只验证大写英文+数字
 * @param isMust
 * @param maxWord
 * @returns {Function}
 */
export const validateNumberCapitalletterLimit = (
  isMust = false,
  maxWord = 100,
  mimWord = 1
) => {
  return (rule, value, callback) => {
    const reg = /^[A-Z0-9]+$/g;
    if (isMust && (!value || value.length === 0)) {
      callback(new Error("请输入此字段"));
      return;
    }
    if (value && !reg.test(value)) {
      callback(new Error("只能输入大写字母和数字"));
      return;
    }
    if (value && value.length < mimWord) {
      callback(new Error(`最少输入${mimWord}字!`));
      return;
    }
    if (value && value.length > maxWord) {
      callback(new Error(`最多可输入${maxWord}字!`));
    } else {
      callback();
    }
  };
};
