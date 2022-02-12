<template>
  <div class="layout-aside">
    <div class="brand-logo" v-if="showLogo">
      <logo :collapse="isCollapse" />
    </div>
    <el-scrollbar wrap-class="scrollbar-wrapper layout-aside-scroll">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :active-text-color="variables.menuActiveText"
        :unique-opened="false"
        :collapse-transition="false"
        mode="vertical"
      >
        <sidebar-item
          v-show="showAllSidebar || route.$paired"
          v-for="route in permission_asideMenu"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import variables from "@/assets/styles/_var.scss";
import sidebarItem from "./sidebar-item";
import logo from "./logo";

export default {
  name: "layout-aside",
  components: {
    sidebarItem,
    logo,
  },
  data() {
    return {};
  },
  // 左侧边栏所渲染的只是store里面的，不是真的路由信息
  computed: {
    ...mapGetters([
      "permission_routes",
      "permission_asideMenu",
      "sidebar",
      "showAllSidebar",
    ]),
    showLogo() {
      return this.$store.state.settings.sidebarLogo;
    },
    activeMenu() {
      const route = this.$route;
      const { meta, path } = route;
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    },
    variables() {
      return variables;
    },
    isCollapse() {
      return !this.sidebar.opened;
    },
  },
  mounted() {
    // console.warn(this.permission_asideMenu)
  },
  methods: {},
};
</script>

<style lang="scss" scoped></style>
