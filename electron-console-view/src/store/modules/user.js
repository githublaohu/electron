import api from "@/api/index";
import { getToken, removeToken } from "@/common/utils/auth";
import { DEFAULT_USER_AVATAR } from "@/config/constants";
// import router, { resetRouter } from '@/router'

const state = {
  oidcConfig: null,
  oidcInfo: null, // OpenId Connect 身份认证  getLocalStore() 不能取本地缓存，否则无法退出登录
  token: getToken(),
  name: "",
  avatar: DEFAULT_USER_AVATAR,
  introduction: "",
  roles: [],
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction;
  },
  SET_NAME: (state, name) => {
    state.name = name;
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar || DEFAULT_USER_AVATAR;
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
  },

  SET_OIDC: (state, authInfo) => {
    state.oidcInfo = authInfo ? authInfo.oidcInfo : "";
    state.oidcConfig = authInfo ? authInfo.oidcConfig : "";
  },
};

const actions = {
  // sso oidc 授权登录信息存储
  loginOidc({ commit }, authInfo) {
    commit("SET_OIDC", authInfo);
    // setLocalStore(oidcInfo);
    // setLocalStore(oidcConfig,oidcConfigKey);
  },
  loginOutOidc({ commit }) {
    commit("SET_OIDC", null);
    // removeLocalStore();
    // removeLocalStore(oidcConfigKey);
  },

  $_userInfoOidc({ commit }, data) {
    const { userName, organizationName } = data;
    commit("SET_NAME", `${userName}(${organizationName})`);
    // commit('SET_AVATAR',data);
  },

  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo;
    return new Promise((resolve, reject) => {
      // todo 登录接口
      userApi.login({ username: username.trim(), password }).then((response) => {
        const { data } = response;
        commit('SET_TOKEN', data.token);
        setToken(data.token);
        resolve();
      }).catch((error) => {
         reject(error);
       });
     });
   },

  // get user info
  // getInfo({ commit, state }) {
  //   return new Promise((resolve, reject) => {
  //     userApi.userInfo(state.token).then((response) => {
  //       const { data } = response;
  //
  //       if (!data) {
  //         reject('Verification failed, please Login again.');
  //       }
  //
  //       const {
  //         roles, name, avatar, introduction,
  //       } = data;
  //
  //       // roles must be a non-empty array
  //       if (!roles || roles.length <= 0) {
  //         reject('getInfo: roles must be a non-null array!');
  //       }
  //
  //       commit('SET_ROLES', roles);
  //       commit('SET_NAME', name);
  //       commit('SET_AVATAR', avatar);
  //       commit('SET_INTRODUCTION', introduction);
  //       resolve(data);
  //     }).catch((error) => {
  //       reject(error);
  //     });
  //   });
  // },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      api.userApi
        .logout(state.token)
        .then(() => {
          commit("SET_TOKEN", "");
          commit("SET_ROLES", []);
          removeToken();
          // resetRouter(); // todo 重置路由

          // reset visited views and cached views
          // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
          dispatch("tagsView/delAllViews", null, { root: true });

          resolve();
        })
        .catch((error) => {
          reject(error);
        });
    });
  },

  // // remove token
  // resetToken({ commit }) {
  //   return new Promise((resolve) => {
  //     commit('SET_TOKEN', '');
  //     commit('SET_ROLES', []);
  //     removeToken();
  //     resolve();
  //   });
  // },
  //
  // // dynamically modify permissions
  // changeRoles({ commit, dispatch }, role) {
  //   return new Promise(async (resolve) => {
  //     const token = role + '-token';
  //
  //     commit('SET_TOKEN', token);
  //     setToken(token);
  //
  //     const { roles } = await dispatch('getInfo');
  //
  //     resetRouter();
  //
  //     // generate accessible routes map based on roles
  //     const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true });
  //
  //     // dynamically add accessible routes
  //     router.addRoutes(accessRoutes);
  //
  //     // reset visited views and cached views
  //     dispatch('tagsView/delAllViews', null, { root: true });
  //
  //     resolve();
  //   });
  // },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
