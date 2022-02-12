<template>
  <he-page class="icons-page">
    <el-tabs type="border-card">
      <el-tab-pane label="Element-UI Icons">
        <div class="grid">
          <div
            v-for="item of elementIcons"
            :key="item"
            @click="handleClipboard(generateElementIconCode(item), $event)"
          >
            <el-tooltip placement="top">
              <div slot="content">
                {{ generateElementIconCode(item) }}
              </div>
              <div class="icon-item">
                <i :class="'el-icon-' + item" />
                <span>{{ item }}</span>
              </div>
            </el-tooltip>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="he-icons">
        <div class="grid">
          <div
            v-for="item of xIcons"
            :key="item"
            @click="handleClipboard(generateIconCode(item), $event)"
          >
            <el-tooltip placement="top">
              <div slot="content">
                {{ generateIconCode(item) }}
              </div>
              <div class="icon-item">
                <he-iconfont
                  :icon-class="'he-icon-' + item"
                  class-name="disabled"
                />
                <span>{{ item }}</span>
              </div>
            </el-tooltip>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </he-page>
</template>

<script>
import clipboards from "@/common/plugin/clipboard";
import elIcons from "./el-icons";
import xIcons from "./he-icons";

export default {
  data() {
    return {
      elementIcons: elIcons,
      xIcons,
    };
  },
  computed: {},
  mounted() {},
  methods: {
    generateIconCode(symbol) {
      return `<he-iconfont icon-class="he-icon-${symbol}" />`;
    },
    generateElementIconCode(symbol) {
      return `<i class="el-icon-${symbol}" />`;
    },
    handleClipboard(text, event) {
      clipboards(text, event);
    },
  },
};
</script>

<style lang="scss" scoped>
.icons-page {
  .grid {
    position: relative;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  }

  .icon-item {
    margin: 20px;
    height: 85px;
    text-align: center;
    width: 100px;
    float: left;
    font-size: 30px;
    color: #24292e;
    cursor: pointer;
  }

  span {
    display: block;
    font-size: 16px;
    margin-top: 10px;
  }

  .disabled {
    pointer-events: none;
  }
}
</style>
