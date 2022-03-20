<template>
  <el-drawer title="添加组织成员" direction="rtl" :visible.sync="show" :size="1000">
    <div class="app-container">
      <div>
        <!-- 筛选条件 -->
        <filter-form :settings="filterForm" v-on="filterForm.events" />

        <div class="bg-white mod mt-20">
          <div class="title">组织成员列表</div>
          <d2-crud v-bind="tableInfo" v-on="tableInfo.events" />
        </div>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import FilterForm from '@/components/FilterForm/index'
import apiUser from '@/api/user'
export default {
  name: 'Profile',
  components: {
    FilterForm
  },
  data() {
    return {
      show: false, // 是否打开抽屉
      // 筛选框配置
      filterForm: {
        items: [{
          props: 'uiName',
          type: 'input',
          label: '用户名称',
          defaultValue: '', // 默认选中的值
          attrs: {
            placeholder: '请输入用户名称'
          }
        }],

        // 回调的事件
        events: {
          reset: info => {
            this.getUserList()
            // Todo: 后续操作
          },
          confirm: info => {
            this.getUserList(info.uiName)
            // Todo: 后续操作
          },
          change: info => {
            this.getUserList(info.uiName)
            // Todo: 后续操作
          }
        }
      },

      // 表格详情
      tableInfo: {
        data: [],
        // pagination: {
        //   currentPage: 1,
        //   pageSize: 10,
        //   total: 0
        // },
        rowHandle: {
          fixed: 'right',
          width: '100px',
          custom: [{
            text: '添加',
            type: 'primary',
            icon: 'el-icon-add',
            size: 'mini',
            emit: 'add'
          }]
        },
        columns: [{
          title: '用户名',
          key: 'uiName',
          minWidth: '60',
        }, {
          title: '性别',
          key: 'uiSex',
          minWidth: '50',
          align: 'center'
        }, {
          title: '电话号码',
          key: 'uiPhone',
          minWidth: '100',
          align: 'center',
          formatter: row => {
            return row.uiPhone || '-'
          }
        }, {
          title: '邮箱',
          key: 'uiEmail',
          minWidth: '200',
          align: 'center',
          formatter: row => {
            return row.uiPhone || '-'
          }
        }],
        // event,表格组件回调的相关事件集
        events: {
          // 点击添加按钮
          add: row => {
            this.$emit('addUser', row.row)
            this.show = false
          }
        }
      }
    }
  },
  created() {
    this.getUserList()
  },
  methods: {
    /**
     * 初始化
     */
    handleCreate() {
      this.show = true
    },

    /**
     * 获取用户列表
     */
    getUserList(uiName = '') {
      apiUser.getUserList({ uiName }).then(res => {
        console.log('获取用户列表成功==>', res)
        this.tableInfo.data = res.data
      }).catch(error => {
        console.log('获取用户列表失败==>', error)
      })
    }
  }
}
</script>
