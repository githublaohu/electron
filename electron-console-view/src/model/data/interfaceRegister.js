import SimpleModeClass from '../simpleMode'


var simpleMode = new SimpleModeClass("user_mode");


simpleMode.addModelRequestInfo("/userInfo/addMode");
simpleMode.updateModelRequestInfo("/userInfo/queryMode");
simpleMode.queryFormModelRequestInfo("/lamp/electron/abilityInfo/queryAbilityInfoByForm");

var beforeSupply = [];

var aiId = simpleMode.getDataModeBehaviorBuild();

aiId.title("能力id").key("aiId").valueExpression("");
beforeSupply.push(aiId);

var aiParentId = simpleMode.getDataModeBehaviorBuild();
aiParentId.title("能力主id").key("aiParentId").isHide(true).isColumn().defaultValue(0);
beforeSupply.push(aiParentId);
simpleMode.beforesSupply(beforeSupply);

var afterSupply = [];


var createTime = simpleMode.getDataModeBehaviorBuild();
createTime.title("创建时间").key("create_time").isColumn().addBehavior().isHide(true).isUser(false).dataModeBehaviorBuild().updateBehavior().isDisabled();
afterSupply.push(createTime);

var updateTime = simpleMode.getDataModeBehaviorBuild()
updateTime.title("修改时间").key("update_time").isColumn().addBehavior().isHide(true).isUser(false).dataModeBehaviorBuild().updateBehavior().isDisabled();
afterSupply.push(updateTime);


simpleMode.afterSupply(afterSupply);
simpleMode.dataModeBehaviorBuild().title("index").key("index").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("faviconIco").key("faviconIco").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("能力名称").key("aiName").isColumn().updateBehavior().isHide(true);
simpleMode.dataModeBehaviorBuild().title("标签").key("aiLabel").isColumn().updateBehavior().isDisabled();
simpleMode.dataModeBehaviorBuild().title("能力类型").key("aiAbilityType").valueExpression("aiAbilityType");
simpleMode.dataModeBehaviorBuild().title("能力说明").key("aiDescription").isColumn();
simpleMode.dataModeBehaviorBuild().title("能力绑定次数").key("aiBindTimes").isHide(true).isColumn();
simpleMode.dataModeBehaviorBuild().title("能力是否启用").key("aiRelationStatus").isHide(true).isColumn();

var viewModel = simpleMode.build();


export default  viewModel;