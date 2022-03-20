<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
	data() {
		return {}
	}
}
</script>

<style lang="scss">
  .el-popover {
    width: 80px;
    min-width: 0px;
    background: #000;
    color: #fff;

    display: flex;
    align-items: center;
    flex-direction: column;
    line-height: normal;
  }
  .el-popper[x-placement^=right] .popper__arrow:after {
    border-right-color: #000;
  }
</style>
