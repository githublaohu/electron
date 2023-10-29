import SimpleModeClass from '../../simpleMode'
import abilityModel from '../abilityModel'

var simpleMode = new SimpleModeClass("exampleRegister");

simpleMode.primaryKey(abilityModel.primaryKey())
simpleMode.setRequestInfo(abilityModel.requestInfos());
simpleMode.afterSupply(abilityModel.afterSupply());
simpleMode.beforesSupply(abilityModel.beforeSupply());

simpleMode.closeUpdate();

var slaveSimpleMode = new SimpleModeClass("exampleInfo");


slaveSimpleMode.setRequestInfo(abilityModel.requestInfos());
slaveSimpleMode.afterSupply(abilityModel.afterSupply());
slaveSimpleMode.beforesSupply(abilityModel.beforeSupply());

slaveSimpleMode.closeUpdate();

slaveSimpleMode.dataModeBehaviorBuild().title("网络地址").key("networkAddress").isColumn().updateBehavior().isHide(true);
slaveSimpleMode.dataModeBehaviorBuild().title("端口").key("port").isColumn().updateBehavior().isHide(true);
simpleMode.slaveModel(slaveSimpleMode.build())


export default  simpleMode.build();
