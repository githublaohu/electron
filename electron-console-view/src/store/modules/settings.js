import variables from "@/assets/styles/element-variables.scss";
import defaultSettings from "@/config/settings";

const {
  showSettings,
  tagsView,
  fixedHeader,
  sidebarLogo,
  logo,
  title,
  showAllSidebar,
} = defaultSettings;

const state = {
  theme: variables.theme,
  showSettings,
  tagsView,
  fixedHeader,
  sidebarLogo,
  showAllSidebar,
  logo,
  title,
};

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    if (Object.prototype.hasOwnProperty.call(state, key)) {
      state[key] = value;
    }
  },
};

const actions = {
  changeSetting({ commit }, data) {
    commit("CHANGE_SETTING", data);
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
