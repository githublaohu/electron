# 动作绑定组件

该组件已经在全局注册挂载，有两种使用方式
1. 在需要用的文件下直接使用 <action-bind res='actionBind'  />
2. 使用指令式，this.$actionBind(info)

需要注意的是，两种使用方式打开组件都是调用组件内的 handleCreate() 函数，传的参数必须是 { id：xxx, …… } 这种类型
