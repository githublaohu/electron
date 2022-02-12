<template>
  <he-page class="form-validate-page">
    <h1>父表单验证-子组件表单-promise方法</h1>
    <el-form
      :model="addForm"
      :inline="true"
      :rules="formRules"
      ref="ruleForm"
      size="mini"
      label-width="120px"
      class="form-mini"
    >
      <child-form ref="child" :addForm="addForm" />

      <message-form ref="message" />

      <el-form-item label="简介" prop="desc">
        <el-input v-model="addForm.desc"></el-input>
      </el-form-item>

      <el-button @click="saveForm()" size="medium">保 存</el-button>
    </el-form>
  </he-page>
</template>

<script>
import childForm from "./child-form";
import messageForm from "./message-form";

export default {
  components: {
    childForm,
    messageForm,
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
    saveForm() {
      this.validateFormAll();
      return false;
    },

    /**
     *  1、父表单验证通过才会验证子表单，存在先后顺序
     */
    validateFormOrder() {
      this.$refs.ruleForm
        .validate()
        .then((valid) => {
          console.log("==验证当前父表单通过=", valid);
          // 父表单验证成功后，验证子表单
          return this.$refs.child.validate();
        })
        .then((valid) => {
          console.log("==验证child通过=", valid);
          return this.$refs.message.validate();
        })
        .then((valid) => {
          console.log("==所有验证通过=", valid);
          // 获取整体数据
          // const reqData = {
          //   // 获取子组件数据
          //   ...this.$refs['message'].getData(),
          //   ...this.addForm
          // };
        })
        .catch((e) => {
          console.warn("--saveForm--", e);
        });
    },

    validateFormAll() {
      const validate1 = this.$refs.child.validate();
      const validate2 = this.$refs.message.validate();
      const validate3 = this.$refs.ruleForm.validate();
      // 父子表单一起验证
      Promise.all([validate1, validate2, validate3])
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style lang="scss" scoped></style>
