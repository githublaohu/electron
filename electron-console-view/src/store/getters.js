const getters = {
  sidebar: (state) => state.app.sidebar,
  size: (state) => state.app.size,
  device: (state) => state.app.device,
  visitedViews: (state) => state.tagsView.visitedViews,
  cachedViews: (state) => state.tagsView.cachedViews,
  token: (state) => state.user.token,
  avatar: (state) => state.user.avatar,
  userName: (state) => state.user.name,
  introduction: (state) => state.user.introduction,
  roles: (state) => state.user.roles,
  permission_routes: (state) => state.permission.routes, // 所有路由
  permission_asideMenu: (state) => state.permission.asideMenu, // 菜单路由
  errorLogs: (state) => state.errorLog.logs,
  oidcInfo: (state) => state.user.oidcInfo,
  oidcConfig: (state) => state.user.oidcConfig,
  showAllSidebar: (state) => state.settings.showAllSidebar,
};
export default getters;
