<template>
	<div class="app-container">
		<div>
			<!-- 筛选条件 -->
			<filter-form :settings="filterForm" v-on="filterForm.events" />
			
			<!-- 表格详情 -->
			<el-button type="primary" size="small" icon="el-icon-plus" @click="() => { $refs.simplePopup.handleCreate() }" class="mt-20">新增</el-button>
			<div class="bg-white mod mt-20">
				<div class="title">接口列表</div>
				<d2-crud v-bind="tableInfo" v-on="tableInfo.events" />
			</div>
		</div>
		
		<!-- 表单弹出框 -->
		<simple-popup ref="simplePopup" :settings="popupInfo" v-on="popupInfo.events" />
		
		<!-- 修改表单弹出框 -->
		<simple-popup v-if="editPopupInfo" ref="editSimplePopup" :settings="editPopupInfo" v-on="editPopupInfo.events" />
	</div>
</template>

<script>
import SimplePopup from '@/components/SimplePopup/index'
import FilterForm from '@/components/FilterForm/index'
export default {
	name: 'Profile',
	components: {
		SimplePopup,
		FilterForm
	},
	data() {
		return {
			user: {},
			activeTab: 'activity',
			
			// 筛选框配置
			filterForm: {
				items: [
					{
						props: 'oiSuperiorName',
						type: 'input',
						label: '接口名称',
						defaultValue: '', // 默认选中的值
						attrs: {
							placeholder: '请输入接口名称查找'
						}
					},
					{
						props: 'oiSuperiorType',
						type: 'input',
						label: '接口类型',
						defaultValue: '', // 默认选中的值
						attrs: {
							placeholder: '请输入接口名称查找'
						}
					}
				],
				
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
				data: [{
					oiName: '晓',
					oiEnglishName: 'xiao',
					oiType: '捕捉动物',
					oiExplain: '这是一段说明',
					oiCreaterName: '我想喝娃哈哈',
					oiOwnerName: 'Tobi',
					createTime: '2021-02-02',
					updateTime: '2021-02-02'
				}],
				pagination: {
					currentPage: 1,
					pageSize: 10,
					total: 0
				},
				rowHandle: {
					fixed: 'right',
					width: '200px',
					custom: [{
						text: '修改',
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
				columns: [
					{
						title: "接口名称",
						key: "oiName",
						minWidth: '100'
					},
					{
						title: "接口英文名",
						key: "oiEnglishName",
						minWidth: '100'
					},
					{
						title: "接口类型",
						key: "oiType",
						minWidth: '100'
					},
					{
						title: "接口说明",
						key: "oiExplain",
						minWidth: '120'
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
					rowClick: row => {
						console.log('点击了表格行数据==>', row)
					},
					modify: row => {
						console.log('点击了修改按钮==>', row)
						this.$refs.editSimplePopup.handleCreate(row.row)
					},
					delete: row => {
						// console.log('点击了删除按钮==>', row)
						this.$confirm('确定删除该接口？', '操作确认', {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'}).then(() => {
							// Todo: 事件操作
							this.$message.success('删除成功')
						}).catch(() => { this.$message.error('选择取消') })
					}
				}
			},
			
			// 弹出框详情
			popupInfo: {
				drawer: {
					// 配置项
					attrs: {
						title: '添加',
						direction: 'rtl',
					},
					events: {
						beforeClose: e => {
							console.log('关闭弹框')
						}
					}
				},
				form: {
					width: '100px',
					items: [
						{
							props: 'oiSuperiorId',
							type: 'input',
							label: '接口id',
							defaultValue: '21212121', // 初始值
							attrs: {
								placeholder: '请填写接口id'
							},
							rules: [{ required: true, message: '请输入接口id', trigger: 'blur' }]
						},
						{
							props: 'oiName',
							type: 'input',
							label: '接口名',
							defaultValue: '', // 初始值
							attrs: {
								placeholder: '请填写接口名称'
							},
							rules: [{ required: true, message: '请输入接口名称', trigger: 'blur' }]
						},
						{
							props: 'oiEnglishName',
							type: 'input',
							label: '接口英文名',
							defaultValue: '', // 初始值
							attrs: {
								placeholder: '请填写接口英文名'
							},
							rules: [{ required: true, message: '请输入接口英文名', trigger: 'blur' }]
						},
						{
							props: 'oiType',
							type: 'input',
							label: '接口类型',
							defaultValue: '', // 初始值
							attrs: {
								placeholder: '请填写接口类型'
							},
							rules: [{ required: true, message: '请输入接口类型', trigger: 'blur' }]
						},
						{
							props: 'oiExplain',
							type: 'input',
							label: '接口说明',
							defaultValue: '', // 初始值
							attrs: {
								placeholder: '请填写接口说明'
							},
							rules: [{ required: true, message: '请输入接口说明', trigger: 'blur' }]
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
		}
	},
	created() {
		// 设置修改模块
		this.editPopupInfo = {
			drawer: {
				...this.popupInfo.drawer,
				attrs: {
					title: '修改',
					direction: 'rtl',
				},
			},
			form: {
				...this.popupInfo.form
			},
			events: {
				...this.popupInfo.events,
				cancel: () => {
					this.$refs.editSimplePopup.handleClose()
				}
			}
		}
		
		console.log('输出修改表单设置==>', this.editPopupInfo)
	}
}
</script>
