<template>
  <div class="page-layout" :class="classObj">
    <div
      v-if="device === 'mobile' && sidebar.opened"
      class="drawer-bg"
      @click="handleClickOutside"
    ></div>
    <!--顶部-->
    <layout-header></layout-header>
    <!--侧边栏-->
    <layout-aside class="layout-aside-container"></layout-aside>
    <!-- 主体 -->
    <layout-main></layout-main>
    <!-- todo 右侧悬浮面板-->
  </div>
</template>

<script>
// 系统基础结构布局
import { mapState } from "vuex";
import layoutHeader from "./layout-header/index";
import layoutAside from "./layout-aside/index";
import layoutMain from "./layout-main";
import resizeMixin from "./mixin/resize-handler";

export default {
  name: "index",
  components: {
    layoutHeader,
    layoutAside,
    layoutMain,
  },
  data() {
    return {};
  },
  mixins: [resizeMixin],
  computed: {
    ...mapState({
      sidebar: (state) => state.app.sidebar,
      device: (state) => state.app.device,
      showSettings: (state) => state.settings.showSettings,
      needTagsView: (state) => state.settings.tagsView,
      fixedHeader: (state) => state.settings.fixedHeader,
    }),
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === "mobile",
        "layout-header-fixed": this.fixedHeader,
        "has-tags-view": this.needTagsView,
      };
    },
    // key() {
    //   return this.$route.name !== undefined ? this.$route.name + +new Date() : this.$route + +new Date()
    // }
  },
  mounted() {},
  methods: {
    handleClickOutside() {
      this.$store.dispatch("app/closeSideBar", { withoutAnimation: false });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/_mixins.scss";
.page-layout {
  @include clearfix;

  &.mobile.openSidebar {
    position: fixed;
    top: 0;
  }

  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }
}
</style>
