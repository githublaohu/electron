<template>
  <div class="mod bg-white" style="margin-bottom:20px;">
    <div
      class="aic"
      style="justify-content: space-between;margin-top: -10px;"
    >
      <span class="title">个人信息</span>

      <el-button type="text" @click="() => { $refs.simplePopup.handleCreate() }">修改信息</el-button>
    </div>

    <el-row :gutter="24" style="display: flex;">
      <el-col :span="5" class="user-profile">
        <div class="box-center">
          <pan-thumb :height="'100px'" :width="'100px'" :hoverable="false">
            <div>Hello</div>
          </pan-thumb>
        </div>
        <div class="box-center">
          <div class="user-name text-center">{{ user.uiName }}</div>
        </div>
      </el-col>

      <el-col :span="8">
        <div class="user-bio-section-header mb-10 title">
          <!-- <i class="el-icon-help mr-5" /><span>个人介绍</span> -->
          个人介绍
        </div>
        <div class="text-muted aic">
          <div>性别</div>
          {{ user.uiSex }}
        </div>
        <div class="text-muted aic">
          <div>邮箱</div>
          {{ user.uiEmail }}
        </div>
        <div class="text-muted aic">
          <div>电话号码</div>
          {{ user.uiPhone }}Ff
        </div>
        <div class="text-muted aic">
          <div>{{ user.uiIdType }}</div>
          {{ user.uiIdCard }}
        </div>
      </el-col>

      <!-- <el-col :span="16" :offset="1">
				<el-row class="user-skills user-bio-section">
					<div class="user-bio-section-header mb-15 title">
						<i class="el-icon-help mr-5" /><span>个人介绍</span>
						技能树
					</div>
					<div class="user-bio-section-body aic" style='flex-wrap: wrap;'>
						<div class="progress-item mr-20">
							<span>Vue</span>
							<el-progress :percentage="70" />
						</div>
						<div class="progress-item">
							<span>JavaScript</span>
							<el-progress :percentage="18" />
						</div>
						<div class="progress-item mr-20">
							<span>Css</span>
							<el-progress :percentage="12" />
						</div>
						<div class="progress-item">
							<span>ESLint</span>
							<el-progress :percentage="100" status="success" />
						</div>
					</div>
				</el-row>
			</el-col> -->
    </el-row>

    <!-- 修改密码弹出框 -->
    <simple-popup ref="simplePopup" :settings="popupInfo" v-on="popupInfo.events" />
  </div>
</template>

<script>
import PanThumb from '@/components/PanThumb'
import SimplePopup from '@/components/SimplePopup/index'
import apiUser from '@/api/user'
export default {
  components: {
    PanThumb,
    SimplePopup
  },
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          name: '我想喝娃哈哈',
          email: '226337822121',
          avatar: 'https://img1.baidu.com/it/u=725867585,4256543316&fm=26&fmt=auto&gp=0.jpg',
          role: 'Admin'
        }
      }
    }
  },
  data() {
    return {
      popupInfo: {
        drawer: {
          // 配置项
          attrs: {
            title: '修改用户信息',
            direction: 'rtl'
          },
          events: {
            beforeClose: e => {
              console.log('关闭弹框')
            }
          }
        },
        form: {
          width: '120px',
          items: [
            // {
            // 	props: 'uiName',
            // 	type: 'input',
            // 	label: '账户名',
            // 	defaultValue: '',
            // 	attrs: {
            // 		placeholder: '请输入用户账户名'
            // 	},
            // 	rules: [{ required: true, message: '请输入账户名称', trigger: 'blur' }]
            // },
            // {
            // 	props: 'uiNickName',
            // 	type: 'input',
            // 	label: '用户昵称',
            // 	defaultValue: '',
            // 	attrs: {
            // 		placeholder: '请输入用户昵称'
            // 	},
            // 	rules: [{ required: true, message: '请输入用户昵称', trigger: 'blur' }]
            // }

            {
              props: 'newPassword',
              type: 'input',
              label: '新密码',
              defaultValue: '',
              attrs: {
                placeholder: '请输入新的密码',
                showPassword: true
              },
              rules: [{ required: true, message: '请输入账号密码', trigger: 'blur' }]
            },
            {
              props: 'newTwoPassword',
              type: 'input',
              label: '请确认密码',
              defaultValue: '',
              attrs: {
                placeholder: '请再次输入新密码',
                showPassword: true
              },
              rules: [{ required: true, message: '请再次输入新密码', trigger: 'blur' }]
            }
          ]
        },

        events: {
          confirm: data => {
            console.log('点击确定按钮==>', data)
            // apiUser.modifyUserInfo(data).then(res => {
            // 	console.log('修改用户信息成功==>', res)
            // 	this.$refs.simplePopup.handleClose()
            // 	this.$emit('refresh') // 重新刷新用户信息
            // }).catch(error => {
            // 	console.log('修改用户信息失败==>', error)
            // })

            // 修改密码
            apiUser.manageModifyUserPassword({ ...data }).then(res => {
              console.log('修改用户密码成功==>', res)
              this.$refs.simplePopup.handleClose()
              this.$emit('refresh') // 重新刷新用户信息
            }).catch(error => {
              console.log('修改用户密码失败==>', error)
            })
            // Todo: 事件操作
          },
          cancel: () => {
            this.$refs.simplePopup.handleClose()
            console.log('点击取消按钮')
          }
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
	.box-center {
		margin: 0 auto;
		display: table;
	}

	.text-muted {
		font-size: 14px;
		color: #999;
		margin-bottom: 13px;
	}

	.text-muted div {
		color: #333;
		width: 80px;
	}

	.user-profile {
		.user-name {
			font-weight: bold;
		}

		.box-center {
			padding-top: 10px;
		}

		.user-role {
			padding-top: 10px;
			font-weight: 400;
			font-size: 14px;
		}

		.box-social {
			padding-top: 30px;

			.el-table {
				border-top: 1px solid #dfe6ec;
			}
		}

		.user-follow {
			padding-top: 20px;
		}
	}

	.user-bio {
		margin-top: 20px;
		color: #606266;

		span {
			padding-left: 4px;
		}

		.user-bio-section {
			font-size: 14px;
			padding: 15px 0;

			.user-bio-section-header {
				border-bottom: 1px solid #dfe6ec;
				padding-bottom: 10px;
				margin-bottom: 10px;
				font-weight: bold;
			}
		}
	}

	.progress-item {
		width: 45%;
		margin-bottom: 20px;
	}
</style>
