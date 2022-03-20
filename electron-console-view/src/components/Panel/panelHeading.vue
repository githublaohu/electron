<template>
  <div class="panel-heading" :class="panelClass">
    <SvgIcon v-if="iconClass" :class-name="iconClass"/>
    {{ title }}
    <div class="pull-right">
      <slot name="title-right"/>
    </div>
  </div>
</template>
<script>
import SvgIcon from '@/components/SvgIcon'
export default {
  name: 'PanelHeading',
  components: {
    SvgIcon
  },
  props: {
    title: {
      type: String,
      default: ''
    },
    iconClass: {
      type: String,
      default: ''
    },
    panelClass:{
      type: String,
      default: 'panel-heading-default'
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .panel-heading {
    padding: 8px 15px;
    .pull-right {
      float: right;
    }
    &.panel-heading-default{
      border-bottom: 1px solid transparent;
      border-top-left-radius: 3px;
      border-top-right-radius: 3px;
      color: #666;
      font-size: 14px;
      background-color: #f5f5f5;
    }
  }
</style>
