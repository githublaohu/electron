/**
 * 业务无关框架UI配置
 */
export default {
  title: "系统演示",
  // https://front-xps-cdn.xsyx.xyz/2020/05/09/2118802622.png
  // https://front-xps-cdn.xsyx.xyz/2020/06/10/31707940.png
  // https://front-xps-cdn.xsyx.xyz/2020/06/10/667945020.png
  // https://front-xps-cdn.xsyx.xyz/2020/06/10/1577505233.png
  // https://front-xps-cdn.xsyx.xyz/2020/06/10/163837970.png
  logo: "https://front-xps-cdn.xsyx.xyz/2020/06/10/31707940.png",

  /**
   * @type {boolean} true | false
   * @description Whether show the settings right-panel
   */
  showSettings: true,

  /**
   * 是否显示layout tab页签
   * @type {boolean} true | false
   * @description Whether need tagsView
   */
  tagsView: true,

  /**
   * 悬浮layout 顶部
   * @type {boolean} true | false
   * @description Whether fix the header
   */
  fixedHeader: true,

  /**
   * 是否显示左侧菜单logo
   * @type {boolean} true | false
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: true,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: "production",
  /**
   * 是否显示左侧菜单所有（忽略菜单权限-开发环境异常情况使用）
   */
  showAllSidebar: true,
};
