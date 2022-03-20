import SimpleModeClass from '../../simpleMode'
import abilityModel from '../abilityModel'

var simpleMode = new SimpleModeClass("conditionRouter");

simpleMode.setRequestInfo(abilityModel.requestInfos());
simpleMode.afterSupply(abilityModel.afterSupply());
simpleMode.beforesSupply(abilityModel.beforeSupply());

simpleMode.closeUpdate();

var slaveSimpleMode = new SimpleModeClass("conditionInfo");


slaveSimpleMode.setRequestInfo(abilityModel.requestInfos());
slaveSimpleMode.afterSupply(abilityModel.afterSupply());
slaveSimpleMode.beforesSupply(abilityModel.beforeSupply());

slaveSimpleMode.closeUpdate();

var dataSopt = {}
dataSopt[0] = {value:"URL", label:"请求地址"}
dataSopt[1] = {value:"QUERY", label:"请求条件"}
dataSopt[2] = {value:"HEADER", label:"请求头"}

slaveSimpleMode.dataModeBehaviorBuild().title("条件点").key("dataSpot").type("selects").values(dataSopt).isColumn().updateBehavior().isHide(true);
slaveSimpleMode.dataModeBehaviorBuild().title("识别key").key("key").isColumn().updateBehavior().isHide(true);
slaveSimpleMode.dataModeBehaviorBuild().title("key值").key("value").isColumn().updateBehavior().isHide(true);
var rewrite = {}
rewrite[0] = {value:"UNCHANGED", label:"保持原样"}
rewrite[1] = {value:"DETELE_KEY", label:"删除key值"}
rewrite[2] = {value:"REWRITE_KEY", label:"重写key值"}
slaveSimpleMode.dataModeBehaviorBuild().title("是否从写rewrite").key("rewrite").type("selects").values(rewrite).isColumn().updateBehavior().isHide(true);
slaveSimpleMode.dataModeBehaviorBuild().title("从写值").key("rewriteValue").isColumn().updateBehavior().isHide(true);

//  /electron/example/example/queryExampleList
// /electron/example

simpleMode.slaveModel(slaveSimpleMode.build())

export default  simpleMode.build();

