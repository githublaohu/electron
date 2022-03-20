<template>
  <div class="page-container flex">
    <div class="team-container">
      <div class="title mb-20">{{ teamInfo.oiName }}</div>
      <div v-for="(item, index) in items" :key="index" class="item"
           :class="{ 'active': item.key === curComponent}" @click="curComponent = item.key">
        <i :class="item.icon" class="mr-20" />
        <span>{{ item.name }}</span>
      </div>
    </div>

    <div class="flex-1" style="width: 0">
      <component :is="curComponent" :info="teamInfo" />
    </div>
  </div>
</template>
<script>
export default {
  name: 'layout',
  components: {
    organization: () => import('./organization/index'),
    member: () => import('./member/index')
  },
  data() {
    return {
      curComponent: 'organization', // 当前生效的组件
      teamInfo: null, // 团队信息
      items: [{
        name: '项目组织',
        icon: 'el-icon-s-data',
        key: 'organization',
        click: info => {
          this.curComponent = 'organization'
        }
      },
        {
          name: '组织成员',
          icon: 'el-icon-user-solid',
          key: 'member',
          click: () => {
            this.curComponent = 'member'
          }
        }
      ]
    }
  },
  created() {
    this.teamInfo = this.$route.query
    console.log('传过来的组织信息==>', this.teamInfo)
  }
}
</script>
<style lang="scss" scoped>
.page-container {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  overflow: hidden;

  .team-container {
    width: 200px;
    padding-top: 50px;
    font-size: 14px;
    text-align: center;
    background-color: #fafbfb;
    border-right: solid 1px #eee;

    .title {
      font-weight: bold;
      font-size: 18px;
      width: 200px;
    }

    .item {
      padding: 20px 0;
      cursor: pointer;

      &:hover {
        background-color: #e7e7e7;
      }
    }

    .active {
      background-color: #e7e7e7;
    }
  }
}
</style>
