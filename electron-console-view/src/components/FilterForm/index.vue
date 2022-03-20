<template>
  <div class="mod bg-white">
    <div class="title">筛选条件</div>
    <el-row :gutter="24">
      <el-form ref="form" :model="formData" label-width="80px" label-position="left">
        <el-col v-for="(item, itemIndex) in formItems" :key="itemIndex" :span="8" class="mr-30 mt-20">
          <el-form-item
            :label="item.label"
            :prop="item.props"
          >
            <div class="ml-10">
              <el-input
                v-if="item.type === 'input'"
                v-model="formData[item.props]"
                v-bind="item.attrs || {}"
                v-on="item.events || {}"
              />
            </div>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>

    <!-- 可自行写配置项 -->
    <div class="aic mt-30" style="justify-content: flex-end;">
      <el-button
        type="danger"
        size="mini"
        @click="reset"
      >重置</el-button>

      <el-button
        type="primary"
        size="mini"
        icon="el-icon-search"
        @click="confirm"
      >查找</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FilterForm',
  props: {
    /**
		 * 配置项
		 */
    settings: {
      type: Object,
      default: () => {
        return {
          items: []
        }
      }
    }
  },
  data() {
    return {
      formItems: [], // 表单项

      formData: {}, // 表单值
      beginFormData: null // 表单默认选中值，重置是需要用到
    }
  },
  watch: {
    settings: {
      immediate: true,
      handler(val) {
        if (val.items) {
          this.formItems = val.items
        }

        // 设置表单值
        !this.beginFormData && this.createFormData()
      }
    },

    /**
		 * 监听表单值的变化，并回调 change 事件
		 */
    formData: {
      immediate: true,
      deep: true,
      handler(val) {
        this.$emit('change', val)
      }
    }
  },
  methods: {
    /**
		 * 生成表单初始值
		 */
    createFormData() {
      this.formItems.forEach(item => {
        this.$set(this.formData, item.props, item.defaultValue || '')
      })
      this.beginFormData = JSON.parse(JSON.stringify(this.formData))
    },

    /**
		 * 点击重置按钮
		 */
    reset() {
      this.formData = JSON.parse(JSON.stringify(this.beginFormData))
      this.$emit('reset', this.formData)
    },

    /**
		 * 点击查找按钮
		 */
    confirm() {
      this.$emit('confirm', this.formData)
    }
  }
}
</script>
