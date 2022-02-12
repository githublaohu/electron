<template>
  <he-page class="form-validate-page">
    <h1>父表单验证-子组件表单-常规方法</h1>
    <el-form
      :model="addForm"
      :inline="true"
      :rules="formRules"
      ref="ruleForm"
      size="mini"
      label-width="120px"
      class="form-mini"
    >
      <child-form ref="childRules" :addForm="addForm" />

      <el-form-item label="简介" prop="desc">
        <el-input v-model="addForm.desc"></el-input>
      </el-form-item>

      <el-button @click="saveForm()" size="medium">保 存</el-button>
    </el-form>
  </he-page>
</template>

<script>
import childForm from "./child-form";

export default {
  components: {
    childForm,
  },
  data() {
    return {
      addForm: {
        name: "",
        desc: "",
      },
      formRules: {
        desc: [
          {
            required: true,
            message: "请输入简介",
            trigger: "blur",
          },
        ],
      },
    };
  },
  computed: {},
  mounted() {},
  methods: {
    // 保存校验
    saveForm() {
      // let flag = this.$refs['childRules'].validateForm();
      // if(flag){
      //   console.log(this.addForm);
      // }else{
      //   this.$message.error('保全信息不完整，请继续填写完整');
      // }
      console.warn("子表单验证：", this.$refs.childRules.validateForm());
      this.$refs.ruleForm.validate((valid) => {
        console.log("当前表单验证:", valid);
      });
      return false;
    },
  },
};
</script>

<style lang="scss" scoped></style>
