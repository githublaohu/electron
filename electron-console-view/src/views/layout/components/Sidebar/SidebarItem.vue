<template>
  <div v-if="!item.hidden" class="menu-wrapper">
    <template v-if="hasOneShowingChild(item.children,item) && (!onlyOneChild.children||onlyOneChild.noShowingChildren)&&!item.alwaysShow">
      <el-tooltip class="item" effect="dark" :content="onlyOneChild.meta && onlyOneChild.meta.title ? onlyOneChild.meta.title : ''" placement="right">
        <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
          <item :icon="onlyOneChild.meta.icon||(item.meta&&item.meta.icon)" :title="onlyOneChild.meta && onlyOneChild.meta.title ? onlyOneChild.meta.title : ''" />
        </app-link>
      </el-tooltip>
    </template>

    <template v-else>
      <el-popover placement="right" trigger="hover" popper-class="popover" >
        <div slot="reference" class="sub-item">
          <template>
            <item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="item.meta.title || ''" />
          </template>
        </div>
        <sidebar-item
          v-for="child in item.children"
          :key="child.path"
          :is-nest="true"
          :item="child"
          :base-path="resolvePath(child.path)"
          class="nest-menu"
        />
      </el-popover>
    </template>

  </div>
</template>

<script>
import path from 'path'
import { isExternal } from '@/common/utils'
import Item from './Item'
import SubItem from './subItem'
import AppLink from './Link'

export default {
  name: 'SidebarItem',
  components: { Item, AppLink, SubItem },
  props: {
    // route object
    item: {
      type: Object,
      required: true
    },
    isNest: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      onlyOneChild: null,

      show: false
    }
  },
  methods: {
    hasOneShowingChild(children = [], parent) {
      const showingChildren = children.filter(item => {
        if (item.hidden) {
          return false
        } else {
          // Temp set(will be used if only has one showing child)
          this.onlyOneChild = item
          return true
        }
      })

      // When there is only one child router, the child router is displayed by default
      if (showingChildren.length === 1) {
        return true
      }

      // Show parent if there are no child router to display
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ... parent, path: '', noShowingChildren: true }
        return true
      }

      return false
    },
    resolvePath(routePath) {
      if (this.isExternalLink(routePath)) {
        return routePath
      }
      return path.resolve(this.basePath, routePath)
    },
    isExternalLink(routePath) {
      return isExternal(routePath)
    }
  }
}
</script>
<style lang="scss" scoped>
  .sub-item {
    width: 100%;
    padding: 10px 0;
    display: inline-flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: white;
    height: 80px;
  }

  ::v-deep .el-popover {
    width: 80px;
  }
</style>
