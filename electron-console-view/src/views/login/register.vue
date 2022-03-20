<template>
	<div class="login-container">
		<el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
			label-position="left">
			<h3 class="title">用户注册</h3>
			<el-form-item prop="username">
				<span class="svg-container">
					<svg-icon icon-class="user" />
				</span>
				<el-input v-model="loginForm.username" name="username" type="text" auto-complete="on"
					placeholder="用户名" />
			</el-form-item>
			<el-form-item prop="userNickName">
				<span class="svg-container">
					<svg-icon icon-class="user" />
				</span>
				<el-input v-model="loginForm.userNickName" name="userNickName" type="text" auto-complete="on"
					placeholder="用户昵称" />
			</el-form-item>
			<el-form-item prop="password">
				<span class="svg-container">
					<svg-icon icon-class="password" />
				</span>
				<el-input :type="pwdType" v-model="loginForm.password" name="password" auto-complete="on"
					placeholder="密码" @keyup.enter.native="handleRegister" />
				<span class="show-pwd" @click="showPwd">
					<svg-icon icon-class="eye" />
				</span>
			</el-form-item>
			<el-form-item>
				<el-button :loading="loading" type="primary" style="width:100%;" @click.native.prevent="handleRegister">
					注册
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	import {
		isvalidUsername
	} from '@/common/utils/validate'
	import apiUser from "@/api/user.js";

	export default {
		name: 'Login',
		data() {
			const validateUsername = (rule, value, callback) => {
				callback()
			}
			const validatePass = (rule, value, callback) => {
				if (value.length < 5) {
					callback(new Error('密码不能小于5位'))
				} else {
					callback()
				}
			}
			return {
				loginForm: {
					username: '',
					userNickName: '',
					password: ''
				},
				loginRules: {
					username: [{
						required: true,
						trigger: 'blur',
						validator: validateUsername
					}],
					userNickName: [{
						required: true,
						trigger: 'blur',
						validator: validateUsername
					}],
					password: [{
						required: true,
						trigger: 'blur',
						validator: validatePass
					}]
				},
				loading: false,
				pwdType: 'password',
				redirect: undefined,
				resultMessage: '1111'
			}
		},
		watch: {
			$route: {
				handler: function(route) {
					this.redirect = route.query && route.query.redirect
				},
				immediate: true
			}
		},
		methods: {
			showPwd() {
				if (this.pwdType === 'password') {
					this.pwdType = ''
				} else {
					this.pwdType = 'password'
				}
			},
			handleRegister() {
				this.$refs.loginForm.validate(valid => {
					if (valid) {
						// 注册用户账号信息
						apiUser.register({
							uiName: this.loginForm.username,
							uiNickname: this.loginForm.userNickName,
							uiPassword: this.loginForm.password
						}).then(res => {
							console.log('注册成功==>', res)
						}).catch(error => {
							console.log('注册失败==>', error)
						})
						
						// 直接登录界面
						
						// this.$router.push({ path: this.redirect || '/' }) // 暂时设置为直接登录
						localStorage.setItem('userInfo', JSON.stringify({name: '1111', uiType: 'admin' }))

						// // this.loading = true
						// // psswordLogin({"uiName":this.loginForm.username,"uiPassword":  this.loginForm.password}).then((res) => {
						// //   this.loading = false
						// //   this.$router.push({ path: this.redirect || '/' })
						// //   localStorage.setItem('userInfo', JSON.stringify( res.data));
						// // }).catch(() => {
						// //   this.loading = false
						// // });
					} else {
						console.log('error submit!!')
						return false
					}
				})
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss">
	$bg:#2d3a4b;
	$light_gray:#eee;

	/* reset element-ui css */
	.login-container {
		.el-input {
			display: inline-block;
			height: 47px;
			width: 85%;

			input {
				background: transparent;
				border: 0px;
				-webkit-appearance: none;
				border-radius: 0px;
				padding: 12px 5px 12px 15px;
				color: $light_gray;
				height: 47px;

				&:-webkit-autofill {
					-webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
					-webkit-text-fill-color: #fff !important;
				}
			}
		}

		.el-form-item {
			border: 1px solid rgba(255, 255, 255, 0.1);
			background: rgba(0, 0, 0, 0.1);
			border-radius: 5px;
			color: #454545;
		}
	}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
	$bg:#2d3a4b;
	$dark_gray:#889aa4;
	$light_gray:#eee;

	.login-container {
		position: fixed;
		height: 100%;
		width: 100%;
		background-color: $bg;

		.login-form {
			position: absolute;
			left: 0;
			right: 0;
			width: 520px;
			max-width: 100%;
			padding: 35px 35px 15px 35px;
			margin: 120px auto;
		}

		.tips {
			font-size: 14px;
			color: #fff;
			margin-bottom: 10px;
			text-align: right;

			span {
				&:first-of-type {
					margin-right: 16px;
				}
			}
		}

		.svg-container {
			padding: 6px 5px 6px 15px;
			color: $dark_gray;
			vertical-align: middle;
			width: 30px;
			display: inline-block;
		}

		.title {
			font-size: 26px;
			font-weight: 400;
			color: $light_gray;
			margin: 0px auto 40px auto;
			text-align: center;
			font-weight: bold;
		}

		.show-pwd {
			position: absolute;
			right: 10px;
			top: 7px;
			font-size: 16px;
			color: $dark_gray;
			cursor: pointer;
			user-select: none;
		}
	}
</style>
