<template>
  <div class="c-order-detail-dailog">
    <div class="codd-header">
      <el-row :gutter="10" class="codd-hd-row">
        <el-col :xs="24" :sm="12" :md="8">
          <span class="codd-label">订单编号：</span
          ><b>200612001101060500088559</b>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8">
          <span class="codd-label">下单时间：</span><b>2020-06-12 14:52:21</b>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8">
          <span class="codd-label">订货门店：</span
          ><b>12312388 夹石河芙蓉兴盛店</b>
        </el-col>
      </el-row>

      <el-row :gutter="10" class="codd-hd-row">
        <el-col :xs="24" :sm="12" :md="8">
          <span class="codd-label">会员电话：</span><b>15084757930</b>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8">
          <span class="codd-label">总金额：</span><b>10014</b>
        </el-col>
        <el-col :xs="24" :sm="12" :md="8">
          <span class="codd-label">支付类型：</span><b>小程序支付</b>
        </el-col>
      </el-row>
    </div>
    <!--表格展示区域-->
    <div class="codd-body">
      <order-detail-table></order-detail-table>
    </div>
  </div>
</template>

<script>
import orderDetailTable from "./order-detail-table";

export default {
  components: {
    orderDetailTable,
  },
  data() {
    return {};
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
.c-order-detail-dailog {
  .codd-hd-row {
    margin-bottom: 10px;
  }
  .codd-label {
    margin-right: 5px;
    min-width: 100px;
    text-align: right;
    display: inline-block;
  }
  .codd-header {
    margin-bottom: 20px;
  }
  .codd-header b {
    font-weight: normal;
    display: inline-block;
    color: #333;
  }
}
</style>
