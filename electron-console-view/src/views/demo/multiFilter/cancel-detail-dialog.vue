<template>
  <div class="c-cancel-detail-dialog">
    <el-form
      ref="dialogForm"
      :model="model"
      :rules="rules"
      size="mini"
      label-width="140px"
      class="c-dialog-form"
      @submit.native.prevent
    >
      <el-form-item
        label="售后类型："
        class="g-filter-col-item"
        label-width="100px"
        prop="reasonType"
      >
        <el-select v-model="model.reasonType" placeholder="请选择">
          <el-option
            v-for="item in reasonTypeOptions"
            :key="item.dictValue"
            :label="item.remark"
            :value="item.dictValue"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item
        label="会员微信名："
        class="g-filter-col-item"
        label-width="100px"
      >
        <span>{{ model.wechatName }}</span>
      </el-form-item>

      <el-form-item
        label="会员手机号："
        class="g-filter-col-item"
        label-width="100px"
      >
        <span>{{ model.phone }}</span>
      </el-form-item>

      <el-form-item
        label="订单编号："
        class="g-filter-col-item"
        label-width="100px"
      >
        <span>{{ model.orderId }}</span>
      </el-form-item>

      <el-form-item
        label="门店名称："
        class="g-filter-col-item"
        label-width="100px"
      >
        <span>{{ model.storeName }}</span>
      </el-form-item>

      <el-form-item
        label="店编号："
        class="g-filter-col-item"
        label-width="100px"
      >
        <span>{{ model.storeNo }}</span>
      </el-form-item>

      <el-form-item
        label="订单金额："
        class="g-filter-col-item"
        label-width="100px"
      >
        <span>{{ model.orderTotal }}</span>
      </el-form-item>
    </el-form>

    <div slot="footer" class="c-dialog-submit-row">
      <el-button
        size="small"
        type="info"
        class="dialog-btn"
        @click="onCloseDialog"
        >取消</el-button
      >
      <el-button
        size="small"
        type="primary"
        class="dialog-btn"
        @click="onSaveDialog"
        :disabled="submitting"
        >确定</el-button
      >
    </div>
  </div>
</template>

<script>
// import { verifyOrderId } from '@/views/demo/multiFilter/validateRules';

export default {
  components: {},
  data() {
    return {
      model: {
        reasonType: "",
        wechatName: "张锐",
        phone: "15012722095",
        orderId: "200616001101087201017302",
        storeName: "优选夹石河店",
        storeNo: "20201212",
        orderTotal: "450.0",
      },
      rules: {
        reasonType: [
          { required: true, message: "请正确选择此字段", trigger: "change" },
        ],
      },
      // 异步获取
      reasonTypeOptions: [
        {
          dictValue: "0",
          remark: "其他原因(当日售后)",
        },
        {
          dictValue: "1",
          remark: "拍错/多拍/不想要",
        },
        {
          dictValue: "2",
          remark: "下错门店(当日售后)",
        },
      ],
      submitting: false,
    };
  },
  mounted() {},
  methods: {
    onCloseDialog() {
      this.$emit("close", false);
    },
    onSaveDialog() {
      this.$refs.dialogForm.validate(async (valid) => {
        this.submitting = true;
        if (valid) {
          // this.$message.error(res.rspDesc || '保存失败');
          this.$emit("close", true);
        } else {
          this.submitting = false;
        }
      });
      return false;
    },
  },
};
</script>

<style lang="scss" scoped>
.c-cancel-detail-dialog {
  .c-dialog-form {
    width: 380px;
    margin: 0 auto;
  }
}
</style>
