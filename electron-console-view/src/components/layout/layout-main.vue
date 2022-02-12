<template>
  <section class="layout-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key"></router-view>
    </transition>
  </section>
</template>

<script>
export default {
  name: "layout-main",
  components: {},
  data() {
    return {};
  },
  computed: {
    key() {
      return this.$route.name !== undefined
        ? this.$route.name + +new Date()
        : this.$route + +new Date();
    },
    // iframe所有页面
    iframeView() {
      return this.lookIframe(this.$router.options.routes);
    },
  },
  mounted() {},
  methods: {
    lookIframe(routes) {
      const iframeArr = [];

      function findIframePath(routesArr) {
        routesArr.filter((item) => {
          const isIframePage = item.meta && item.meta.iframe;
          const hasChild = item.children
            ? item.children && item.children.length > 0
            : false;
          if (isIframePage) {
            iframeArr.push(item);
            return isIframePage;
          }
          if (!hasChild) {
            return false;
          } else {
            return findIframePath(item.children);
          }
        });
      }

      findIframePath(routes);
      return iframeArr;
    },
  },
};
</script>

<style lang="scss" scoped></style>
