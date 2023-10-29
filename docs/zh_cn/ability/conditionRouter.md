### ConditionRouter
> 条件路由的目的是：支持nacos，consul，eureka注册中心，方面用户从spring-gateway迁移过来


/electron/example/example/queryExampleList
/electron/example

保持原样：/electron/example/example/queryExampleList
删除key值： /example/queryExampleList
重写key值：如果重写key为：conditionRouter 新path是 /conditionRouter/example/queryExampleList

/electron/example/example/queryExampleList?key=value


./etcdctl put /electron/default/AbilityRelation/APPLICATION~test~CONDITION_ROUTER~20 "{\"ability\":{\"conditions\":[{\"dataSpot\":\"URL\",\"isResources\":false,\"key\":\"/test\",\"rewrite\":\"UNCHANGED\",\"value\":\"/test-example\"}]},\"abilityTypeEnum\":\"CONDITION_ROUTER\",\"aiId\":11,\"aiName\":\"nacos条件路由\",\"arExplain\":\"NONE\",\"arId\":12,\"arRelationStatus\":1,\"createTime\":\"2022-03-19T13:21:37\",\"departmentId\":0,\"departmentName\":\"default\",\"environmentalId\":0,\"environmentalName\":\"default\",\"organizationId\":12,\"organizationName\":\"test-springmvc\",\"organizationTypeEnum\":\"APPLICATION\",\"teanId\":0,\"teanName\":\"default\",\"updateTime\":\"2022-04-19T23:07\"}"

./etcdctl put /electron/default/InstanceInfo/test {\"id\":1\,\"name\":\"test-springmvc\"\,\"applicationName\":\"test-springmvc\"\,\"applicationEnglishName\":\"test-springmvc\"\,\"networkAddress\":\"127.0.0.1\"\,\"port\":\"8800\"\,\"protocol\":\"http\"\,\"language\":\"cn\"\,\"nodeTypeEnum\":\"INSTANCE\"}

#### 作用

#### 参数

#### 测试
