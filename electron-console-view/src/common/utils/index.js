/**
 * Created by jiachenpan on 16/11/18.
 */

export function parseTime(time, cFormat) {
  if (arguments.length === 0) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if (('' + time).length === 10) time = parseInt(time) * 1000
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') { return ['日', '一', '二', '三', '四', '五', '六'][value ] }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}

export function formatTime(time, option) {
  time = +time * 1000
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) {
    // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return (
      d.getMonth() +
      1 +
      '月' +
      d.getDate() +
      '日' +
      d.getHours() +
      '时' +
      d.getMinutes() +
      '分'
    )
  }
}

export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * 时间格式化
 * @param fmt "yyyy-MM-dd hh:mm:ss"
 * @param date date对象
 * @returns {*}
 */
export function dateFtt(fmt,date){
  let o = {
    "M+" : date.getMonth()+1,                 //月份
    "d+" : date.getDate(),                    //日
    "h+" : date.getHours(),                   //小时
    "m+" : date.getMinutes(),                 //分
    "s+" : date.getSeconds(),                 //秒
    "q+" : Math.floor((date.getMonth()+3)/3), //季度
    "S"  : date.getMilliseconds()             //毫秒
  };
  if(/(y+)/.test(fmt))
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(let k in o)
    if(new RegExp("("+ k +")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
  return fmt;
}
/**
 * 动态创建加载js 需要优化重复加载相同js问题
 * @param url
 * @returns {Promise<any>}
 */
export function loadJs(url){
  return new Promise((resolve,reject)=>{
   try {
     let script=document.createElement('script');
     script.type="text/javascript";
     if(script.readyState){
       script.onreadystatechange=function(){
         if(script.readyState == "loaded" || script.readyState == "complete"){
           script.onreadystatechange=null;
           resolve();
         }
       }
     }else{
       script.onload=function(){
         resolve();
       }
     }
     script.src=url;
     document.body.appendChild(script);
   }catch (e) {
     reject(e)
   }
  })
}

/**
 * 延时执行
 * @param func
 * @param delay
 * @returns {Function}
 */
export function debounce(func, delay= 700) {
  let timer
  return function (...args) {
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(() => {
      func.apply(this, args)
    }, delay)
  }
}

/**
 * blob下载文件
 * @param blob
 * @param fileName
 * https://blog.csdn.net/OLiver_web/article/details/79742868
 */
export function downFile(blob, fileName) {
  if (window.navigator.msSaveOrOpenBlob) {
    navigator.msSaveBlob(blob, fileName);
  } else {
    let link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = fileName;
    link.click();
    window.URL.revokeObjectURL(link.href);
  }
}

/**
 * a 标签下载方式
 * @param url
 * @param saveName
 */
export function openDownloadDialog(url, saveName) {
  return new Promise((resolve, reject) => {
    if (window.navigator.msSaveBlob) {
      // for ie 10 and later
      try {
        var blobObject = new Blob([self.output]);
        window.navigator.msSaveBlob(blobObject, saveName);
        resolve()
      }
      catch (e) {
        console.log(e);
        reject(e)
      }
    } else {
      // 谷歌浏览器 创建a标签 添加download属性下载
      if (typeof url == 'object' && url instanceof Blob) {
        url = URL.createObjectURL(url); // 创建blob地址
      }
      var aLink = document.createElement('a');
      aLink.href = url;
      aLink.download = saveName || ''; // HTML5新增的属性，指定保存文件名，可以不要后缀，注意，file:///模式下不会生效
      var event;
      if (window.MouseEvent) {
        event = new MouseEvent('click');
      }
      else {
        event = document.createEvent('MouseEvents');
        event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
      }
      aLink.dispatchEvent(event);
      resolve()
    }
  })
}

/**
 * 取出两个数组的不同元素
 */
export function getArrDiff(arr1, arr2) {
  return arr1.concat(arr2).filter(function(v, i, arr) {
    return arr.indexOf(v) === arr.lastIndexOf(v);
  });
}

/**
 * 取两数组相同元素
 */
export function getArrSame(arr1, arr2) {
  let newArr = [];
  for (let i = 0; i < arr2.length; i++) {
    for (let j = 0; j < arr1.length; j++) {
      if(arr1[j] === arr2[i]){
        newArr.push(arr1[j]);
      }
    }
  }
  return newArr;
}
