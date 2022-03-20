import SimpleModeClass from '../simpleMode'

var simpleMode = new SimpleModeClass();

var beforeSupply = [];

simpleMode.closeQueryForm();

var aiId = simpleMode.getDataModeBehaviorBuild().title("能力id").key("aiId").valueExpression("");
beforeSupply.push(aiId);

var organizationId = simpleMode.getDataModeBehaviorBuild().title("组织id").key("organizationId").valueExpression("info.oiId");
beforeSupply.push(organizationId);

var organizationName = simpleMode.getDataModeBehaviorBuild().title("组织id").key("organizationName").valueExpression("info.oiName");
beforeSupply.push(organizationName);


var organizationType = simpleMode.getDataModeBehaviorBuild().title("组织类型").key("organizationTypeEnum").valueExpression("info.oiType");
beforeSupply.push(organizationType);

var aiParentId = simpleMode.getDataModeBehaviorBuild().title("能力主id").key("aiParentId").valueExpression("miandata.aiId").defaultValue(0);
beforeSupply.push(aiParentId);

var aiName = simpleMode.getDataModeBehaviorBuild().title("能力名称").key("aiName").isColumn().updateBehavior().isHide(true).dataModeBehaviorBuild();
beforeSupply.push(aiName);


var afterSupply = [];

var aiLabel = simpleMode.getDataModeBehaviorBuild().title("标签").key("aiLable").isColumn().updateBehavior().isDisabled().dataModeBehaviorBuild();
afterSupply.push(aiLabel);

var aiAbilityType = simpleMode.getDataModeBehaviorBuild().title("能力类型").key("aiAbilityType").valueExpression(["view.meta.type","bindingData.type"]).queryFormBehavior().isUser().dataModeBehaviorBuild();
afterSupply.push(aiAbilityType);

var aiDescription = simpleMode.getDataModeBehaviorBuild().title("能力说明").key("aiDescription").isColumn().queryFormBehavior().isHide(true).dataModeBehaviorBuild();
afterSupply.push(aiDescription);

var aiBindTimes = simpleMode.getDataModeBehaviorBuild().title("能力绑定次数").key("aiBindTimes").isHide(true).isColumn().isUser();
afterSupply.push(aiBindTimes);

var aiRelationStatus = simpleMode.getDataModeBehaviorBuild().title("能力是否启用").key("aiRelationStatus").isHide(true).isColumn().queryFormBehavior().isUser().dataModeBehaviorBuild();
afterSupply.push(aiRelationStatus);

var createTime = simpleMode.getDataModeBehaviorBuild();
createTime.title("创建时间").key("create_time").isColumn().addBehavior().isHide(true).isUser(false).dataModeBehaviorBuild()
        .updateBehavior().isDisabled().dataModeBehaviorBuild()
        .queryFormBehavior().isHide(true).dataModeBehaviorBuild();;
afterSupply.push(createTime);

var updateTime = simpleMode.getDataModeBehaviorBuild()
updateTime.title("修改时间").key("update_time").isColumn().isHide(true).addBehavior().isUser(false).dataModeBehaviorBuild()
        .updateBehavior().isDisabled().dataModeBehaviorBuild();
afterSupply.push(updateTime);


var hierarchyIntercept = SimpleModeClass.HierarchyIntercept()

var requestInfos = [];
var requestInfo = simpleMode.getRequestInfo();
requestInfo.modelName = "addModel";
requestInfo.url = "/abilityInfo/insertAbilityInfo";
requestInfo.dataIntercept = hierarchyIntercept;
requestInfos.push(requestInfo);

requestInfo = simpleMode.getRequestInfo();
requestInfo.modelName = "updateModel";
requestInfo.url = "";
requestInfo.dataIntercept = hierarchyIntercept;
requestInfos.push(requestInfo);

requestInfo = simpleMode.getRequestInfo();
requestInfo.modelName = "queryFormModel";
requestInfo.url = "/abilityInfo/queryAbilityInfoByForm";
requestInfo.dataIntercept = hierarchyIntercept;
requestInfos.push(requestInfo);

function AbilityModel(afterSupplyArray,beforeSupplyArray){
    this.__afterSupply = afterSupplyArray;
    this.__beforeSupply = beforeSupplyArray;
    this.__requestInfos;

    this.__clone = function(cloniyc){
        var newClone = []
        for(var index in cloniyc){
            newClone.push(cloniyc[index].clone());
        }
        return newClone;
    }

    this.afterSupply = function(){
        return this.__clone(this.__afterSupply);
    }

    this.beforeSupply = function(){
        return this.__clone(this.__beforeSupply);
    }

    this.requestInfos = function(){
        return  this.__clone(this.__requestInfos);
       // return  this.__clone(this.__requestInfos);
    }
}

var abilityModel = new AbilityModel(afterSupply , beforeSupply);

abilityModel.__requestInfos =  requestInfos;
export default  abilityModel;