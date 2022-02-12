export default {
  data() {
    return {
      dialog: {
        title: "",
        component: null,
        visible: false,
        width: "50%",
        top: "15vh",
      },
    };
  },
  methods: {
    /**
     * 弹窗方法
     * @param visible <Boolean> 是否显示
     * @param title String 弹窗标题
     * @param component 窗体内容组件
     * @param width 弹窗宽度
     * @param top margin-top 值
     */
    setDialog(
      visible = false,
      title = "",
      component = null,
      width = "50%",
      top = "15vh"
    ) {
      this.dialog = {
        visible,
        component,
        width,
        title,
        top,
      };
    },
  },
};
