import SimpleModeClass from '../../simpleMode'
import abilityModel from '../abilityModel'

var simpleMode = new SimpleModeClass("dataInjection");

simpleMode.setRequestInfo(abilityModel.requestInfos());
simpleMode.afterSupply(abilityModel.afterSupply());
simpleMode.beforesSupply(abilityModel.beforeSupply());

simpleMode.closeUpdate();


var slaveSimpleMode = new SimpleModeClass("dataInjectionInfo");


slaveSimpleMode.setRequestInfo(abilityModel.requestInfos());
slaveSimpleMode.afterSupply(abilityModel.afterSupply());
slaveSimpleMode.beforesSupply(abilityModel.beforeSupply());

slaveSimpleMode.closeUpdate();



var operateSpot = []
operateSpot["REQUEST"] = "请求时注入"
operateSpot["RESPONSE"] = "响应时注入"

slaveSimpleMode.dataModeBehaviorBuild().title("操作点").key("dataSpot").type("select").values(operateSpot).isColumn().updateBehavior().isHide(true);

var dataSopt = {}
dataSopt["URL"] = "请求地址"
dataSopt["QUERY"] = "请求条件"
dataSopt["HEADER"] = "请求头"
dataSopt["BODY"] = "请求体"
slaveSimpleMode.dataModeBehaviorBuild().title("条件点").key("dataSpot").type("select").values(dataSopt).isColumn().updateBehavior().isHide(true);
slaveSimpleMode.dataModeBehaviorBuild().title("key").key("key").isColumn().updateBehavior().isHide(true);
slaveSimpleMode.dataModeBehaviorBuild().title("value").key("value").isColumn().updateBehavior().isHide(true);




simpleMode.slaveModel(slaveSimpleMode.build())

export default  simpleMode.build();