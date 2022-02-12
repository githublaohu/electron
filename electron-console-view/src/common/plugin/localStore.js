/** *
 * description: 本地存储
 * Author: ricopter@qq.com
 * Data: 2020/3/28
 * Time: 18:08
 * */
import Cookies from "js-cookie";

export default {
  set(n, val) {
    // safari 隐私浏览器会报错
    const valType = Object.prototype.toString.call(val);
    if (valType === "[object Object]" || valType === "[object Array]") {
      val = JSON.stringify(val);
    }
    if (window.localStorage && localStorage.setItem) {
      try {
        localStorage.setItem(n, val);
      } catch (e) {
        Cookies.set(n, val);
      }
    } else {
      Cookies.set(n, val);
    }
    return this;
  },
  get(n) {
    if (window.localStorage && localStorage.getItem) {
      try {
        return localStorage.getItem(n);
      } catch (e) {
        return Cookies.get(n);
      }
    } else {
      return Cookies.get(n);
    }
  },
  remove(n) {
    if (window.localStorage && localStorage.removeItem) {
      try {
        localStorage.removeItem(n);
      } catch (e) {
        Cookies.remove(n);
      }
    } else {
      Cookies.remove(n);
    }
    return this;
  },
};
