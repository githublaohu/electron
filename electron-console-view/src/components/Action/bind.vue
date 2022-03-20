<template>
	<!--  动作绑定组件 -->
	<el-drawer :visible.sync="show" v-bind="drawer.attrs || {}" v-on="drawer.events || {}">
		<div class="ml-20 mr-15">
			<!--   动作类型tab   -->
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="已绑定动作" name="bind">
          <el-tree
            ref="bindTree"
            :props="props"
            :load="loadNode"
            lazy>
            <span class="custom-tree-node aic" slot-scope="{ node, data }">
              <!--              第一级节点不显示复选框 && 当前的tab为未选中的节点操作-->
              <div style="min-width: 120px">{{ node.label }}</div>

              <span v-if="data.type !== 'AUTHENTICATION' && data.type !== 'PARTITION'" class="delete" @click="deleteAction(node, data)">移除</span>
            </span>
          </el-tree>
        </el-tab-pane>

        <el-tab-pane label="选择绑定动作" name="select">
          <el-tree
            ref="unBindTree"
            :props="props"
            :load="loadNode"
            lazy>
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <!--              第一级节点不显示复选框 && 当前的tab为未选中的节点操作-->
              <el-checkbox :value="selectNode.id && selectNode.id === data.id" v-if="data.type !== 'AUTHENTICATION' && data.type !== 'PARTITION'" @click="clickSelectNode(node, data)" />
              <span>{{ node.label }}</span>
            </span>
          </el-tree>
        </el-tab-pane>
      </el-tabs>
		</div>
	</el-drawer>
</template>
<script>
	import apiAction from '@/api/my/action'

  // 第一级节点显示动作类型
  const FIRST_NODE = [{
	  name: '请求认证',
    type: 'AUTHENTICATION'
  }, {
	  name: '资源隔离',
    type: 'PARTITION'
  }]

	export default {
		name: 'bind',
		data() {
			return {
				show: false, // 是否显示弹出框
				activeName: 'bind', // 选中的tab栏

				drawer: {}, // 弹出框配置
        info: {}, // 传过来的组织信息

        // 树节点配置
        props: {
          label: 'name',
          children: 'children'
        },

        selectNode: {}, // 选中的节点
			}
		},
		methods: {
			/**
			 * 打开弹出框，查询动作
			 */
			handleCreate(info) {
        if (info && info.id) {
          this.info = info
          this.show = true
          return
        }
        console.error('不完整参数')
			},

      /**
       * 点击切换tab栏
       */
      handleClick(e) {
        console.log('点击切换tab', e)
      },

      /**
       * 加载树形节点数据
       * 目前的节点层级为两级，第一级为动作类型，第二级为对应的动作
       */
      async loadNode(node, resolve) {
        // 第一级节点显示动作类型
        if (node.level === 0) {
          return resolve(FIRST_NODE)
        }

        if (node.level === 2) {
          return resolve([])
        }

        let res = { data: [] } // 请求的数据
        // 1.判断当前的tab，调用不同的接口获取动作列表
        if (this.activeName === 'bind') {
          res = await apiAction.queryBindAction({
            organizationId: this.info.id,
            aiAbilityType: node.data.type
          })
        } else if(this.activeName === 'select') {
          res = await apiAction.queryUnBindAction({
            organizationId: this.info.id,
            organizationTypeEnum: this.info.type,
            aiAbilityType: node.data.type
          })
        }
        // 2. 格式化返回动作
        const data = res.data.map(item => {
          return {
            name: item.aiName || '',
            type: item.abilityTypeEnum,
            id: item.arId,
            leaf: true
          }
        })
        resolve(data)
      },

      /**
       * 点击选中节点
       */
      async clickSelectNode(node, data) {
        this.selectNode  = data
        try {
          await this.$confirm(`确定绑定动作【${this.selectNode.name}】`, '操作确认', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })

          // 动作绑定
          await apiAction.bindAction({
            arId: this.selectNode.id
          })
          this.$message.success('绑定成功')
          this.$refs.unBindTree.remove(node) // 删除该节点
        } catch(e) {
          console.log('绑定失败==>', e)
        }
      },

      /**
       * 点击移除节点
       */
      async deleteAction(node, data) {
        this.selectNode = data
        try {
          await this.$confirm(`确认解绑动作【${this.selectNode.name}】`, '操作确认', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })

          // 解绑动作
          await apiAction.unBindAction({ arId: this.selectNode.id })
          this.$message.success('解绑成功')
          this.$refs.bindTree.remove(node) // 删除该节点
        } catch(e) {
          console.log('解绑失败==>', e)
        }
      },

			/**
			 * 点击取消按钮
			 */
			cancel() { this.show = false },

			/**
			 * 点击确定按钮
			 */
			confirm() {}
		}
	}
</script>

<style>
  .delete {
    font-size: 12px;
    color: red;
    cursor: pointer;
  }
</style>
