import SimpleModeClass from '../simpleMode'
import modelManage from '../modelManage'


var simpleMode = new SimpleModeClass("abilityRelation");

simpleMode.addModelRequestInfo("/abilityRelation/bindAbilityRelation");
simpleMode.updateModelRequestInfo("/abilityRelation/unbindAbilityRelation");
simpleMode.queryFormModelRequestInfo("/abilityRelation/queryAbilityRelationListByOrganizationId");

simpleMode.closeUpdate();
simpleMode.closeQuery();
simpleMode.closeQueryForm();


var bindingView = simpleMode.__viewModel.bindingView;
var treeDatas = "[{\"childrenlist\":[{\"explain\":\"\",\"label\":\"统计\",\"relation\":[\"INTERFACE\",\"APPLICATION\"],\"resourcePath\":\"statistics\",\"type\":\"STATISTICS\"},{\"explain\":\"\",\"label\":\"请求记录\",\"relation\":[\"INTERFACE\",\"APPLICATION\"],\"resourcePath\":\"requestRecord\",\"type\":\"REQUESTRECORD\"},{\"explain\":\"\",\"label\":\"链路跟踪\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"linkTrack\",\"type\":\"LINKTRACK\"}],\"explain\":\"\",\"label\":\"收集\"},{\"childrenlist\":[{\"explain\":\"\",\"label\":\"条件路由\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"conditionRouter\",\"type\":\"CONDITIONROUTE\"}],\"explain\":\"\",\"label\":\"条件路由\"},{\"childrenlist\":[{\"explain\":\"\",\"label\":\"热备\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"hotStandby\",\"type\":\"HOTSTANDBY\"},{\"explain\":\"\",\"label\":\"隔离\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"partition\",\"type\":\"PARTITION\"},{\"explain\":\"\",\"label\":\"负载均衡\",\"relation\":[\"APPLICATION\",\"SYSTEM_DEFAULT\"],\"resourcePath\":\"loadBalancing\",\"type\":\"LOAD_BALANCING\"}],\"explain\":\"\",\"label\":\"路由\"},{\"childrenlist\":[{\"explain\":\"\",\"label\":\"秒杀\",\"relation\":[\"INTERFACE\"],\"resourcePath\":\"seckill\",\"type\":\"SECKILL\"},{\"explain\":\"\",\"label\":\"限流，熔断\",\"relation\":[\"APPLICATION\",\"INTERFACE\"],\"resourcePath\":\"traffisSafety\",\"type\":\"TRAFFISSAFETY\"},{\"explain\":\"\",\"label\":\"参数校验\",\"relation\":[\"INTERFACE\"],\"resourcePath\":\"paremVerifcation\",\"type\":\"PAREMVERIFCATION\"},{\"explain\":\"\",\"label\":\"认证\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"authentication\",\"type\":\"AUTHENTICATION\"}],\"explain\":\"\",\"label\":\"安全\"},{\"childrenlist\":[{\"explain\":\"\",\"label\":\"请求redis\",\"relation\":[\"INTERFACE\"],\"resourcePath\":\"redisRPC\",\"type\":\"REDISRPC\"},{\"explain\":\"\",\"label\":\"MessageMiddlewareRPC\",\"relation\":[\"INTERFACE\"],\"resourcePath\":\"messageMiddlewareRPC\",\"type\":\"MESSAGE_MIDDLEWARE_RPC\"},{\"explain\":\"\",\"label\":\"模板前置\",\"relation\":[\"INTERFACE\"],\"resourcePath\":\"template\",\"type\":\"TEMPLATE\"},{\"explain\":\"\",\"label\":\"接口集\",\"relation\":[\"INTERFACE\"],\"resourcePath\":\"interfaces\",\"type\":\"INTERFACES\"},{\"explain\":\"\",\"label\":\"接口设置\",\"relation\":[\"INTERFACE\"],\"resourcePath\":\"setupResult\",\"type\":\"SETUPRESULT\"}],\"explain\":\"\",\"label\":\"RPC配置\"},{\"childrenlist\":[{\"explain\":\"\",\"label\":\"异常返回\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"errorResult\",\"type\":\"ERRORRESULT\"},{\"explain\":\"\",\"label\":\"警告\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"alarm\",\"type\":\"ALARM\"}],\"explain\":\"\",\"label\":\"异常处理\"},{\"childrenlist\":[{\"explain\":\"\",\"label\":\"执行\",\"relation\":[\"INTERFACE\"],\"resourcePath\":\"invoking\",\"type\":\"INVOKING\"}],\"explain\":\"\",\"label\":\"invok处理\"},{\"childrenlist\":[{\"explain\":\"\",\"label\":\"配置根类\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"config\",\"type\":\"CONFIG\"}],\"explain\":\"\",\"label\":\"配置\"},{\"childrenlist\":[{\"explain\":\"\",\"label\":\"mock API\",\"relation\":[\"INTERFACE\"],\"resourcePath\":\"interfaceRegister\",\"type\":\"INTERFACERESGISTER\"},{\"explain\":\"\",\"label\":\"资源注册\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"resourcesRegister\",\"type\":\"RESOURCESRESGISTER\"},{\"explain\":\"\",\"label\":\"实例注册\",\"relation\":[\"APPLICATION\"],\"resourcePath\":\"exampleInfoRegister\",\"type\":\"EXAMPLEINFOREGISTER\"}],\"explain\":\"可以注册实例信息，静态资源\",\"label\":\"注册\"},{\"childrenlist\":[{\"explain\":\"\",\"label\":\"数据注入\",\"relation\":[\"INTERFACE\",\"APPLICATION\"],\"resourcePath\":\"dataInjection\",\"type\":\"DATAINJECTION\"}],\"explain\":\"\",\"label\":\"结果处理\"}]";
bindingView.treeDatas(JSON.parse(treeDatas));
bindingView.label("label");
bindingView.children("childrenlist");

var tableView = simpleMode.__viewModel.tableView
tableView.__deleteKey = "arRelationStatus";
tableView.__deleteValue = 1;


simpleMode.dataModeBehaviorBuild().title("绑定id").key("arId").valueExpression("arId").addBehavior().isUser(false).dataModeBehaviorBuild();
simpleMode.dataModeBehaviorBuild().title("组织id").key("organizationId").valueExpression("info.oiId");
simpleMode.dataModeBehaviorBuild().title("组织名").key("organizationName").valueExpression("info.oiName");
simpleMode.dataModeBehaviorBuild().title("组织别名").key("organizationEnglishName").valueExpression("info.oiEnglistName");
simpleMode.dataModeBehaviorBuild().title("组织类型").key("organizationTypeEnum").valueExpression("info.oiType");
simpleMode.dataModeBehaviorBuild().title("动作id").key("aiId").valueExpression("aiId");
simpleMode.dataModeBehaviorBuild().title("动作名").key("aiName").valueExpression("aiName");
simpleMode.dataModeBehaviorBuild().title("动作类型").key("abilityTypeEnum").valueExpression("bindingData.type").isColumn();
simpleMode.dataModeBehaviorBuild().title("协议配置类型").key("protocelConfigEnum").valueExpression("protocelConfigEnum").defaultValue("NONE").isColumn();
simpleMode.dataModeBehaviorBuild().title("说明").key("arExplain").defaultValue("NONE").isColumn()
simpleMode.dataModeBehaviorBuild().title("绑定状态").key("arRelationStatus").isHide(true).isColumn();

export default  simpleMode.build();
