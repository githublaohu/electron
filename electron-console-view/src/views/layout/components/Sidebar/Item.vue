<script>
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    }
  },
  render(h, context) {
    const { icon, title } = context.props
    const vnodes = []

    if (icon) {
      if (icon.startsWith('el-')) {
        vnodes.push(<i class={[icon, 'el-icon']} style='font-size: 30px;color:inherit'/>)
      } else {
        vnodes.push(<svg-icon icon-class={icon}/>)
      }
    }

    if (title) {
      vnodes.push(<div style="font-size: 12px;line-height: 12px;margin-top: 10px">{(title)}</div>)
    }

    return vnodes
  }
}
</script>
