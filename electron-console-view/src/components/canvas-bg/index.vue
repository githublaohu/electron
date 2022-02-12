<template>
  <div class="canvas-bg" :style="bgStyle">
    <canvas ref="canvas" height="100%" width="100%" id="canvas"></canvas>
  </div>
</template>

<script>
import technology from "./technology";
import ParticleLine from "./particle-line";
import SideText from "./side-text";

const technologyKey = "technology";
const particleKey = "particle";
const sideTextKey = "sideText";

export default {
  name: "canvasBg",
  components: {},
  props: {
    // 背景类型
    bgType: {
      type: String,
      default: particleKey, // technology sideText
    },
    // 背景 css style
    bgStyle: {
      type: Object,
      default() {
        return {
          opacity: 0.5,
        };
      },
    },
    particleConfig: {
      type: Object,
      default() {
        return {
          lineWidth: 0.3,
          dotsNumber: 100,
          dotsDistance: 100,
          hoverEffect: true,
        };
      },
    },
    sideTextConfig: {
      type: Object,
      default() {
        return {
          text: "兴盛优选",
        };
      },
    },
  },
  data() {
    return {};
  },
  computed: {},
  mounted() {
    switch (this.bgType) {
      case technologyKey:
        technology.init(this.$refs.canvas);
        technology.onResize();
        break;
      case particleKey:
        /* eslint-disable no-new */
        new ParticleLine("canvas", this.particleConfig);
        break;
      case sideTextKey:
        SideText.init(this.$refs.canvas, this.sideTextConfig.text);
        SideText.onResize();
        break;
      default:
    }
  },
  beforeDestroy() {
    if (this.bgType === technologyKey) {
      technology.unOnResize();
    }
    if (this.bgType === sideTextKey) {
      SideText.unOnResize();
    }
  },
  destroyed() {},
  methods: {},
};
</script>

<style lang="scss" scoped>
.canvas-bg {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 0;
}
</style>
