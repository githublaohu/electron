<template>
  <div>
    <!-- 团队组织列表 -->
    <div className="bg-white mod mt-20">
      <div className="title">团队组织列表</div>
      <d2-crud v-bind="tableInfo" v-on.catch="tableInfo.events"/>
    </div>
  </div>
</template>
<script>
import apiOrganization from '@/api/my/organization'

export default {
  name: 'team',
  data() {
    return {
      // 团队列表
      tableInfo: {
        data: [],
        rowHandle: {
          fixed: 'right',
          width: '120px',
          custom: [{
            text: '查看详情',
            type: 'primary',
            size: 'mini',
            icon: 'el-icon-view',
            emit: 'view'
          }]
        },
        columns: [
          {
            title: "组织id",
            key: "oiId",
            minWidth: '60'
          },
          {
            title: "组织名",
            key: "oiName",
            minWidth: '100'
          },
          {
            title: "组织英文名",
            key: "oiEnglishName",
            minWidth: '100'
          },
          {
            title: "组织类型",
            key: "oiType",
            minWidth: '100'
          },
          {
            title: "组织说明",
            key: "oiExplain",
            minWidth: '150'
          },
          {
            title: "创建人名",
            key: "oiCreaterName",
            minWidth: '100'
          },
          {
            title: "拥有人名",
            key: "oiOwnerName",
            minWidth: '100'
          },
          {
            title: "创建时间",
            key: "createTime",
            minWidth: '180'
          },
          {
            title: "修改时间",
            key: "updateTime",
            minWidth: '180'
          }
        ],
        // event,表格组件回调的相关事件集
        events: {
          view: row => {
            // 点击查看组织详情
            this.$router.push({path: '/team/detail', query: row.row})
          }
        }
      }
    }
  },
  created() {
    this.getTeamList() // 获取个人团队列表
  },
  methods: {
    /**
     * 获取团队列表
     */
    getTeamList() {
      apiOrganization.querySubOrganization({ioSuperiorId: 2}).then(res => {
        this.tableInfo.data = res.data || []
      }).catch(error => {
        console.log('获取团队组织列表失败==>', error)
      })
    }
  }
}
</script>
