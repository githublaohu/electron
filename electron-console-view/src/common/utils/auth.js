/**
 * 授权信息本地存储
 */
import Cookies from "js-cookie";
import localStore from "../plugin/localStore";

const TokenKey = "Admin-Token";
const oidcKey = "oidc-info";
export function getToken() {
  return Cookies.get(TokenKey);
}

export function setToken(token) {
  return Cookies.set(TokenKey, token);
}

export function removeToken() {
  return Cookies.remove(TokenKey);
}
/** oidc 授权信息存储 * */
export function getLocalStore(key = oidcKey) {
  let oidcInfo;
  try {
    oidcInfo = JSON.parse(localStore.get(key));
  } catch (e) {
    oidcInfo = "";
  }
  return oidcInfo;
}

export function setLocalStore(val, key = oidcKey) {
  return localStore.set(key, val);
}

export function removeLocalStore(key = oidcKey) {
  return localStore.remove(key);
}
