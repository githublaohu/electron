<template>
	<div>
		<el-menu class="navbar" mode="horizontal">
			<hamburger :toggle-click="toggleSideBar" :is-active="!sidebar.opened" class="hamburger-container" />
			<breadcrumb />
			<el-dropdown class="avatar-container" trigger="click">
				<div class="avatar-wrapper aic">
					<img :src="avatar" class="user-avatar">
					<i class="el-icon-caret-bottom" />
				</div>
				<el-dropdown-menu slot="dropdown" class="user-dropdown">
					<router-link class="inlineBlock" to="/user">
						<el-dropdown-item>
							个人中心
						</el-dropdown-item>
					</router-link>
					<!-- <el-dropdown-item @click="logout">
						退出登录
					</el-dropdown-item> -->
					<el-dropdown-item divided>
						<span style="display:block;" @click="logout">安全退出</span>
					</el-dropdown-item>
				</el-dropdown-menu>
			</el-dropdown>
		</el-menu>
	</div>
</template>

<script>
	import {
		mapGetters
	} from 'vuex'
	import Breadcrumb from '@/components/Breadcrumb'
	import Hamburger from '@/components/Hamburger'
  import { removeToken } from "../../../common/utils/auth";

	export default {
		components: {
			Breadcrumb,
			Hamburger
		},
		computed: {
			...mapGetters([
				'sidebar',
				'avatar'
			])
		},
		methods: {
			toggleSideBar() {
				this.$store.dispatch('ToggleSideBar')
			},
			async logout() {
				try {
					await this.$confirm('确认退出登录？', '操作确认', {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'})
					// await this.$store.dispatch('LoginOut')
					// location.reload() // 为了重新实例化vue-router对象 避免bug
          removeToken(null) // 删除token
					// 返回登录页面
					this.$router.push({
						path: '/login'
					})
				} catch(e) {
					console.log('退出登录失败==>', e)
				}
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.navbar {
		height: 50px;
		line-height: 50px;
		border-radius: 0px !important;

		.hamburger-container {
			line-height: 58px;
			height: 50px;
			float: left;
			padding: 0 10px;
		}

		.screenfull {
			position: absolute;
			right: 90px;
			top: 16px;
			color: red;
		}

		.avatar-container {
			height: 50px;
			display: inline-block;
			position: absolute;
			right: 35px;

			.avatar-wrapper {
				cursor: pointer;
				margin-top: 5px;
				position: relative;
				line-height: initial;

				.user-avatar {
					width: 40px;
					height: 40px;
					border-radius: 10px;
					margin-right: 8px;
				}

				// .el-icon-caret-bottom {
				// 	position: absolute;
				// 	right: -20px;
				// 	top: 25px;
				// 	font-size: 12px;
				// }
			}
		}
	}
</style>
