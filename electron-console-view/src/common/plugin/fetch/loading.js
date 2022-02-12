/**
 * 全局loading
 */
import { Loading } from "element-ui";

let loadingCount = 0;
let loading;

const startLoading = (message) => {
  loading = Loading.service({
    // lock: true,
    fullscreen: true,
    text: message || "拼命加载中...",
    background: "rgba(255,255,255,0.1)",
  });
};

const endLoading = () => {
  loading && loading.close();
};

// loading
const showLoading = (url, message = "") => {
  startLoading(message);
  loadingCount += 1;
};

const hideLoading = () => {
  if (loadingCount <= 0) {
    return;
  }
  loadingCount -= 1;
  if (loadingCount === 0) {
    endLoading();
  }
};

export { showLoading, hideLoading };
