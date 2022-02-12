/**
 * 8B 验证规则
 */

/**
 * 查询-订单编号-规则
 * @returns {function(...[*]=)}
 */
export const verifyOrderId = () => {
  return (rule, value, callback) => {
    const reg = /^\d+$/;
    if (value && !reg.test(value)) {
      callback(new Error("请输入数字"));
    } else if (value && value.toString().length < 6) {
      callback(new Error("请输入订单编号后6位数字"));
    } else {
      callback();
    }
  };
};
