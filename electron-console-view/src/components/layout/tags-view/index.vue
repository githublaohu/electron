<template>
  <div id="tags-view" class="tags-view">
    <scroll-pane ref="scrollPane" class="tags-view-wrapper">
      <router-link
        v-for="tag in visitedViews"
        ref="tag"
        :key="tag.path"
        :class="isActive(tag) ? 'active' : ''"
        :to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }"
        tag="span"
        class="tags-view-item"
        @click.middle.native="!isAffix(tag) ? closeSelectedTag(tag) : ''"
        @contextmenu.prevent.native="openMenu(tag, $event)"
      >
        {{ tag.title }}
        <span
          v-if="!isAffix(tag) && visitedViews.length > 1"
          class="el-icon-close"
          @click.prevent.stop="closeSelectedTag(tag)"
        />
      </router-link>
    </scroll-pane>

    <ul
      v-show="visible"
      :style="{ left: left + 'px', top: top + 'px' }"
      class="contextmenu"
    >
      <li @click="refreshSelectedTag(selectedTag)">刷新</li>
      <li
        v-if="!isAffix(selectedTag) && visitedViews.length > 1"
        @click="closeSelectedTag(selectedTag)"
      >
        关闭
      </li>
      <li
        v-if="!isAffix(selectedTag) && visitedViews.length > 1"
        @click="closeOthersTags"
      >
        关闭其他
      </li>
      <li
        v-if="!isAffix(selectedTag) && visitedViews.length > 1"
        @click="closeAllTags(selectedTag)"
      >
        关闭所有
      </li>
    </ul>
  </div>
</template>

<script>
import path from "path";
import ScrollPane from "./ScrollPane";

