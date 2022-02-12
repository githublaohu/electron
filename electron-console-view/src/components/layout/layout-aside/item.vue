<script>
export default {
  name: "MenuItem",
  functional: true,
  props: {
    icon: {
      type: String,
      default: "",
    },
    title: {
      type: String,
      default: "",
    },
  },
  render(h, context) {
    const { icon, title } = context.props;
    const vNodes = [];
    if (icon) {
      vNodes.push(<he-iconfont icon-class={icon} />);
    }

    if (title) {
      vNodes.push(<span slot="title">{title}</span>);
    }
    return vNodes;
  },
};
</script>
