<script src="../../router/routerData.js"></script>
<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <sidebar class="sidebar-container" />

    <div class="main-container">
      <navbar />
      <tags-view />

      <div class="page-container flex">
        <template v-if="show">
          <div>这是我说的</div>
        </template>
        <div class="team-container">
<!--          <div class="title mb-20">{{ teamInfo.oiName }}</div>-->
          <div
            v-for="(item, index) in routeList"
            :key="index"
            class="item"
            :class="{ 'active': index === active}"
            @click="goToPath(index)"
          >
            <i v-if="item.meta && item.meta.icon" :class="item.icon" class="mr-20" />
            <span>{{ item.meta ? item.meta.title : '-' }}</span>
          </div>
        </div>

        <div class="flex-1" style="width: 0">
          <app-main />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Navbar, Sidebar, TagsView, AppMain } from './components'
import ResizeMixin from './mixin/ResizeHandler'

export default {
  name: 'Layout',
  components: {
    Navbar,
    Sidebar,
    TagsView,
    AppMain
  },
  mixins: [ResizeMixin],
  data() {
    return {
      active: 0
    }
  },
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    routes() {
      return this.$router.options.routes
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    },

    // 获取当前路由的子路由
    routeList() {
      let routes = {
        children: this.$router.options.routes
      }

      const route = this.$route.matched;

      for(let i=0; i<route.length-1; i++){
        routes = routes.children.find((e) => (e.name == route[i].name));
      }

      console.log('查看当前路由的children==>', routes.children)

      return routes.children
    }
  },
  created() {
    let routes = {
      children: this.$router.options.routes
    }

    const route = this.$route.matched;

    for(let i=0; i<route.length-1; i++){
      routes = routes.children.find((e) => (e.name == route[i].name));
    }

    return routes.children
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('CloseSideBar', { withoutAnimation: false })
    },

    /**
     * 点击跳转路径
     * @param index
     */
    goToPath(index) {
      this.active = index

      console.log('点击跳转的路由==>', this.$route, this.$route.path + '/' + this.routeList[index].path)

      this.$router.push({
        path: this.$route.path + '/' + this.routeList[index].path
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
@import "@/assets/styles/mixin.scss";
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
  &.mobile.openSidebar{
    position: fixed;
    top: 0;
  }
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

.page-container {
  .team-container {
    width: 200px;
    padding-top: 50px;
    font-size: 14px;
    text-align: center;
    background-color: #fafbfb;
    border-right: solid 1px #eee;

    .title {
      font-weight: bold;
      font-size: 18px;
      width: 200px;
    }

    .item {
      padding: 20px 0;
      cursor: pointer;

      &:hover {
        background-color: #e7e7e7;
      }
    }

    .active {
      background-color: #e7e7e7;
    }
  }
}
</style>