export default {
  name: "tagsView",
  components: { ScrollPane },
  data() {
    return {
      visible: false,
      top: 0,
      left: 0,
      selectedTag: {},
      affixTags: [],
    };
  },
  computed: {
    visitedViews() {
      return this.$store.state.tagsView.visitedViews;
    },
    routes() {
      return this.$store.state.permission.routes;
    },
  },
  watch: {
    $route() {
      this.addTags();
      this.moveToCurrentTag();
    },
    visible(value) {
      if (value) {
        document.body.addEventListener("click", this.closeMenu);
      } else {
        document.body.removeEventListener("click", this.closeMenu);
      }
    },
  },
  mounted() {
    this.initTags();
    this.addTags();
  },
  methods: {
    isActive(route) {
      return route.path === this.$route.path;
    },
    isAffix(tag) {
      return tag.meta && tag.meta.affix;
    },
    filterAffixTags(routes, basePath = "/") {
      let tags = [];
      routes.forEach((route) => {
        if (route.meta && route.meta.affix) {
          const tagPath = path.resolve(basePath, route.path);
          tags.push({
            fullPath: tagPath,
            path: tagPath,
            name: route.name,
            meta: { ...route.meta },
          });
        }
        if (route.children) {
          const tempTags = this.filterAffixTags(route.children, route.path);
          if (tempTags.length >= 1) {
            tags = [...tags, ...tempTags];
          }
        }
      });
      return tags;
    },
    initTags() {
      this.affixTags = this.filterAffixTags(this.routes);
      for (const tag of this.affixTags) {
        // Must have tag name
        if (tag.name) {
          this.$store.dispatch("tagsView/addVisitedView", tag);
        }
      }
    },
    addTags() {
      const { name } = this.$route;
      if (name) {
        this.$store.dispatch("tagsView/addView", this.$route);
      }
      return false;
    },
    moveToCurrentTag() {
      const tags = this.$refs.tag;
      this.$nextTick(() => {
        for (const tag of tags) {
          if (tag.to.path === this.$route.path) {
            this.$refs.scrollPane.moveToTarget(tag);
            // when query is different then update
            if (tag.to.fullPath !== this.$route.fullPath) {
              this.$store.dispatch("tagsView/updateVisitedView", this.$route);
            }
            break;
          }
        }
      });
    },
    refreshSelectedTag(view) {
      this.$store.dispatch("tagsView/delCachedView", view).then(() => {
        const { fullPath } = view;
        this.$nextTick(() => {
          this.$router.replace({
            path: "/redirect" + fullPath,
          });
        });
      });
    },
    closeSelectedTag(view) {
      this.$store
        .dispatch("tagsView/delView", view)
        .then(({ visitedViews }) => {
          if (this.isActive(view)) {
            this.toLastView(visitedViews, view);
          }
        });
    },
    closeOthersTags() {
      this.$router.push(this.selectedTag);
      this.$store
        .dispatch("tagsView/delOthersViews", this.selectedTag)
        .then(() => {
          this.moveToCurrentTag();
        });
    },
    closeAllTags(view) {
      this.$store.dispatch("tagsView/delAllViews").then(({ visitedViews }) => {
        if (this.affixTags.some((tag) => tag.path === view.path)) {
          return;
        }
        this.toLastView(visitedViews, view);
      });
    },
    toLastView(visitedViews, view) {
      const latestView = visitedViews.slice(-1)[0];
      if (latestView) {
        this.$router.push(latestView.fullPath);
      } else if (view.name === "Dashboard") {
        this.$router.replace({ path: "/redirect" + view.fullPath });
      } else {
        this.$router.push("/");
      }
    },
    openMenu(tag, e) {
      const menuMinWidth = 80;
      const menuMinHeight = 32;

      const offsetLeft = this.$el.getBoundingClientRect().left; // container margin left
      const { offsetWidth } = this.$el; // container width
      const maxLeft = offsetWidth - menuMinWidth; // left boundary
      const left = e.clientX - offsetLeft; // 15: margin right

      if (left > maxLeft) {
        this.left = maxLeft;
      } else {
        this.left = left;
      }

      // this.top = e.clientY
      this.top = menuMinHeight / 2;
      this.visible = true;
      this.selectedTag = tag;
    },
    closeMenu() {
      this.visible = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.tags-view {
  height: 32px;
  width: 100%;
  position: relative;
  margin-top: -1px;
  .tags-view-wrapper {
    .tags-view-item {
      position: relative;
      margin-left: -1px;
      margin-bottom: -1px;
      display: inline-block;
      padding: 0 30px 0 20px;
      color: #4d4d4d;
      font-size: 14px;
      border: 1px solid #eaecef;
      cursor: pointer;
      -webkit-user-select: none;
      -moz-user-select: none;
      -ms-user-select: none;
      user-select: none;
      -webkit-transition: all 0.3s ease-in-out;
      transition: all 0.3s ease-in-out;
      -webkit-transform-origin: left bottom;
      transform-origin: left bottom;
      vertical-align: middle;
      background: #fff;
      height: 32px;
      line-height: 32px;

      &:hover {
        background: #1890ff;
        border: 1px solid #1890ff;
        color: #fff;
        opacity: 0.8;
        //background: #2b2f3a;
      }

      &.active {
        background: #1890ff;
        border: 1px solid #1890ff;
        /*background: #2b2f3a;*/
        color: #fff;
        &:hover {
          opacity: 0.8;
        }
      }

      //reset element css of el-icon-close
      /deep/ .el-icon-close {
        width: 14px;
        height: 14px;
        line-height: 10px;
        position: absolute;
        right: 5px;
        top: 9px;
        border-radius: 50%;
        text-align: center;
        transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
        transform-origin: 100% 50%;

        &:before {
          transform: scale(0.6);
          display: inline-block;
          vertical-align: -3px;
        }

        &:hover {
          background-color: #b4bccc;
          color: #fff;
        }
      }
    }
  }

  .contextmenu {
    margin: 0;
    background: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    /*box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, .3);*/

    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;

      &:hover {
        background: #eee;
      }
    }
  }
}
</style>
