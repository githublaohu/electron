<template>
  <div class="form-content">
    <el-form
      :model="addForm"
      :inline="true"
      :rules="formRules"
      ref="ruleForm"
      size="mini"
      label-width="120px"
      class="form-mini"
    >
      <el-form-item label="名字" prop="name">
        <el-input v-model="addForm.name"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    addForm: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  data() {
    return {
      formRules: {
        name: [
          {
            required: true,
            message: "请输入姓名",
            trigger: "blur",
          },
        ],
      },
    };
  },
  computed: {},
  mounted() {},
  methods: {
    // 子组件校验，传递到父组件
    validate() {
      // 返回`elementUI`表单验证的结果（为`promise`对象）
      return this.$refs.ruleForm.validate();
    },
    resetForm() {
      this.$refs.ruleForm.resetFields();
    },
  },
};
</script>

<style lang="scss" scoped></style>
