import SimpleModeClass from '../../simpleMode'
import abilityModel from '../abilityModel'

var simpleMode = new SimpleModeClass("loadBalancing");
simpleMode.primaryKey(abilityModel.primaryKey())
simpleMode.setRequestInfo(abilityModel.requestInfos());
simpleMode.afterSupply(abilityModel.afterSupply());
simpleMode.beforesSupply(abilityModel.beforeSupply());

simpleMode.closeUpdate();
simpleMode.closeQueryForm();

var dataSopt = []
dataSopt[0] = {value:"IP_HASH", label:"网络地址hash"}
dataSopt[1] = {value:"POLLING", label:"轮训负载"}
dataSopt[2] = {value:"RANDOM", label:"随机负载"}

simpleMode.dataModeBehaviorBuild().title("负载策略").key("name").type("selects").values(dataSopt).defaultValue("POLLING")
        .isColumn().updateBehavior().isHide(true).dataModeBehaviorBuild();



export default  simpleMode.build();

