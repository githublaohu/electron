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
        label="商品名称"
        align="left"
        width="240"
        prop="productName"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="商品规格"
        align="left"
        prop="productSize"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="商品数量" align="left" prop="productNumber" />

      <el-table-column label="操作" align="center" width="230" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" plain>查看</el-button>
          <el-button type="primary" plain>编辑</el-button>
          <el-button type="danger">删除</el-button>
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
  },
};
</script>

<style lang="scss" scoped>
.c-data-table {
  margin: 10px 10px 10px 10px;
}
</style>
