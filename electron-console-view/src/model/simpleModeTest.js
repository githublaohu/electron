var simpleModeClass = require('./simpleMode');

// 场景
/**
 * id
 * 用户名
 * 密码
 * 性别
 * 年龄
 * 邮箱
 * 创建时间
 * 修改时间
 * 
 */

try {
  var simpleMode = simpleModeClass()

  simpleMode.viewName("user_mode");

  simpleMode.addModelRequestInfo("/userInfo/addMode");
  simpleMode.updateModelRequestInfo("/userInfo/queryMode");
  simpleMode.queryFormModelRequestInfo("/userInfo/queryFormMode");

  var afterSupply = [];

  var createTime = simpleMode.getDataModeBehaviorBuild();
  createTime.title("创建时间").key("create_time").isColumn().addBehavior().isUser(false).dataModeBehaviorBuild().updateBehavior().isDisabled().dataModeBehaviorBuild();
  afterSupply.push(createTime);

  var updateTime = simpleMode.getDataModeBehaviorBuild()
  updateTime.title("修改时间").key("update_time").isColumn().addBehavior().isUser(false).dataModeBehaviorBuild().updateBehavior().isDisabled().dataModeBehaviorBuild();
  afterSupply.push(updateTime);

  var organization = simpleMode.getDataModeBehaviorBuild();
  organization.title("组织能力").key("organization_id").valueExpression("organization.organizationId");
  afterSupply.push(organization);

  simpleMode.afterSupply(afterSupply);




  simpleMode.dataModeBehaviorBuild().title("用户id").key("userId").addBehavior().isUser(false).dataModeBehaviorBuild().updateBehavior().isHide(true).dataModeBehaviorBuild();

  simpleMode.dataModeBehaviorBuild().title("用户名").key("userName").type("input")
    .defaultValue("admin").isColumn().updateBehavior().isDisabled(false);

  simpleMode.dataModeBehaviorBuild().title("密码").key("password").type("password")
    .updateBehavior().isUser(false).dataModeBehaviorBuild().queryBehavior().isUser(false).dataModeBehaviorBuild();

  var sexArray = [{
    value: "male",
    label: "男性"
  }, {
    value: "female",
    label: "女性"
  }];
  simpleMode.dataModeBehaviorBuild().title("性别").key("sex").type("radio").defaultValue("male").values(sexArray)
    .isColumn().queryBehavior().isDisabled(false);


  var ageArray = [{
    value: "1",
    label: "1岁"
  }, {
    value: "2",
    label: "2岁"
  }, {
    value: "3",
    label: "3岁"
  }, {
    value: "3",
    label: "3岁"
  }, {
    value: "4",
    label: "4岁"
  }]
  simpleMode.dataModeBehaviorBuild().title("年龄").key("age").type("select").defaultValue("1").values(ageArray).isColumn();

  var viewModel = simpleMode.build();
  var organization = {organizationId:1};
  viewModel.setViewContextData("organization", organization);
  viewModel.initialization();

  // 无法debug好烦
  console.log(simpleMode);

} catch (err) {
  console.log(err);
}
