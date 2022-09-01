import SimpleModeClass from '../../simpleMode'
import abilityModel from '../abilityModel'

var simpleMode = new SimpleModeClass("partition");
simpleMode.primaryKey(abilityModel.primaryKey())
simpleMode.setRequestInfo(abilityModel.requestInfos());
simpleMode.afterSupply(abilityModel.afterSupply());
simpleMode.beforesSupply(abilityModel.beforeSupply());

simpleMode.closeUpdate();

var dataSopt = {}
dataSopt["URL"] = "请求地址"
dataSopt["QUERY"] = "请求条件"
dataSopt["HEADER"] = "请求头"
dataSopt["BODY"] = "请求体"
dataSopt["USER_KEY"] = "用户ID"
dataSopt["USER_INFO"] = "用户信息"
simpleMode.dataModeBehaviorBuild().title("条件点").key("dataSpot").type("select").values(dataSopt).isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("识别key").key("key").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("key值").key("value").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("版本号").key("version").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("是否是静态资源").key("isResources").isColumn().updateBehavior().isHide(true);

var slaveSimpleMode = new SimpleModeClass("partitionInfo");

slaveSimpleMode.primaryKey(abilityModel.primaryKey())
slaveSimpleMode.setRequestInfo(abilityModel.requestInfos());
slaveSimpleMode.afterSupply(abilityModel.afterSupply());
slaveSimpleMode.beforesSupply(abilityModel.beforeSupply());

slaveSimpleMode.closeUpdate();



slaveSimpleMode.dataModeBehaviorBuild().title("网络地址").key("networkAddress").isColumn().updateBehavior().isHide(true);
slaveSimpleMode.dataModeBehaviorBuild().title("端口").key("port").isColumn().updateBehavior().isHide(true);




simpleMode.slaveModel(slaveSimpleMode.build())

export default  simpleMode.build();

