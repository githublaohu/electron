import SimpleModeClass from '../../simpleMode'
import abilityModel from '../abilityModel'

var simpleMode = new SimpleModeClass("authentication");

simpleMode.setRequestInfo(abilityModel.requestInfos());
simpleMode.afterSupply(abilityModel.afterSupply());
simpleMode.beforesSupply(abilityModel.beforeSupply());

simpleMode.closeUpdate();
simpleMode.closeQueryForm();
simpleMode.dataModeBehaviorBuild().title("服务名").key("applicationName").isColumn().updateBehavior().isHide(true);


var dataSopt = {}
dataSopt["URL"] = "请求地址"
dataSopt["QUERY"] = "请求条件"
dataSopt["HEADER"] = "请求头"
simpleMode.dataModeBehaviorBuild().title("token存在点").key("tokenSpot").type("select").values(dataSopt).isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("token的key").key("tokenName").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("用户唯一标识字段").key("userKey").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("穿透存在点").key("redirectSpot").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("认证不通过重定向地址").key("redirectData").isColumn().updateBehavior().isHide(true);

var slaveSimpleMode = new SimpleModeClass("authenticationInfo");


slaveSimpleMode.setRequestInfo(abilityModel.requestInfos());
slaveSimpleMode.afterSupply(abilityModel.afterSupply());
slaveSimpleMode.beforesSupply(abilityModel.beforeSupply());

slaveSimpleMode.closeUpdate();
slaveSimpleMode.closeQueryForm();

slaveSimpleMode.dataModeBehaviorBuild().title("不认证地址").key("networkAddress").isColumn().updateBehavior().isHide(true);


simpleMode.slaveModel(slaveSimpleMode.build())

export default  simpleMode.build();

