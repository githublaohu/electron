<template>
  <div class="app-container">
    <div>
      <!-- 筛选条件 -->
      <filter-form :settings="filterForm" v-on="filterForm.events" />

      <!-- 表格详情 -->
      <!-- <el-button type="primary" size="small" icon="el-icon-plus"
				@click="() => { $refs.simplePopup.handleCreate() }" class="mt-20">新增</el-button> -->
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        class="mt-20"
        @click="handleBindMember"
      >新增</el-button>

      <div class="bg-white mod mt-20">
        <div class="title">组织成员列表</div>
        <d2-crud v-bind="tableInfo" v-on="tableInfo.events" />
      </div>
    </div>

    <!-- 表单弹出框 -->
    <simple-popup ref="simplePopup" :settings="popupInfo" v-on="popupInfo.events" />

    <!-- 修改表单弹出框 -->
    <simple-popup
      v-if="editPopupInfo"
      ref="editSimplePopup"
      :settings="editPopupInfo"
      v-on="editPopupInfo.events"
    />

    <!--   添加成员弹出框-->
    <search-user-list ref="addUserList" @addUser="addUser" />
  </div>
</template>

<script>
import SimplePopup from '@/components/SimplePopup/index'
import FilterForm from '@/components/FilterForm/index'
import apiOrganization from '@/api/my/organization'
import SearchUserList from './components/search-user-list'
export default {
  name: 'Profile',
  components: {
    SimplePopup,
    FilterForm,
    SearchUserList
  },
  props: {
    /**
			 * 团队组织信息
			 */
    info: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      teamInfo: null, // 团队信息
      // 筛选框配置
      filterForm: {
        items: [{
          props: 'oiSuperiorId',
          type: 'input',
          label: '成员姓名',
          defaultValue: '', // 默认选中的值
          attrs: {
            placeholder: '请输入成员姓名'
          }
        }],

        // 回调的事件
        events: {
          reset: info => {
            console.log('重置选项==>', info)
            // Todo: 后续操作
          },
          confirm: info => {
            console.log('查找参数==>', info)
            // Todo: 后续操作
          },
          change: info => {
            console.log('表单值变化==>', info)
            // Todo: 后续操作
          }
        }
      },

      // 表格详情
      tableInfo: {
        data: [],
        pagination: {
          currentPage: 1,
          pageSize: 10,
          total: 0
        },
        rowHandle: {
          fixed: 'right',
          width: '200px',
          custom: [{
            text: '修改权限',
            type: 'warning',
            size: 'mini',
            icon: 'el-icon-edit',
            emit: 'modify'
          }, {
            text: '删除',
            type: 'danger',
            icon: 'el-icon-delete',
            size: 'mini',
            emit: 'delete'
          }]
        },
        columns: [{
          title: '组织名',
          key: 'oiName',
          minWidth: '100'
        },
        {
          title: '组织英文名',
          key: 'oiEnglishName',
          minWidth: '100'
        },
        {
          title: '组织类型',
          key: 'oiType',
          minWidth: '100'
        },
        {
          title: '组织说明',
          key: 'oiExplain',
          minminWidth: '120'
        },
        {
          title: '创建人名',
          key: 'oiCreaterName',
          minWidth: '100'
        },
        {
          title: '拥有人名',
          key: 'oiOwnerName',
          minWidth: '100'
        },
        {
          title: '创建时间',
          key: 'createTime',
          minWidth: '180'
        },
        {
          title: '修改时间',
          key: 'updateTime',
          minWidth: '180'
        }
        ],
        // event,表格组件回调的相关事件集
        events: {
          rowClick: row => {
            console.log('点击了表格行数据==>', row)
          },
          modify: row => {
            console.log('点击了修改按钮==>', row)
            this.$refs.editSimplePopup.handleCreate(row.row)
          },
          delete: row => {
            // console.log('点击了删除按钮==>', row)
            this.$confirm('确定移出该成员？', '操作确认', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              apiOrganization.deleteOrganizationMember({

              })
              this.$message.success('删除成功')
            }).catch(() => {
              this.$message.error('选择取消')
            })
          }
        }
      },

      // 弹出框详情
      popupInfo: {
        drawer: {
          // 配置项
          attrs: {
            title: '添加',
            direction: 'rtl'
          },
          events: {
            beforeClose: e => {
              console.log('关闭弹框')
            }
          }
        },
        form: {
          width: '100px',
          items: [{
            props: 'oiSuperiorId',
            type: 'input',
            label: '组织id',
            defaultValue: '21212121', // 初始值
            attrs: {
              placeholder: '请填写组织id'
            },
            rules: [{
              required: true,
              message: '请输入组织id',
              trigger: 'blur'
            }]
          },
          {
            props: 'oiName',
            type: 'input',
            label: '组织名',
            defaultValue: '', // 初始值
            attrs: {
              placeholder: '请填写组织名称'
            },
            rules: [{
              required: true,
              message: '请输入组织名称',
              trigger: 'blur'
            }]
          },
          {
            props: 'oiEnglishName',
            type: 'input',
            label: '组织英文名',
            defaultValue: '', // 初始值
            attrs: {
              placeholder: '请填写组织英文名'
            },
            rules: [{
              required: true,
              message: '请输入组织英文名',
              trigger: 'blur'
            }]
          },
          {
            props: 'oiType',
            type: 'input',
            label: '组织类型',
            defaultValue: '', // 初始值
            attrs: {
              placeholder: '请填写组织类型'
            },
            rules: [{
              required: true,
              message: '请输入组织类型',
              trigger: 'blur'
            }]
          },
          {
            props: 'oiExplain',
            type: 'input',
            label: '组织说明',
            defaultValue: '', // 初始值
            attrs: {
              placeholder: '请填写组织说明'
            },
            rules: [{
              required: true,
              message: '请输入组织说明',
              trigger: 'blur'
            }]
          }
          ]
        },

        events: {
          confirm: data => {
            console.log('点击确定按钮==>', data)
          },
          cancel: () => {
            this.$refs.simplePopup.handleClose()
            console.log('点击取消按钮')
          }
        }
      },

      editPopupInfo: null, // 修改表单设置

      // 动作绑定弹出框
      acitonBindInfo: {
        drawer: {
          // 配置项
          attrs: {
            title: '绑定动作',
            direction: 'rtl'
          },
          events: {
            beforeClose: e => {
              console.log('关闭弹框')
            }
          }
        },

        events: {
          confirm: data => {
            console.log('点击确定按钮==>', data)
          },
          cancel: () => {
            this.$refs.simplePopup.handleClose()
            console.log('点击取消按钮')
          }
        }
      }
    }
  },
  created() {
    this.getTeamMemberList() // 获取团队成员列表
  },
  methods: {
    /**
			 * 根据团队id获取团队成员列表
			 */
    getTeamMemberList() {
      if (!this.info) {
        console.error('没有传进来正确的团队id')
        return
      }
      apiOrganization.queryOrganizationMember({
        organizationId: this.info.oiId
      }).then(res => {
        console.log('查询到的组织下的成员列表==>', res)
        this.tableInfo.data = res.data || []
      }).catch(error => {
        console.log('查询下属组织失败==>', error)
      })
    },

    /**
			 * 点击添加成员
			 */
    handleBindMember() {
      this.$refs.addUserList.handleCreate()
    },

    /**
     * 绑定团队成员
     * @param {Object} userInfo
     */
    addUser(userInfo) {
      console.log('要绑定的成员信息==>', userInfo)

      apiOrganization.addOrganizationMember({
        organizationId: this.info.oiId,
        uiId: userInfo.uiId,
        uiName: userInfo.uiName
      }).then(res => {
        this.$message.success('添加成功')
        this.getTeamMemberList() // 重新刷新列表
      }).catch(error => {
        console.log('添加成员失败==>', error)
        this.$message.error('绑定成员失败')
      })
    }
  }
}
</script>
