<template>
  <div class="c-order-detail-table">
    <el-table
      :data="list"
      border
      show-summary
      :summary-method="getSummaries"
      style="width: 100%;"
    >
      <el-table-column
        type="index"
        label="序号"
        prop="id"
        align="center"
        width="60"
      />
      <el-table-column
        prop="productName"
        label="商品名称"
        width="210"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        prop="sku"
        label="商品编码"
        width="180"
        :show-overflow-tooltip="true"
      />
      <el-table-column prop="productTypeName" label="商品类型" width="90" />
      <el-table-column prop="warehouseTypeName" label="仓库类型" width="90" />
      <el-table-column prop="warehouseName" label="配送仓库" width="90" />
      <el-table-column
        prop="lineName"
        label="配送线路"
        width="200"
        :show-overflow-tooltip="true"
      />
      <el-table-column prop="deliveryTime" label="提货时间" width="150" />
      <el-table-column
        prop="vendorName"
        label="供应商"
        width="200"
        :show-overflow-tooltip="true"
      />
      <el-table-column prop="vendorCode" label="供应商编码" width="200" />
      <el-table-column prop="skuContent" label="规格" width="80" />
      <el-table-column prop="packingNumber" label="包装数" width="80" />
      <el-table-column prop="presaleQty" label="限订数量" width="80" />
      <el-table-column prop="qty" label="订货数量" width="80" />
      <el-table-column prop="itemAdjustedPrice" label="价格" width="80" />
      <el-table-column prop="totalPrice" label="配送金额" width="80" />
      <el-table-column prop="totalQty" label="总数量" width="80" />
      <el-table-column prop="commission" label="每份提成" width="100" />
      <el-table-column prop="totalCommission" label="合计提成" width="100" />
      <el-table-column prop="platformAmt" label="平台服务费/份" width="100" />
      <el-table-column prop="totalPlatformAmt" label="平台服务费" width="100" />
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [
        {
          productName: "湘西组合野果",
          sku: "0137125",
          productTypeName: "品牌馆商品",
          warehouseTypeName: "",
          warehouseName: "",
          lineName: "",
          deliveryTime: "2020-06-16 16:00:01",
          vendorName: "大世界AA",
          vendorCode: "778899",
          skuContent: "12",
          packingNumber: "10",
          presaleQty: "100000",
          qty: "50",
          itemAdjustedPrice: "15.00",
          totalPrice: "55.00",
          totalQty: "100",
          commission: "21",
          totalCommission: "23",
          platformAmt: "10",
          totalPlatformAmt: "20",
        },
        {
          productName: "湘西组合野果",
          sku: "0137125",
          productTypeName: "品牌馆商品",
          warehouseTypeName: "",
          warehouseName: "",
          lineName: "",
          deliveryTime: "2020-06-16 16:00:01",
          vendorName: "大世界AA",
          vendorCode: "778899",
          skuContent: "12",
          packingNumber: "10",
          presaleQty: "100000",
          qty: "50",
          itemAdjustedPrice: "15.00",
          totalPrice: "55.00",
          totalQty: "100",
          commission: "21",
          totalCommission: "23",
          platformAmt: "10",
          totalPlatformAmt: "20",
        },
      ],
    };
  },
  methods: {
    getSummaries(param) {
      const { columns } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 13) {
          sums[index] = "1000";
        } else if (index === 15) {
          sums[index] = "1000";
        } else if (index === 16) {
          sums[index] = "1000";
        } else if (index === 18) {
          sums[index] = "1000";
        } else if (index === 20) {
          sums[index] = "1000";
        }
        // todo 表格脚部取值
      });

      return sums;
    },
  },
};
</script>

<style lang="scss" scoped>
.c-order-detail-table {
}
</style>
