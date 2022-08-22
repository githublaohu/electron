<template>
  <div class="app-container">
    <div>
      <!-- 筛选条件 -->
      <!--      <filter-form :settings="filterForm" v-on="filterForm.events" />-->

      <!-- 表格详情 -->
      <el-button type="primary" size="small" icon="el-icon-plus" class="mt-20" @click="() => { $refs.simplePopup.handleCreate() }">新增</el-button>
      <div class="bg-white mod mt-20">
        <div class="title">项目组织列表</div>
        <d2-crud v-bind="tableInfo" v-on="tableInfo.events" />
      </div>
    </div>

    <!-- 表单弹出框 -->
    <simple-popup ref="simplePopup" :settings="popupInfo" v-on="popupInfo.events" />
  </div>
</template>

<script>
import SimplePopup from '@/components/SimplePopup/index'
import apiOrganization from '@/api/my/organization'
export default {
  name: 'Profile',
  components: {
    SimplePopup
  },
  props: {
    /**
			 * 团队组织信息
			 */
    info: {
      type: Object,
      default: null
    },
    view: {
      type: String,
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
          label: '组织名称',
          defaultValue: '', // 默认选中的值
          attrs: {
            placeholder: '请输入组织名称查找'
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
        // rowHandle: {
        // 	fixed: 'right',
        // 	width: '200px',
        // 	// custom: [{
        // 	// 	text: '修改',
        // 	// 	type: 'warning',
        // 	// 	size: 'mini',
        // 	// 	icon: 'el-icon-edit',
        // 	// 	emit: 'modify'
        // 	// }, {
        // 	// 	text: '删除',
        // 	// 	type: 'danger',
        // 	// 	icon: 'el-icon-delete',
        // 	// 	size: 'mini',
        // 	// 	emit: 'delete'
        // 	// }]
        // },
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
            this.$confirm('确定删除该组织？', '操作确认', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              // Todo: 事件操作
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
            label: '所属团队id',
            defaultValue: this.info.oiId, // 初始值
            attrs: {
              placeholder: '请填写项目组织id',
              disabled: true
            },
            rules: [{
              required: true,
              message: '请填写项目组织id',
              trigger: 'blur'
            }]
          },
          {
            props: 'oiName',
            type: 'input',
            label: '组织名称',
            defaultValue: '', // 初始值
            attrs: {
              placeholder: '请填写项目组织名称'
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
              placeholder: '请填写项目组织英文名称'
            },
            rules: [{
              required: true,
              message: '请填写项目组织英文名称',
              trigger: 'blur'
            }]
          },
          {
            props: 'oiType',
            type: 'select',
            label: '组织类型',
            defaultValue: 'APPLICATION', // 初始值
            attrs: {
              placeholder: '请选择项目组织类型',
              disabled: true
            },
            options: [{
              label: '测试',
              value: 'CODE_EXAMPLE'
            }, {
              label: '系统默认',
              value: 'SYSTEM_DEFAULT'
            }, {
              label: '默认',
              value: 'DEFAULT'
            }, {
              label: '空间',
              value: 'SPACE'
            }, {
              label: '部门',
              value: 'DEPARTMENT'
            }, {
              label: '团队',
              value: 'TEAM'
            }, {
              label: '应用',
              value: 'APPLICATION'
            }, {
              label: '实例',
              value: 'INTERFACE'
            }, {
              label: '接口',
              value: 'INSTANCE'
            }, {
              label: '实例接口',
              value: 'INTERFACE_EXAMPLE'
            }],
            rules: [{
              required: true,
              message: '请选择项目组织类型',
              trigger: 'blur'
            }]
          },
          {
            props: 'oiExplain',
            type: 'input',
            label: '组织说明',
            defaultValue: '', // 初始值
            attrs: {
              placeholder: '请填写项目组织说明'
            },
            rules: [{
              required: true,
              message: '请填写项目组织说明',
              trigger: 'blur'
            }]
          }
          ]
        },

        events: {
          confirm: async(data) => {
						  // 添加下属组织
            apiOrganization.addSubOrganization(data).then(res => {
              console.log('添加项目组织成功==>, res')
              this.$message.success('添加成功')
              this.$refs.simplePopup.handleClose()
              this.getTeamOrganizationList() // 重新刷新列表数据
            }).catch(error => {
              console.log('添加项目组织失败==>', error)
            })
          },
          cancel: () => {
            this.$refs.simplePopup.handleClose()
          }
        }
      }
    }
  },
  created() {
    this.getTeamOrganizationList() // 获取项目组织列表
  },
  methods: {
    /**
			 * 根据团队id获取项目组织列表
			 */
    getTeamOrganizationList() {
      if (!this.info) {
        console.error('没有传进来正确的团队id')
        return
      }
      console.log('组织信息==>', this.info)
      apiOrganization.querySubOrganization({
        oiSuperiorId: this.info.oiId,
        oiType: this.info.oiType
      }).then(res => {
        console.log('查询到的下属组织==>', res)
        this.tableInfo.data = res.data
      }).catch(error => {
        console.log('查询下属组织失败==>', error)
      })
    }
  },

  /**
     * 点击添加项目组织
     * @param {Object} data
     */
  addSubOrganization(data) {
    return new Promise((resolve, reject) => {

    })
  }
}
</script>
