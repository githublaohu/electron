export default {
  data() {
    return {
      page: {
        num: 1,
        size: 20,
        total: 0,
      },
    };
  },
  methods: {
    handleSizeChange(size) {
      this.page.num = 1;
      this.page.size = size;
      this.getList();
    },
    handleCurrentChange(page) {
      this.page.num = page;
      this.getList();
    },
    // 刷新
    handleRefresh() {
      this.getList();
    },
  },
};
