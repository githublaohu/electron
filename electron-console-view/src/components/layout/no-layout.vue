<template>
  <transition name="fade" mode="out-in">
    <keep-alive :include="cachedViews">
      <router-view :key="key" />
    </keep-alive>
  </transition>
</template>

<script>
export default {
  name: "NoLayout",
  computed: {
    key() {
      return this.$route.fullPath;
    },
    // 手动缓存路由组件
    cachedViews() {
      return [];
    },
  },
  mounted() {},
};
</script>

<style scoped></style>
