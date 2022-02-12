<template>
  <he-table-filter-collapse>
    <he-table-filter
      ref="filterForm"
      :model="model"
      :rules="rules"
      :is-collapse="true"
      @submit="filterSubmit"
      @reset="filterRest"
    >
      <el-col :xs="24" :sm="12" :md="7">
        <el-form-item
          label="订单编号"
          class="g-filter-col-item"
          label-width="100px"
          prop="orderId"
        >
          <el-input
            placeholder="请输入订单编号"
            v-model.number="model.orderId"
            clearable
          >
          </el-input>
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="12" :md="7">
        <el-form-item
          label="会员手机号"
          class="g-filter-col-item"
          label-width="100px"
          prop="userName"
        >
          <el-input
            placeholder="请输入会员手机号"
            v-model="model.userName"
            clearable
          ></el-input>
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="12" :md="7">
        <el-form-item
          label="收货人手机号"
          class="g-filter-col-item"
          label-width="100px"
          prop="phone"
        >
          <el-input
            placeholder="请输入收货人手机号"
            v-model="model.phone"
            clearable
          ></el-input>
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="12" :md="7">
        <el-form-item
          label="门店编号"
          class="g-filter-col-item"
          label-width="100px"
          prop="storeNo"
        >
          <el-input
            placeholder="请输入门店编号"
            v-model="model.storeNo"
            clearable
          ></el-input>
        </el-form-item>
      </el-col>

      <el-col :xs="24" :sm="12" :md="7">
        <el-form-item
          label="门店名称"
          class="g-filter-col-item"
          label-width="100px"
          prop="storeName"
        >
          <el-input
            placeholder="请输入门店名称"
            v-model="model.storeName"
            clearable
          ></el-input>
        </el-form-item>
      </el-col>
    </he-table-filter>
  </he-table-filter-collapse>
</template>

<script>
import { validatePhone } from "@/common/utils/validateRules";
import { verifyOrderId } from "./validateRules";

export default {
  name: "data-filter",
  components: {},
  data() {
    return {
      model: {
        orderId: "",
        userName: "",
        phone: "",
        storeNo: "",
      },
      rules: {
        orderId: [{ validator: verifyOrderId(), trigger: "blur" }],
        userName: [{ validator: validatePhone(), trigger: "blur" }],
        phone: [{ validator: validatePhone(), trigger: "blur" }],
      },
    };
  },
  computed: {
    selectOptions() {
      return [
        {
          label: "电商线",
          value: "1",
        },
        {
          label: "物流线",
          value: "2",
        },
        {
          label: "资金线",
          value: "3",
        },
        {
          label: "供应链线",
          value: "4",
        },
        {
          label: "基础/中间件线",
          value: "5",
        },
      ];
    },
    levelOptions() {
      return [
        {
          label: "高",
          value: "1",
        },
        {
          label: "中",
          value: "2",
        },
        {
          label: "低",
          value: "3",
        },
      ];
    },
    progressTypeOptions() {
      return [
        {
          label: "计划",
          value: "1",
        },
        {
          label: "实际",
          value: "2",
        },
      ];
    },
  },
  mounted() {},
  methods: {
    // 验证规则
    filterSubmit(model) {
      this.$refs.filterForm.$refs.dataFilterForm.validate(async (valid) => {
        const everyEmpty = Object.values(model).every(
          (item) => item.toString().length === 0
        );
        if (everyEmpty) {
          this.$message.warning("请输入查询条件!");
          return false;
        }
        if (valid) {
          this.$emit("submit", model);
        } else {
          return false;
        }
      });
    },
    filterRest(model) {
      this.$emit("reset", model);
    },
  },
};
</script>

<style lang="scss" scoped>
.g-filter-content {
}
.col-date-picker {
  width: 185px;
}
</style>
