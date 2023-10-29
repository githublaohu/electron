import SimpleModeClass from '../../simpleMode'


var simpleMode = new SimpleModeClass("interface");


simpleMode.queryFormModelRequestInfo("/lamp/electron/instanceAndInterface/queryNodeBaseListByFrom");

simpleMode.closeAdd();
simpleMode.closeUpdate();
simpleMode.closeQuery();

simpleMode.dataModeBehaviorBuild().title("组织id").key("organizationId").valueExpression("info.oiId");
simpleMode.dataModeBehaviorBuild().title("组织英文名").key("organizationEnglishName").valueExpression("info.oiEnglishName");
simpleMode.dataModeBehaviorBuild().title("组织类型").key("organizationTypeEnum").defaultValue("INTERFACE");
simpleMode.dataModeBehaviorBuild().title("网络地址").key("networkAddress").isColumn();
simpleMode.dataModeBehaviorBuild().title("端口").key("port").isColumn();
simpleMode.dataModeBehaviorBuild().title("通讯协议").key("protocol").isColumn();
simpleMode.dataModeBehaviorBuild().title("语言").key("language").isColumn();
simpleMode.dataModeBehaviorBuild().title("PRC框架").key("RPCType").isColumn();
simpleMode.dataModeBehaviorBuild().title("服务版本").key("version").isColumn();
simpleMode.dataModeBehaviorBuild().title("上线时间").key("gaterDate").isColumn();
simpleMode.dataModeBehaviorBuild().title("下线时间").key("gaterDate").isColumn();

export default  simpleMode.build();

