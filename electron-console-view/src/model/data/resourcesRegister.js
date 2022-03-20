import SimpleModeClass from '../simpleMode'
import abilityModel from './abilityModel'

var simpleMode = new SimpleModeClass("resourcesRegister");

simpleMode.setRequestInfo(abilityModel.requestInfos());
simpleMode.afterSupply(abilityModel.afterSupply());
simpleMode.beforesSupply(abilityModel.beforeSupply());

simpleMode.closeUpdate();

simpleMode.dataModeBehaviorBuild().title("首页路径").key("index").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("Ico").key("faviconIco").isColumn().updateBehavior().isHide(true);


var slaveSimpleMode = new SimpleModeClass("resourcesInfo");


slaveSimpleMode.setRequestInfo(abilityModel.requestInfos());
slaveSimpleMode.afterSupply(abilityModel.afterSupply());
slaveSimpleMode.beforesSupply(abilityModel.beforeSupply());

slaveSimpleMode.closeUpdate();


slaveSimpleMode.dataModeBehaviorBuild().title("文件路径").key("remoteAddress").isColumn().updateBehavior().isHide(true);
slaveSimpleMode.dataModeBehaviorBuild().title("前缀").key("prefix").isColumn().updateBehavior().isHide(true);
slaveSimpleMode.dataModeBehaviorBuild().title("版本号").key("version").isColumn().updateBehavior().isHide(true);



simpleMode.slaveModel(slaveSimpleMode.build())

export default  simpleMode.build();

