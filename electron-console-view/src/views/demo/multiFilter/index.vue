<template>
  <he-page class="order-cancel-page">
    <!--搜索条件区域-->
    <data-filter @submit="onQueryFilter" @reset="onResetFilter" />
    <!--表格区域：表格、操作、分页-->
    <he-table-layout title="订单列表">
      <!--业务table定制-->
      <data-table :list="list" @ctrlRow="onCtrlRow" />

      <template v-slot:footer>
        <he-table-pagination
          :page="page"
          @sizeChange="handleSizeChange"
          @currentChange="handleCurrentChange"
          @onRefresh="handleRefresh"
        />
      </template>
    </he-table-layout>
    <!--弹窗区域-->
    <el-dialog
      :append-to-body="true"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :top="dialog.top"
      @close="onDialogClose"
      center
    >
      <component
        :inject-data="dialogInjectData"
        :is="dialog.component"
        @close="onDialogClose"
      />
    </el-dialog>
  </he-page>
</template>

<script>
import { dialogMixin, paginationMixin } from "@/common/mixin";
// component
import dataFilter from "./data-filter";
import dataTable from "./data-table";
import cancelDetailDialog from "./cancel-detail-dialog";
import orderDetailDailog from "./order-detail-dailog";

export default {
  components: {
    dataFilter,
    dataTable,
  },
  mixins: [dialogMixin, paginationMixin],
  data() {
    return {
      filterForm: {}, // 接收data-filter组件值
      list: [
        {
          rows: 10,
          page: 1,
          current: 1,
          count: null,
          total: null,
          userKey: null,
          needPaged: true,
          orderId: "200611001101046100088557",
          orderStatus: "PAID",
          userId: 568821600004302461,
          userName: "18870798916",
          wechatName: "mai",
          receiver: "fdd",
          phone: "15524526325",
          totalCommission: 15312.0,
          amount: null,
          orderTotal: 1113003.0,
          orderDate: "2020-06-11 09:33:55",
          payDate: "2020-06-11 09:21:05",
          storeId: 66880000190302,
          storeNo: "18870789",
          storeName: "谢东萍麓谷店铺",
          totalQty: 11,
          totalLogisticsAmt: 0.0,
          totalStorageAmt: 0.0,
          totalPlatformAmt: 14211.0,
          totalPromotionAmt: 0.0,
          paymentId: "3522200611001101046100088557",
          platformAmount: "14211.00",
          totalPrice: 1113003.0,
          start: 0,
        },
      ],
      dialogInjectData: null,
    };
  },
  computed: {},
  mounted() {
    // todo 请求 getList
    // this.getList()
  },
  methods: {
    /** **查询条件*** */
    async onQueryFilter(filter) {
      // 可能需要数据转换
      this.queryFilterList(filter);
    },
    async onResetFilter(filter) {
      // 可能需要数据转换
      this.queryFilterList(filter);
    },
    // 同步查询条件-列表
    queryFilterList(filter) {
      this.filterForm = filter;
      this.getList();
    },
    /** **表格*** */
    // 请求列表固定用getList方法，因为分页组件事件会调用
    async getList() {
      try {
        // todo 参数转换处理
        const params = {
          ...this.filterForm,
          // 页码
          page: this.page.num,
          // 记录数
          pageSize: this.page.size,
        };
        const res = await this.$api.demoApi.cancelList(params);
        this.list = res.rows || [];
        this.page.total = res.total || 0;
      } catch (e) {
        this.$log.error(e, "getList");
      }
    },
    onCtrlRow(item, type) {
      switch (type) {
        case "look":
          this.onDetail(item);
          break;
        case "cancel":
          this.onCancelDetail(item);
          break;
        default:
      }
    },
    /** **弹窗添加编辑*** */
    onDetail(item) {
      this.dialogInjectData = {
        item,
      };
      this.setDialog(true, "订单详情", orderDetailDailog, "90%");
    },
    onCancelDetail(item) {
      this.dialogInjectData = {
        item,
      };
      this.setDialog(true, "订单取消确认", cancelDetailDialog, "440px");
    },
    onDialogClose(result) {
      this.setDialog();
      if (result) {
        this.getList(this.listQuery);
      }
    },
  },
};
</script>

<style scoped>
.order-cancel-page {
}
</style>
