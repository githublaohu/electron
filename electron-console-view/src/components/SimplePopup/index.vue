<template>
  <!-- 简单的form表单组件，比较单一，可自行拓展配置 -->
  <el-drawer
    :visible.sync="show"
    v-bind="drawer.attrs || {}"
    v-on="drawer.events || {}"
  >
    <div class="ml-20 mr-15">
      <el-form ref="form" :model="formData" :rules="formRules" :label-width="labelWidth" label-position="left">
        <template v-for="(item, itemIndex) in formItems">
          <el-form-item
            v-if="!item.hasOwnProperty('show') || item.show"
            :key="itemIndex"
            :label="item.label"
            :prop="item.props"
          >
            <div class="ml-10">
              <!-- 输入框类型 -->
              <el-input
                v-if="item.type === 'input'"
                v-model="formData[item.props]"
                v-bind="item.attrs"
                v-on="item.events"
              />

              <!-- 穿梭框类型 -->
              <el-transfer
                v-if="item.type === 'transfer'"
                v-model="formData[item.props]"
                v-bind="item.attrs"
                v-on="item.events"
              />

              <!-- 选择框类型 -->
              <el-select
                v-if="item.type === 'select'"
                v-model="formData[item.props]"
                v-bind="item.attrs"
                v-on="item.events"
              >
                <el-option
                  v-for="option in item.options"
                  :key="option.value"
                  :label="option.label"
                  :value="option.value"
                />
              </el-select>
            </div>
          </el-form-item>
        </template>
      </el-form>

      <!-- 可自行写配置项 -->
      <div class="acc mt-30">
        <el-button
          type="danger"
          size="small"
          class="mr-30"
          @click="cancel"
        >取消</el-button>

        <el-button
          type="primary"
          size="small"
          icon="el-icon-check"
          @click="submitForm"
        >确定</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
export default {
  name: 'SimplePopup',
  props: {
    /**
		 * 表单配置
		 */
    settings: {
      type: Object,
      default: () => {
        return {
          drawer: {},
          form: {}
        }
      }
    }
  },
  data() {
    return {
      drawer: {}, // 抽屉配置项
      formItems: [], // 表单配置项

      benginFormData: null, // 设置表单一开始的初始值，用于重置表单
      formData: {}, // 表单值

      formRules: null, // 表单填写规则
      labelWidth: '',
      show: false // 是否显示弹出框
    }
  },
  watch: {
    settings: {
      immediate: true,
      handler(val) {
        this.drawer = val.drawer || {}
        this.formItems = val.form.items || [],
        this.labelWidth = val.form.width

        // 生成表单填写规则
        !this.formRules && this.createFormRules()
      }
    },

    formItems: {
      immediate: true,
      handler(val) {
        if (val.length) {
          this.createFormData() // 生成初始值
        }
      }
    }
  },
  methods: {
    /**
		 * 打开弹出框
		 * @param {Object} 设置表单的初始值
		 */
    handleCreate(data = null) {
      this.formData = data || this.formData

      this.show = true
    },

    /**
		 * 关闭弹框
		 */
    handleClose() {
		  this.resetForm() // 重置表单数据
      this.show = false
    },

    /**
		 * 生成表单初始值
		 */
    createFormData() {
      this.formItems.forEach(item => {
        this.$set(this.formData, item.props, item.defaultValue || '')
      })
      this.benginFormData = JSON.parse(JSON.stringify(this.formData))
    },

    /**
		 * 生成表单填写规格
		 */
    createFormRules() {
      this.formRules = {}
      this.formItems.forEach(item => {
        this.formRules[item.props] = item.rules
      })
    },

    /**
		 * 点击取消按钮
		 */
    cancel() {
      this.$emit('cancel')
    },

    /**
		 * 点击确定按钮
		 */
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$emit('confirm', this.formData)
          return
        }
        // Todo: 处理验证未通过情况
		    })
    },

    /**
		 * 重置数据，清楚校验规则
		 */
    resetForm() {
      this.formData = JSON.parse(JSON.stringify(this.benginFormData)) // 重置值

      this.$refs.form.resetFields() // 重置校验数据
    }
  }
}
</script>

<style scoped>
	>>> .el-drawer__header {
		font-weight: 600;
	}
</style>
