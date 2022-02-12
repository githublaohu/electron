<template>
  <div id="app">
    <router-view />
    <!--   <transition name="fade" mode="out-in">-->
    <!--     <router-view></router-view>-->
    <!--   </transition>-->
  </div>
</template>

<script>
export default {
  name: "App",
  components: {},
  data() {
    return {};
  },
  computed: {},
  created() {},
  mounted() {
    console.log("app mounted href:", window.location.href);
  },
  methods: {},
};
</script>
