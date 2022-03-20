/**
 * element ui表单通用验证规则
 **/
import {validateSpecial, validateNumber,validateURL,validateChineseID } from './validate'

/**
 * 验证身份证ID
 */
export const validateChinaID = (rule, value, callback) => {
  if (value && !validateChineseID(value)) {
    callback(new Error('请正确输入身份证号码！'))
  }else {
    callback()
  }
};

/**
 * 验证联系方式
 */
export const validateLinkPhone = (rule, value, callback) => {
  const reg = /^[0-9|\-|/|_]+$/ig;
  const maxWord = 30;
  if (value && !reg.test(value)) {
    callback(new Error('请输入有效联系方式!'))
  }else if(value && !reg.test(value) && value.length>maxWord){
    callback(new Error(`最多可输入${maxWord}字!`))
  }else {
    callback()
  }
};

/**
 * 验证数字输入
 */
export const validateNumberInput = (rule, value, callback) => {
  if (value && !validateNumber(value)) {
    callback(new Error('请输入有效数字!'))
  } else {
    callback()
  }
};

/**
 * 普通input字符串验证 中 英 数字
 */
export const validateInput = (rule, value, callback) => {
  const reg = /[\u4e00-\u9fa5]|[A-Z]|[a-z]|[0-9]+$/i;
  if (value && (validateSpecial(value) || !reg.test(value))) {
    callback(new Error('非法字符,请重新输入!'))
  }else {
    callback()
  }
};
/**
 * 普通input字符串验证英文符号
 */
export const validateEnglish = (words)=>{
  return (rule, value, callback) => {
  const reg = /^[a-z_A-Z0-9-]+$/i;
  if (!value) {
    callback(new Error('请填写' + words))
  }

  if (value && (validateSpecial(value) || !reg.test(value))) {
    callback(new Error('非法字符,请重新输入!'))
  }else {
    callback()
  }
}
};
