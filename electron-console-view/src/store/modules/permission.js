/**
 * todo 权限控制：角色信息匹配路由
 */
const asyncRoutes = [];
const constantRoutes = [];
/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some((role) => route.meta.roles.includes(role));
  } else {
    return true;
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = [];

  routes.forEach((route) => {
    const tmp = { ...route };
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles);
      }
      res.push(tmp);
    }
  });

  return res;
}

const state = {
  asideMenu: null, // [] 后台返回侧边菜单数据
  routes: [],
  addRoutes: [],
};

// todo routes 与 addRoutes 区别
const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes;
    state.routes = constantRoutes.concat(routes);
  },
  INIT_ROUTES: (state, routes) => {
    // 这一部分放拦截器比较合适
    state.addRoutes = routes;
    state.routes = routes;
  },
  SET_ASIDE_MENU: (state, asideMenu) => {
    state.asideMenu = asideMenu;
  },
};

const actions = {
  // 角色获取对应的路由菜单
  generateRoutes({ commit }, roles) {
    return new Promise((resolve) => {
      let accessedRoutes;
      if (roles.includes("admin")) {
        accessedRoutes = asyncRoutes || [];
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles);
      }
      commit("SET_ROUTES", accessedRoutes);
      resolve(accessedRoutes);
    });
  },
  // 初始化路由菜单数据
  $_initRoutes({ commit }, routes) {
    commit("INIT_ROUTES", routes);
  },
  $_asideMenu({ commit }, asideMenu) {
    commit("SET_ASIDE_MENU", asideMenu);
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
