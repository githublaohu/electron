<template>
  <div class="panel" :class="panelClass">
    <PanelHeading :title="title" :icon-class="iconClass"/>
    <div class="panel-body">
      <slot/>
    </div>
  </div>
</template>

<script>
import PanelHeading from './PanelHeading'
export default {
  name: 'Panel',
  components: {
    PanelHeading
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
      default: 'panel-default'
    },
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .panel{
    margin-bottom: 20px;
  }
  .panel-default{
    background-color: #fff;
    border: 1px solid #d8dce5;
    border-radius: 4px;
    -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
    box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
  }
  .panel-body {
    padding: 10px 0px;
  }
</style>
