import SimpleModeClass from '../../simpleMode'
import abilityModel from '../abilityModel'

var simpleMode = new SimpleModeClass("errorResult");

simpleMode.primaryKey(abilityModel.primaryKey())
simpleMode.setRequestInfo(abilityModel.requestInfos());
simpleMode.afterSupply(abilityModel.afterSupply());
simpleMode.beforesSupply(abilityModel.beforeSupply());

simpleMode.closeUpdate();

simpleMode.dataModeBehaviorBuild().title("code初始值").key("basicsCode").isColumn().updateBehavior().isHide(true);

var resultMode = {}
resultMode["JSON"] = "json格式"
resultMode["TEMPLATE"] = "字符串模板"

//simpleMode.dataModeBehaviorBuild().title("key值").key("resultMode").type("select").defaultValue("JSON").values(resultMode).isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("返回模式").key("resultMode").type("select").defaultValue("JSON").values(resultMode).isHide(true);
simpleMode.dataModeBehaviorBuild().title("codeKey").key("codeKey").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("messageKey").key("messageKey").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("connectKey").key("connectKey").isColumn().updateBehavior().isHide(true);
//simpleMode.dataModeBehaviorBuild().title("模板内容").key("templateContent").isColumn().updateBehavior().isHide(true);

export default  simpleMode.build();

