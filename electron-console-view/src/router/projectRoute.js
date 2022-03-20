import manage from '../model/modelManage'

import organizationview from '@/views/team/organization/index.vue'
import memberview from '@/views/team/member/index.vue'
import authorityView  from '@/views/organization-authority/index.vue'
import resourcesRegisterView  from '@/model/mainview/mainview.vue'
import abilityRelation from '@/model/binding/bindingview.vue'

//eval("exports._promise2= _promise2");
//eval("exports.default= _interopRequireDefault");

// 这里的代码需要重构一篇

function ProjectRoute() {
    this.route = [];

    this.components = {};

    this.routeInfo = function (routeInfo) {
        this.route.push(routeInfo.__routeConfig);
        if (routeInfo.__viewImport != null) {
            if (typeof routeInfo.__viewImport != "string") {
                this.components[routeInfo.__routeConfig["name"]] = routeInfo.__viewImport
            } else
                this.components[routeInfo.__routeConfig["name"]] = function () {
                    return exports._promise2.default.resolve().then(function () {
                        return exports.default(__webpack_require__(routeInfo.__viewImport));
                    });
                };
        }
        if (routeInfo["children"] != null) {
            for (var index in routeInfo.children) {
                var name = routeInfo.children[index].name;
                if (typeof routeInfo.__viewImport != string) {
                    this.components[name] = routeInfo.__viewImport
                } else
                    this.components[name] = function () {
                        return exports._promise2.default.resolve().then(function () {
                            return exports.default(__webpack_require__(routeInfo.children[index].__viewImport));
                        });
                    };
            }
        }
    }
}

var projectRoute = new ProjectRoute();


var ability = manage.routeInfo().path("/ability")
    .names("ability")
    .title("动作")
    .icon('el-icon-school')
    .build();

ability.createChildrenRoute().path("/ability/resourcesRegister")
    .viewImport("../model/mainview/mainview")
    .names("resourcesRegister")
    .title("静态资源代理")
    .icon('el-icon-school')
    .modelImport("./data/resourcesRegister.js")
    .build();

ability.createChildrenRoute().path("/ability/choice/loadBalancing").viewImport("../model/mainview/mainview").names("loadBalancing")
    .title("负载均衡").icon('el-icon-school').modelImport("./data/choice/loadBalancing.js").build();

ability.createChildrenRoute().path("/ability/choice/partition").viewImport("../model/mainview/mainview").names("partition")
    .title("资源隔离").icon('el-icon-school').modelImport("./data/choice/partition.js").build();

ability.createChildrenRoute().path("/ability/discern/condition").viewImport("../model/mainview/mainview").names("conditionRouter")
    .title("条件路由").icon('el-icon-school').modelImport("./data/discern/conditionRouter.js").build();

ability.createChildrenRoute().path("/ability/errer/errerResult").viewImport("../model/mainview/mainview").names("errerResult")
    .title("异常返回").icon('el-icon-school').modelImport("./data/errer/errerResult.js").build();

ability.createChildrenRoute().path("/ability/register/exampleRegister").viewImport("../model/mainview/mainview").names("exampleRegister")
    .title("实例注册").icon('el-icon-school').modelImport("./data/register/exampleRegister.js").build();

ability.createChildrenRoute().path("/ability/result/dataInjection").viewImport("../model/mainview/mainview").names("dataInjection")
    .title("数据注入").icon('el-icon-school').modelImport("./data/result/dataInjection.js").build();

ability.createChildrenRoute().path("/ability/security/authentication").viewImport("../model/mainview/mainview").names("authentication")
    .title("认证与鉴权").icon('el-icon-school').modelImport("./data/security/authentication.js").build();


var organization = manage.routeInfo().path("/organization")
    //.viewImport("./src/views/team/organization/index.vue")
    .viewImport(organizationview)
    .names("organization")
    .title("项目组织")
    .icon('el-icon-data')
    .build();

projectRoute.routeInfo(organization);

var member = manage.routeInfo().path("/member")
    //   .viewImport('./src/views/team/member/index.vue')
    .viewImport(memberview)
    .names("member")
    .title("组织成员")
    .icon('el-icon-school')
    .build();

projectRoute.routeInfo(member);

//   .viewImport('./src/views/team/member/index.vue')
var example = manage.routeInfo().path("/example").modelImport("./data/space/example.js").viewImport(resourcesRegisterView).names("example").title("实例列表").icon('el-icon-school').build();
projectRoute.routeInfo(example);

var interfaceRoute = manage.routeInfo().path("/interface").modelImport("./data/space/interface.js").viewImport(resourcesRegisterView).names('interface').title("接口信息").icon('el-icon-school').build();
projectRoute.routeInfo(interfaceRoute);

var authorityRoute = manage.routeInfo().path("/authority").viewImport(authorityView).names('authority').title("组织权限").icon('el-icon-school').build();
projectRoute.routeInfo(authorityRoute);

var resourcesRoute = manage.routeInfo().path("/ability/choice/loadBalancing").names("loadBalancing").viewImport(resourcesRegisterView)
                                                                                .title("负载功能").icon('el-icon-school')
                                                                                .meta("type","LOADBALANCING").build();
projectRoute.routeInfo(resourcesRoute);

var partitionRoute = manage.routeInfo().path("/ability/choice/partition").names("partition").viewImport(resourcesRegisterView)
                                                                                .title("资源隔离").icon('el-icon-school')
                                                                                .meta("type","PARTITION").build();
projectRoute.routeInfo(partitionRoute);


var conditionRoute = manage.routeInfo().path("/ability/discern/condition").names("conditionRouter").viewImport(resourcesRegisterView)
                                                                                .title("条件路由").icon('el-icon-school')
                                                                                .meta("type","CONDITIONROUTE").build();
projectRoute.routeInfo(conditionRoute);


var errorRoute = manage.routeInfo().path("/ability/errer/errerResult").names("errerResult").viewImport(resourcesRegisterView)
                                                                                .title("异常返回").icon('el-icon-school')
                                                                                .meta("type","ERRERRESULT").build();
projectRoute.routeInfo(errorRoute);

var dataInjectionRoute = manage.routeInfo().path("/ability/register/exampleRegister").names("exampleRegister").viewImport(resourcesRegisterView)
                                                                                .title("实例注入").icon('el-icon-school')
                                                                                .meta("type","EXAMPLEINFOREGISTER").build();
projectRoute.routeInfo(dataInjectionRoute);

var dataInjectionRoute = manage.routeInfo().path("/ability/result/dataInjection").names("dataInjection").viewImport(resourcesRegisterView)
                                                                                .title("数据注入").icon('el-icon-school')
                                                                                .meta("type","DATAINJECTION").build();
projectRoute.routeInfo(dataInjectionRoute);

var authenticationRoute = manage.routeInfo().path("/ability/security/authentication").names("authentication").viewImport(resourcesRegisterView)
                                                                                .title("认证与鉴权").icon('el-icon-school')
                                                                                .meta("type","AUTHENTICATION").build();
projectRoute.routeInfo(authenticationRoute);


var abilityRelationInfo = manage.routeInfo().path("/abilityRelation").names("abilityRelation").viewImport(abilityRelation)
                                        .title("策略绑定").icon('el-icon-school').modelImport("./data/abilityRelation.js").build();
projectRoute.routeInfo(abilityRelationInfo);

export default projectRoute;

