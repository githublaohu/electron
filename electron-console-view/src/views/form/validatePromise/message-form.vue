<template>
  <div>
    <el-form
      :model="ruleForm"
      :rules="rules"
      :inline="true"
      ref="ruleForm"
      label-width="120px"
      class="demo-ruleForm"
      size="mini"
    >
      <el-form-item label="消息内容" prop="content">
        <el-input v-model="ruleForm.content"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ruleForm: {
        content: "",
      },
      rules: {
        content: [
          { required: true, message: "请输入内容", trigger: "blur" },
          {
            min: 3,
            max: 5,
            message: "长度在 3 到 5 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    validate() {
      return this.$refs.ruleForm.validate();
    },
    getData() {
      return this.ruleForm;
    },
    resetForm() {
      this.$refs.ruleForm.resetFields();
    },
  },
};
</script>
