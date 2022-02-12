<template>
  <div class="layout-header">
    <div class="header-bar">
      <div
        class="aside-nav-switch"
        :class="{ 'is-active': sidebar.opened }"
        @click="toggleSideBar"
      >
        <i class="el-icon-s-fold"></i>
      </div>

      <nav-crumb class="g-nav-crumb" />

      <!--右侧菜单信息-->
      <div class="right-menu">
        <template v-if="device !== 'mobile'">
          <header-search id="header-search" class="right-menu-item" />

          <error-log class="errLog-container right-menu-item hover-effect" />

          <screen-full id="screenfull" class="right-menu-item hover-effect" />

          <!--          <el-tooltip content="Global Size" effect="dark" placement="bottom">-->
          <!--            <size-select id="size-select" class="right-menu-item hover-effect" />-->
          <!--          </el-tooltip>-->
        </template>

        <el-dropdown
          class="avatar-container right-menu-item hover-effect"
          trigger="click"
        >
          <div class="avatar-wrapper">
            <img
              :src="avatar + '?imageView2/1/w/80/h/80'"
              class="user-avatar"
              v-if="avatar"
            />
            <i class="el-icon-caret-bottom" />
          </div>

          <el-dropdown-menu slot="dropdown">
            <router-link to="/">
              <el-dropdown-item>首页</el-dropdown-item>
            </router-link>
            <el-dropdown-item v-if="userName">
              <span style="display: block;">{{ userName }}</span>
            </el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">
              <span style="display: block;">安全退出</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
    <!--页签-->
    <div class="layout-tags-view" v-if="showTagsView">
      <tags-view />
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import navCrumb from "./nav-crumb";
import tagsView from "../tags-view/index";
import headerSearch from "./header-search";
import errorLog from "./error-log";
import screenFull from "./screen-full";
// import sizeSelect from './size-select';

export default {
  name: "layout-header",
  components: {
    navCrumb,
    tagsView,
    headerSearch,
    errorLog,
    screenFull,
    // sizeSelect,
  },
  data() {
    return {};
  },
  computed: {
    ...mapGetters(["sidebar", "avatar", "device", "userName"]),
    showTagsView() {
      return this.$store.state.settings.tagsView;
    },
  },
  mounted() {},
  methods: {
    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    async logout() {
      // todo 退出登陆
      await this.$store.dispatch("user/loginOutOidc");
    },
  },
};
</script>

<style lang="scss" scoped></style>
