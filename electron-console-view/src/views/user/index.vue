<template>
  <div class="app-container">
    <div v-if="user">
      <user-card :user="user" @refresh="getUserInfo" />

      <!-- 表格详情 -->
			<el-button type="primary" size="small" icon="el-icon-plus"
        @click="() => { $refs.simplePopup.handleCreate() }">新增</el-button>

      <div class="bg-white mod mt-20">
        <div class="title">团队组织列表</div>
        <d2-crud v-bind="tableInfo" v-on.stop="tableInfo.events" />
      </div>
    </div>

    <!-- 表单弹出框 -->
    <simple-popup ref="simplePopup" :settings="popupInfo" v-on="popupInfo.events" />

    <!-- 修改表单弹出框 -->
    <simple-popup
      v-if="editPopupInfo"
      ref="editSimplePopup"
      :settings="editPopupInfo"
      v-on.stop="editPopupInfo.events"
    />

    <!-- 添加权限弹出框 -->
    <dialog-auth ref="dialogAuth" @confirm="confirmAuth" @cancel="closeAuth" />
  </div>
</template>

<script>
import UserCard from './components/user-card'
import SimplePopup from '@/components/SimplePopup/index'
import DialogAuth from '@/components/DialogAuth/index'

import apiUser from '@/api/user'
import apiOrganization from '@/api/my/organization'
export default {
  name: 'Profile',
  components: {
    UserCard,
    SimplePopup,
    DialogAuth
  },
  data() {
    return {
      user: {},
      activeTab: 'activity',

      // 表格详情
      tableInfo: {
        data: [],
        rowHandle: {
          fixed: 'right',
          width: '180px',
          custom: [{
            text: '修改',
            type: 'warning',
            size: 'mini',
            // icon: 'el-icon-edit',
            emit: 'modify'
          }, {
            text: '查看详情',
            type: 'primary',
            size: 'mini',
            // icon: 'el-icon-view',
            emit: 'view'
          }]
        },
        columns: [{
          title: '组织id',
          key: 'oiId',
          minWidth: '50'
        },
        {
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
          minWidth: '150'
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
          modify: row => {
            // console.log('点击了修改按钮==>', row)
            this.$refs.editSimplePopup.handleCreate(JSON.parse(JSON.stringify(row.row)))
          },
          view: row => {
            this.$router.push({
              path: '/team/detail',
              query: {
                oiId: row.row.oiId,
                oiName: row.row.oiName,
                oiType: row.row.oiType,
                oiEnglishName: row.row.oiEnglishName,
              }
            })
          }
        },

        rowClick: () => {
          console.log('点击了行')
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
            props: 'oiId',
            type: 'input',
            label: '组织id',
            defaultValue: '0', // 初始值
            attrs: {
              placeholder: '如果创建空间，默认为0'
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
            type: 'select',
            label: '组织类型',
            defaultValue: 'APPLICATION', // 初始值
            attrs: {
              placeholder: '请选择组织类型',
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
          },
          {
            props: 'ioLabel',
            type: 'input',
            label: '组织标签',
            defaultValue: '', // 初始值
            attrs: {
              placeholder: '请输入组织标签'
            },
            rules: [{
              message: '请输入组织标签',
              trigger: 'blur'
            }]
          }
          ]
        },

        events: {
          confirm: data => {
            console.log('点击确定按钮==>', data)
            this.addOrganization(data)
          },

          cancel: () => {
            this.$refs.simplePopup.resetForm() // 重置表单数据
            this.$refs.simplePopup.handleClose()
            console.log('点击取消按钮')
          }
        }
      },

      editPopupInfo: null // 修改表单设置
    }
  },
  created() {
    // 设置修改模块
    this.editPopupInfo = {
      drawer: {
        ...this.popupInfo.drawer,
        attrs: {
          title: '修改',
          direction: 'rtl'
        }
      },
      form: {
        width: '100px',
        items: [{
          props: 'oiId',
          type: 'input',
          label: '组织id',
          defaultValue: '111', // 初始值
          attrs: {
            placeholder: '如果创建空间，默认为0',
            disabled: true
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
            placeholder: '请填写组织名称',
            disabled: true
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
            placeholder: '请填写组织英文名',
            disabled: true
          },
          rules: [{
            required: true,
            message: '请输入组织英文名',
            trigger: 'blur'
          }]
        },
        {
          props: 'oiType',
          type: 'select',
          label: '组织类型',
          defaultValue: '', // 初始值
          attrs: {
            placeholder: '请选择组织类型',
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
        },
        {
          props: 'ioLabel',
          type: 'input',
          label: '组织标签',
          defaultValue: '', // 初始值
          attrs: {
            placeholder: '请输入组织标签',
            disabled: true
          },
          rules: [{
            message: '请输入组织标签',
            trigger: 'blur'
          }]
        }
        ]
      },
      events: {
        confirm: data => {
          console.log('修改点击确定按钮==>', data)
          this.modifyOrganization(data)
        },

        cancel: () => {
          this.$refs.editSimplePopup.handleClose()
        }
      }
    }

    this.getUserInfo() //  获取用户信息
    this.getUserOrganization() // 获取用户的团队组织列表
  },
  methods: {
    /**
			 * 点击了确定权限
			 */
    confirmAuth(data) {
      console.log()
      console.log('主界面接受回调==>', data)
    },

    /**
			 * 点击了取消按钮
			 */
    closeAuth() {
      console.log('主界面接受cancel回调')
    },

    /**
			 * 获取用户信息
			 */
    getUserInfo() {
      apiUser.userInfo({
        uiId: this.$store.getters.userId
      }).then(res => {
        this.user = res.data || {}
      }).catch(error => {
        console.log('获取用户信息失败==>', error)
      })
    },

    /**
			 * 获取用户的组织列表
			 */
    getUserOrganization() {
      apiOrganization.userOrganization({
        uiId: this.$store.getters.userId
      }).then(res => {
        console.log('用户所属的团队组织列表==>', res)
        this.tableInfo.data = res.data || []
      }).catch(error => {
        console.log('获取团队组织列表失败==>', error)
      })
    },

    /**
			 * 添加组织
			 * @param {Object} data
			 */
    addOrganization(data) {
      data[""] = 10
      apiOrganization.addOrganization(data).then(res => {
        console.log('添加组织成功==>', res)
        this.$refs.SimplePopup.handleClose()
        this.getUserOrganization() // 重新获取列表数据
      }).catch(error => {
        console.log('添加组织失败==>', error)
      })
    },

    /**
			 * 修改点击确定按钮
			 * @param {Object} data
			 */
    modifyOrganization(data) {
      apiOrganization.modifyOrganization(data).then(res => {
        console.log('修改组织信息成功==>', res)
        this.$refs.editSimplePopup.handleClose()
        this.getUserOrganization() // 重新获取列表数据
      }).catch(error => {
        console.log('修改组织信息失败==>', error)
      })
    }
  }
}
</script>
