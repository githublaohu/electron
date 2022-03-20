<!-- 直接传入权限列表list， 详细的配置可见props -->
<template>
	<simple-popup ref="SimplePopup" :settings="popupInfo" v-on="popupInfo.events" />
</template>

<script>
import SimplePopup from '@/components/SimplePopup/index'
export default {
	name: 'Auth',
	components: { SimplePopup },
	props: {
		/**
		 * 权限列表
		 */
		list: {
			type: Array,
			default: () => {
				return [
					{
						id: 1,
						desc: '权限1'
					},
					{
						id: 2,
						desc: '权限2'
					},
					{
						id: 3,
						desc: '权限3',
						disabled: true // 表示不可移动
					},
					{
						id: 4,
						desc: '权限4'
					},
					{
						id: 5,
						desc: '权限5'
					}
				]
			}
		}
	},
	computed: {
		popupInfo() {
			return {
				drawer: {
					// 配置项
					attrs: {
						title: '设置权限',
						direction: 'rtl',
						size: '500'
					},
					events: {
						beforeClose: e => {
							console.log('关闭弹框')
						}
					}
				},
				form: {
					width: '0px',
					items: [
						{
							props: 'has_auth_list',
							type: 'transfer',
							label: '',
							defaultValue: [],
							attrs: {
								props: {
									key: 'id',
									label: 'desc'
								},
								titles: ['未拥有权限', '已有权限'],
								buttonTexts: ['移除', '添加'],
								data: this.list
							}
						}
					]
				},
				
				events: {
					confirm: data => {
						console.log('点击确定按钮==>', data)
						this.$emit('confirm', data)
					},
					cancel: () => {
						console.log('点击取消按钮')
						this.$refs.SimplePopup.handleClose()
						this.$emit('cancel')
					}
				}
			}
		}
	},
	methods: {
		/**
		 * 点击显示权限配置框
		 * @param {Object} 设置初始值  
		 */
		handleCreate(data = null) {
			this.$refs.SimplePopup.handleCreate()
		}
	}
}
</script>

<style>
</style>
