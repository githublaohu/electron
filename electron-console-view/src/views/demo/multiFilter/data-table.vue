<template>
  <div class="c-data-table">
    <el-table
      ref="listTable"
      :key="tableKey"
      :data="list"
      :max-height="tableHeight"
      :row-key="rowKey"
      border
      fit
      highlight-current-row
      cell-class-name="col-table-item"
      @select="onTableColSelect"
      @row-click="rowClick"
      @selection-change="onSelectionChange"
      @sort-change="onSortChange"
      style="width: 100%;"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column
        type="index"
        label="序号"
        prop="id"
        align="center"
        width="60"
      />

      <el-table-column
        label="下单时间"
        align="center"
        width="140"
        prop="orderDate"
      />
      <el-table-column
        label="付款时间"
        align="center"
        width="140"
        prop="payDate"
      />
      <el-table-column
        label="会员微信名称"
        align="center"
        width="140"
        prop="wechatName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="会员手机号"
        align="center"
        width="120"
        prop="phone"
      />
      <el-table-column
        label="订单编号"
        align="center"
        width="220"
        prop="orderId"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <b class="col-order-id" @click="onCtrlRow(scope.row, 'look')">{{
            scope.row.orderId
          }}</b>
        </template>
      </el-table-column>
      <el-table-column
        label="门店名称"
        align="center"
        width="180"
        prop="storeName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="门店编号"
        align="center"
        width="120"
        prop="storeNo"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="支付状态"
        align="center"
        width="100"
        prop="orderStatus1"
      />
      <el-table-column
        label="订单状态"
        align="center"
        width="100"
        prop="orderStatus"
      />
      <el-table-column
        label="订单金额"
        align="center"
        width="120"
        prop="orderTotal"
        :show-overflow-tooltip="true"
      />

      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template slot-scope="scope">
          <el-button type="danger" @click="onCtrlRow(scope.row, 'cancel')"
            >取消订单</el-button
          >
          <el-button type="primary" plain @click="onCtrlRow(scope.row, 'look')"
            >查看</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  components: {},
  props: {
    list: {
      type: Array,
      default() {
        return [];
      },
    },
    tableHeight: {
      type: Number,
      default: null,
    },
    tableKey: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {};
  },
  computed: {},
  mounted() {},
  methods: {
    rowKey(row) {
      return row.activityId;
    },
    // 选中
    onTableColSelect(selection, row) {
      this.$emit("rowSelect", selection, row);
    },
    rowClick(row, column, event) {
      this.$emit("rowClick", row, column, event);
    },
    onSelectionChange(val) {
      // element bug 初始化过滤undefined
      const arr =
        val && val.length > 0 ? val.filter((item) => item !== undefined) : [];
      this.$emit("selectionChange", arr);
    },
    // 排序
    onSortChange(data) {
      this.$emit("sortChange", data);
    },
    // 操作
    onCtrlRow(item, type) {
      this.$emit("ctrlRow", item, type);
    },
  },
};
</script>

<style lang="scss" scoped>
.c-data-table {
  margin: 5px 10px 10px 10px;
  .col-order-id {
    text-decoration: underline;
    color: #3a8ee6;
    cursor: pointer;
  }
}
</style>
