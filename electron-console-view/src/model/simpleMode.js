//  Copyright (c) [Year] [name of copyright holder]
//  [Software Name] is licensed under Mulan PSL v2.
//  You can use this software according to the terms and conditions of the Mulan PSL v2.
//  You may obtain a copy of Mulan PSL v2 at:
//           http://license.coscl.org.cn/MulanPSL2
//  THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
//  EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
//  MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
//  See the Mulan PSL v2 for more details.

import {request} from './requestModel'


// 视图
//        模型[ 新增，修改，删除，查询列表，查询]
//        网络[ 新增，修改，删除，查询列表，查询]
//       校验[ 新增，修改，删除，查询列表，查询]
//       执行结果[ 新增，修改，删除，查询列表，查询]
//       模型视图[主， 新增，修改]（不在这里）
//                  模型类型：
//                                         单模型
//                                         主从模型
//                  关联
//                  联动
//       单模型[新增，修改，删除，列表查询，查询，分页，查询表单]
//       主从模型[新增，修改，删除，列表查询，查询，分页(可无)，查询表单（可无）]

function objectClone(copied){
    var copy = {};
    for( var key in copied){
        copy[key] = null;
    }
    var newCopied = JSON.parse( JSON.stringify( copied));
    for( var key in newCopied){
        copy[key] = newCopied[key];
    }
    return copy;
}

// 数据模型
// 修改需要去掉默认值
// form表达字段，都需要数据行为
function DataModeBehavior(dataModeBehaviorBuild) {

   
    //  字段名
    this.__key;

    // 字段的form类型
    this.__type = "input";

    //  form字段描述
    this.__title;

    // 默认值
    this.__defaultValue;

    // 
    this.__values;

    this.__isBehavior = true;
    // 隐藏
    this.__isHide = false;
    // 不可编辑
    this.__isDisabled = false;
    // 是否使用
    this.__isUser = true;
    // 判断字段
    this.__if = null;
    // 判断条件
    this.__ifValue = null;

    this.__rules = [];

    // 取值的表达式
    this.__valueExpression;

    this.defaultValue = function (defaultValue) {
        this.__defaultValue = defaultValue;
        return this;
    }

    this.isBehavior = function (isBehavior) {
        this.__isBehavior = isBehavior;
        return this;
    }

    this.isDisabled = function (isDisabled) {
        this.__isDisabled = isDisabled;
        return this;
    }

    this.isHide = function (isHide) {
        this.__isHide = isHide;
        return this;
    }

    this.isUser = function (isUser) {
        this.__isUser = isUser;
        return this;
    }

    this.if = function (ifs) {
        this.__if = ifs;
        return this;
    }

    this.ifValue = function (ifValue) {
        this.__ifValue = ifValue;
        return this;
    }

    this.rule = function (rule) {
        this.__rules.add(rule);
        return this;
    }
    this.dataModeBehaviorBuild = function () {
        return dataModeBehaviorBuild;
    }

    this.valueExpression = function(valueExpression){
        this.__valueExpression = valueExpression;
    }

    this.clone = function(){
        var dataModeBehavior = new DataModeBehavior();
        dataModeBehavior.__key = this.__key;
        dataModeBehavior.__type = this.__type;
        dataModeBehavior.__title=this.__title;
        dataModeBehavior.__defaultValue=this.__defaultValue;
        dataModeBehavior.__values=this.__values;
        dataModeBehavior.__isBehavior = this.__isBehavior;
        // 隐藏
        dataModeBehavior.__isHide =this.__isHide;
        // 不可编辑
        dataModeBehavior.__isDisabled =this.__isDisabled;
        // 是否使用
        dataModeBehavior.__isUser = this.__isUser;
        // 判断字段
        dataModeBehavior.__if =this.__if ;
        // 判断条件
        dataModeBehavior.__ifValue =this.__ifValue;
        dataModeBehavior.__rules =this.__rules;
        dataModeBehavior.__valueExpression =  this.__valueExpression;
        return dataModeBehavior;
    }
}


function createrBehaviorName(behavior, name) {
    return (name == null ? behavior + "-form-" : name + "-" + behavior + "-form-") + Date.now()
}

var behaviorMap = {
    "add": "addModel",
    "update": "updateModel",
    "queryForm": "queryFormModel",
    "query": "queryModel",
    "delete": "deleteModel"
};

function DataIntercept(){

    this.dataIntercept = function(data){
        return data;
    }

    this.resultIntercept = function(data){
        return data;
    }

}
function DeleteNullValueDataIntercept(){

    this.dataIntercept = function(data){
        for(var key in data){
            var value = data[key];
            if(value == null || value === undefined ){
                    delete data[key];
            }
        }
        return data;
    }

    this.resultIntercept = function(data){
        return data;
    }

}

function HierarchyIntercept(){

    this.dataIntercept = function(data){
        for(var index in data){
            if(index.indexOf('.') == -1){
                continue;
            }
            var keyArray = index.split('.');
            var value = data;
            for(var keyIndex in keyArray ){
                var key =  keyArray[keyIndex]
                if(keyIndex == (keyArray.length - 1)){
                    value[key] = data[index]
                    delete data[index]
                }else{   
                    value = value[key];
                    if(value == null){
                        data[key] = {}
                    }
                }
            }
        }
        return data;
    }

    this.resultIntercept = function(data){
        for(var index in data){
            var value = data[index]
            if(typeof value == "object"){
                __resultHierarchy(index , value , data)
                delete data[index]
            }
        }
        return data;
    }

    this.__resultHierarchy = function(keyPrefix , object, superior){
        for(var index in object ){
            var key = keyPrefix +"." + index;
            var value = object[index];
            if(typeof value == "object"){
                __resultHierarchy(key , value , superior)
            }else{
                superior[  key ] = value;
            }
        }
    }
}

function RequestInfo(url,metod){

    this.modelName;

    this.method = metod == null ? "POST": metod;

    this.url = url;

    this.headers;

    this.deleteNullValueDataIntercept = new DeleteNullValueDataIntercept();

    this.dataInterceptObject = new DataIntercept();

    this.dataIntercept = function(data){
        data = this.deleteNullValueDataIntercept.dataIntercept(data);
        return  this.dataInterceptObject.dataIntercept(data);
    }

    this.resultIntercept = function(data){
        return this.dataInterceptObject.resultIntercept(data);
    }

    this.clone = function(){
        var requestInfo = new RequestInfo()
        requestInfo.modelName = this.modelName;
        requestInfo.method = this.method;
        requestInfo.url =this.url;
        requestInfo.headers = this.header;
        requestInfo.dataInterceptObject = this.dataInterceptObject;
        return requestInfo;
    }
}

function ModeBehavior(behavior,viewName,viewModel) {

    this.behavior = behavior;

    this.viewName = viewName;

    this.viewModel = viewModel;
    /**
     * 
     */
    this.bebaviorName = behaviorMap[behavior];

    this.refsName = createrBehaviorName(behavior,viewName)

    this.isUser = true;

    this.isVisible = false;

    // DataModeBehavior
    this.dataModeBehavior = [];

    this.rules = {};

    // view form 需要一个formData
    this.formData = {};

    // formData 默认值
    this.defaultFormData = {};

    this.__requsetInfo;

    this.__vue;

    // RequestInfo
    this.requestModel;

    var thiz = this;


    this.clone = function(viewModel){
        var modeBehavior = new ModeBehavior(this.behavior , this.viewName,viewModel);
        modeBehavior.isUser = this.isUser;
        modeBehavior.isVisible = this.isVisible ? true:false;

        for(var index in this.dataModeBehavior){
            modeBehavior.dataModeBehavior.push(this.dataModeBehavior[index]);
        }
        modeBehavior.requestModel = this.requestModel;
        modeBehavior.rules = this.rules;
        modeBehavior.formData = objectClone(this.formData);
        modeBehavior.defaultFormData =objectClone( this.defaultFormData);
        modeBehavior.__requsetInfo = this.__requsetInfo == null ? null : this.__requsetInfo.clone();
        return modeBehavior;
    }

    this.resetFormData = function(){
        thiz.formData =  objectClone(thiz.defaultFormData)
    }

    this.showView = function(row){
        thiz.isVisible = true;
        thiz.viewModel.slaveModel.setViewContextData("miandata", row);
        thiz.viewModel.slaveModel.initialization();
        thiz.viewModel.slaveModel.queryFormModel.request();

    }

    this.request = function(){
        var vue = thiz. __vue;
        var isValidate= false;
        var ref = vue.$refs[thiz.refsName]
        if(ref != null){
            ref.validate(valid => {
                isValidate = valid;
            });
         }else{
            isValidate = true;
         }
        if(!isValidate){
            return;
        }
        var bebavior = vue[thiz.bebaviorName];
        var request = {};
        request["url"] = thiz.__requsetInfo.url;
        request["method"] = thiz.__requsetInfo.method;

        var header = {};
        header["Content-type"] = "application/json";

        if(thiz.behavior == "queryForm"){
            //header[""] = JSON.stringify(vue.tableView.__pagination);
        }

        request["headers"] = header;
        request["data"] = thiz.__requsetInfo.dataIntercept( thiz.formData );

        thiz.requestModel(request).then(res => {
            if(res.code != 200){
                thiz.requestFail(this,res);
                return;
            }
            thiz.resetFormData();
            if(bebavior.behavior === "add"){
                //vue.requestSuccess(res);
                bebavior.isVisible = false;
                thiz.viewModel.tableView.resetPagination();
                thiz.viewModel.queryFormModel.request();
            } if(bebavior.behavior === "update"){
                thiz.requestSuccess(res);
                bebavior.isVisible = false;
            }else{
                thiz.viewModel.tableView.__columnsData = thiz.__requsetInfo.resultIntercept( res.data ) ;
            }
        },res=>{
            console.log(res);
        });

    }

}

/**
 * 表格视图对象
 */
function TableView() {

    this.__isPagination = true;

    this.__pagination = {
        currentPage: 1,
        pageSize: this.__pageSize,
        total: 0
    };

    this.__columns = [];

    this.__columnsData = [];

    this.__userTables = true;

    this.__delete = true;

    this.__deleteKey = null;

    this.__deleteValue = null

    this.__activation= true;

    this.__update = true;

    this.__query = true;

    this.__key = null;

    this.__pageSize = 10;

    this.clone = function(){
        var tableView = new TableView();
        tableView.__isPagination = this.__isPagination;
        tableView.__pagination = JSON.parse( JSON.stringify( this.__pagination));
        tableView.__columns = this.__columns;
        tableView.__userTables = this.__userTables;
        tableView.__delete = this.__delete;
        tableView.__deleteKey = this.__deleteKey;
        tableView.__deleteValue = this.__deleteValue;
        tableView.__activation = this.__activation;
        tableView.__update = this.__update;
        tableView.__query = this.__query;
        tableView.__key = this.__key;
        tableView.__pageSize = this.__pageSize;
        return tableView;
    }

    this.deleteView = function(row){
        return this.__delete && this.__deleteValue === row[this.__deleteKey]
    }

    this.activationView = function(row){
        return this.__delete && this.__activation && this.__deleteValue != row[this.__deleteKey]
    }

    this.setPagination = function(pagination){
        this.__pagination = pagination;
    }

    this.resetPagination = function(){
        this.__pagination = {
            currentPage: 1,
            pageSize: this.__pageSize,
            total: 0
        };
    }


}

function BindingTree(){

    this.__id;

    this.__name;

    this.__label;

    this.__children = [];

    this.__isChildren;

    this.__data;
}

/**
 *  
 * 
 * 
 * 
 */
function  BindingView(){

    this.__isTree = false;

    this.__treeData = []

    this.__mapper =  {
        children: 'children',
        label: 'label'
      };

    this.__rowKey = "bindingData"

    this.treeDatas = function(treeData){
        this.__treeData = treeData;
        return this;
    }

    this.data = function(treeData){
        this.__treeData.push(treeData);
        return this;
    }

    this.label = function(label){
        this.__mapper["label"] = label;
        return this;
    }

    this.children = function(children){
        this.__mapper["children"] = children;
        return this;
    }

    this.mapper = function(mapper){
        this.__mapper  = mapper;
        return this;
    }

    this.rowKey = function(rowKey){
        this.__rowKey = rowKey;
        return this;
    }

    this.clone = function(){
        var bindingView = new BindingView();
        bindingView.__isTree = this.__isTree;
        bindingView.__mapper = this.__mapper;
        bindingView.__rowKey = this.__rowKey;
        bindingView.__treeData = this.__treeData;
        return bindingView;
    }
}

/**
 * 主对象
 * 
 */
function ViewModel(viewName) {

    this.viewName = viewName;

    this.viewTitle;

    this.addModel = new ModeBehavior("add",viewName,this);

    this.updateModel = new ModeBehavior("update",viewName,this);

    this.queryFormModel = new ModeBehavior("queryForm",viewName,this);

    this.queryModel = new ModeBehavior("query",viewName,this);

    this.tableView = new TableView();

    this.bindingView = new BindingView();

    this.viewContext = {}

    this.assemblyContext;

    this.requestModel;

    this.__vue;

    this.slaveName;

    this.slaveModel;

    this.vue = function(vue){
        this.__vue = vue ;
        this.addModel.__vue = vue;
        this.updateModel.__vue = vue;
        this.queryFormModel.__vue = vue;
        this.queryModel.__vue = vue;
        if( this.slaveModel != null){
            this.slaveModel.vue(vue);
        }
    }

    this.clone = function(){
        var viewModel = new ViewModel();
        viewModel.viewName = this.viewName;
        viewModel.viewTitle = viewModel.viewTitle;
        viewModel.addModel = this.addModel.clone(viewModel);
        viewModel.updateModel = this.updateModel.clone(viewModel);
        viewModel.queryFormModel = this.queryFormModel.clone(viewModel);
        viewModel.queryModel  = this.queryModel.clone(viewModel);
        viewModel.tableView = this.tableView.clone();
        viewModel.requestModel = this.requestModel;
        viewModel.slaveName = this.slaveName;
        viewModel.bindingView= this.bindingView.clone();
        if( this.slaveModel  != null){
            viewModel.slaveModel = this.slaveModel.clone();
        }
        return viewModel;
    }

    this.initialization = function(){
        var modelArray = [ this.addModel , this.updateModel ,  this.queryFormModel ];
        for(var index in modelArray){
            this.toInitialization(modelArray[index]);
        }
    }

    this.appointInitialization = function(modelName,data){
        this.toInitialization(this[modelName],data);
    }

    this.toInitialization = function(modeBehavior, data){
        var dataModeBehaviors =  modeBehavior.dataModeBehavior;
        for( var dataIndex in dataModeBehaviors){
            var dataModeBehavior = dataModeBehaviors[dataIndex];
            if(dataModeBehavior.__valueExpression == null || dataModeBehavior.__valueExpression == ""){
                continue;
            }

            if (typeof dataModeBehavior.__valueExpression == "string") {
                this.valueExpression(modeBehavior,dataModeBehavior,dataModeBehavior.__valueExpression,data);
            }else{
                for(var valueIndex in dataModeBehavior.__valueExpression){
                    if(this.valueExpression(modeBehavior,dataModeBehavior,dataModeBehavior.__valueExpression[valueIndex],data)){
                        break;
                    }
                }
            }
        }
    }

    this.valueExpression = function( modeBehavior , dataModeBehavior ,valueExpression,data){
        var expression = valueExpression.split(".");
        var value = data != null ? data : this.viewContext;
        var  nullValue = false;
        for(var expressionIndex in expression){
            var expressionValue = expression[ expressionIndex]
            if(value == undefined ){
                console.log( dataModeBehavior );
                nullValue = true;
                return false;
            }
            value = value[expressionValue];
        }
        if( nullValue || value == undefined ){
            value = dataModeBehavior.__defaultValue;
            //return false;
        }
        modeBehavior.defaultFormData[dataModeBehavior.__key]  = value;
        modeBehavior.formData[dataModeBehavior.__key]  = value;
        return nullValue;
    }



    this.setViewContextData = function(key, value){
        this.viewContext[key] = value;
        if( this.slaveModel  != null){
            this.slaveModel.setViewContextData(key, value);
        }
    }

    this.deleteViewContextData = function(key){
        delete   this.viewContext[key];
        if( this.slaveModel  != null){
            this.slaveModel.deleteViewContextData(key);
        }
    }

    this.setAssemblyContext = function(assembly){
        this.assemblyContext = assembly;
    }
    
    this.requestFail = function(vue,res){
        const h = vue.$createElement;
        vue.$message({
        message: h('p', null, [
                h('span', null, '内容可以是 '),
                h('i', { style: 'color: teal' }, 'VNode')
            ])
        });
    }

    this.requestSuccess = function(vue,res){
        const h = vue.$createElement;
        vue.$message({
        message: h('p', null, [
                h('span', null, '内容可以是 '),
                h('i', { style: 'color: teal' }, 'VNode')
            ])
        });
    }
}


/**
 *   构建viewmodel的代码
 */
function DataModeBehaviorBuild() {

    this.__isColumn = false;

    this.__rules = [];

    this.__addBehavior = new DataModeBehavior(this);

    this.__updateBehavior = new DataModeBehavior(this);

    this.__queryFormBehavior = new DataModeBehavior(this);

    this.__queryBehavior = new DataModeBehavior(this);


    this.key = function (key) {
        this.setValue("__key", key);
        return this;
    }

    this.title = function (title) {
        this.setValue("__title", title);
        return this;
    }

    this.type = function (type) {
        this.setValue("__type", type);
        return this;
    }

    this.defaultValue = function (defaultValue) {
        this.setValue("__defaultValue", defaultValue);
        return this;
    }

    this.values = function (values) {
        this.setValue("__values", values);
        return this;
    }

    this.isBehavior = function (isBehavior) {
        this.setValue("__isBehavior",isBehavior);
        return this;
    }

    this.isDisabled = function (isDisabled) {
        this.setValue("__isDisabled",isDisabled);
        return this;
    }

    this.isUser = function (isUser) {
        this.setValue("__isUser",isUser);
        return this;
    }

    this.if = function (ifs) {
        this.setValue("__if",ifs);
        return this;
    }

    this.ifValue = function (ifValue) {
        this.setValue("__ifValue",ifValue);
        return this;
    }

    this.isColumn = function () {
        this.__isColumn = true;
        return this;
    }

    this.rule = function (rule) {
        this.setValue("__rules",rule);
        return this;
    }

    this.addBehavior = function () {
        return this.__addBehavior;
    }

    this.updateBehavior = function () {
        return this.__updateBehavior;
    }

    this.queryFormBehavior = function () {
        return this.__queryFormBehavior;
    }

    this.queryBehavior = function () {
        return this.__queryBehavior;
    }

    this.isDisabled = function () {
        this.setValue("__isDisabled", true);
        return this;
    }

    this.isBehavior = function () {
        this.setValue("__isBehavior",hide);
        return this;
    }

    this.isHide = function (hide) {
        this.setValue("__isHide",hide);
        return this;
    }

    this.valueExpression = function(valueExpression){
        this.isHide(true);
        this.setValue("__valueExpression",valueExpression);
        return this;
    }

    this.setValue = function (key, value) {
        this.__addBehavior[key] = value;
        this.__updateBehavior[key] = value;
        this.__queryBehavior[key] = value;
        this.__queryFormBehavior[key] = value;
    }

    this.clone = function(){
        var dataModeBehaviorBuild = new DataModeBehaviorBuild();
        dataModeBehaviorBuild.__isColumn = this.__isColumn;
        dataModeBehaviorBuild.__rules = this.__rules;
    
        dataModeBehaviorBuild.__addBehavior = this.__addBehavior.clone();
        dataModeBehaviorBuild.__updateBehavior =this.__updateBehavior.clone();
        dataModeBehaviorBuild.__queryFormBehavior = this.__updateBehavior.clone();
        dataModeBehaviorBuild.__queryBehavior = this.__queryBehavior.clone();
        return dataModeBehaviorBuild;
    }
}

/**
 * 从这个对象看起，尤其是build方法
 * @returns 
 */
function Mode(viewName) {

    this.__viewModel = new ViewModel(viewName);

    this.__beforeSupply = [];

    this.__afterSupply = [];

    this.__dataModeBehaviorBuilds = [];

    this.title = function ( title) {
        this.__viewModel.viewTitle = title;
        return this;
    }

    this.slaveName = function(slaveName){
        this.__viewModel.slaveName = slaveName;
        return this;
    }

    this.slaveModel = function(slaveModel){
        this.__viewModel.slaveName = slaveModel.viewName;
        this.__viewModel.slaveModel = slaveModel;
    }

    this.addModelRequestInfo= function(url , method){
        this.__viewModel.addModel.__requsetInfo = new RequestInfo(url , method);
    }

    this.queryModelRequestInfo= function(url , method){
        this.__viewModel.queryFormModel.__requsetInfo =  new RequestInfo(url , method);
    }

    this.queryFormModelRequestInfo= function(url , method){
        this.__viewModel.queryFormModel.__requsetInfo =  new RequestInfo(url , method);
    }

    this.updateModelRequestInfo= function(url , method){
        this.__viewModel.updateModel.__requsetInfo =  new RequestInfo(url , method);
    }

    this.setRequestInfo = function(requestInfos){
        for(var index in requestInfos ){
            var requestInfo = requestInfos[index]
            this.__viewModel[requestInfo.modelName].__requsetInfo = requestInfo;
        }
    }

    this.requestModel = function(requestModel){
        this.__viewModel.requestModel = requestModel;
    }

    this.primaryKey = function () {

    }

    
    this.beforesSupply = function (beforesSupply) {
        this.__beforeSupply = beforesSupply;
        return this;
    }
    
    // 
    this.afterSupply = function (afterSupply) {
        this.__afterSupply = afterSupply;
        return this;
    }

    // 关闭添加功能
    this.closeAdd = function () {
        this.__viewModel.addModel.isUser = false;
        return this;
    }

    // 关闭修改功能
    this.closeUpdate = function () {
        this.__viewModel.updateModel.isUser = false;
        return this;
    }

    // 关闭分页功能
    this.closePage = function () {
        this.__viewModel.tableView.__isPagination = false;
        return this;
    }

    // 关闭查询form表单
    this.closeQueryForm = function () {
        this.__viewModel.queryFormModel.isUser = false;
        return this;
    }

    // 关闭查询功能
    this.closeQuery = function () {
        this.__viewModel.queryModel.isUser = false;
        return this;
    }

    // 关闭表格
    this.closeTables = function () {
        this.__viewModel.userTables = false;
        return this;
    }

    this.dataModeBehaviorBuild = function () {
        var dataModeBehaviorBuild = new DataModeBehaviorBuild();
        this.__dataModeBehaviorBuilds.push(dataModeBehaviorBuild);
        return dataModeBehaviorBuild;
    }

    this.getDataModeBehaviorBuild = function () {
        return new DataModeBehaviorBuild();
    }

    this.getRequestInfo = function(){
        return new RequestInfo();
    }


    this.createRequestModel = function () {
        return new RequestModel();
    }

    this.build = function () {
        // 处理之前
        this.handleDataModeBehavior(this.__beforeSupply);
        this.handleDataModeBehavior(this.__dataModeBehaviorBuilds);
        this.handleDataModeBehavior(this.__afterSupply);

        if(this.__viewModel.requestModel == null){
            if(Mode.defaultRequestModel == null){
                // 异常
            }
            this.__viewModel.requestModel = Mode.defaultRequestModel;
            
        }
        this.__viewModel.addModel.requestModel = Mode.defaultRequestModel;
        this.__viewModel.updateModel.requestModel = Mode.defaultRequestModel;
        this.__viewModel.queryFormModel.requestModel = Mode.defaultRequestModel;

        

        return this.__viewModel;
    }

    this.handleDataModeBehavior = function (dataModeBehaviorBuildArray) {
        for (var index in dataModeBehaviorBuildArray) {
            var dataModeBehaviorBuild = dataModeBehaviorBuildArray[index];
            this.createDataModeBehavior(dataModeBehaviorBuild.__addBehavior, this.__viewModel.addModel);
            this.createDataModeBehavior(dataModeBehaviorBuild.__updateBehavior, this.__viewModel.updateModel);
            this.createDataModeBehavior(dataModeBehaviorBuild.__queryFormBehavior, this.__viewModel.queryFormModel);
            this.createDataModeBehavior(dataModeBehaviorBuild.__queryBehavior, this.__viewModel.queryModel);
            if (dataModeBehaviorBuild.__isColumn) {
                this.__viewModel.tableView.__columns.push({
                    title: dataModeBehaviorBuild.__addBehavior.__title,
                    key: dataModeBehaviorBuild.__addBehavior.__key
                });
            }
        }
    }

    this.createDataModeBehavior = function (behavior, modeBehavior) {
        if(behavior == null){
            console.log(modeBehavior);
        }
        if(behavior.isUser == false){
            return;
        }
        modeBehavior.dataModeBehavior.push(behavior);
        if(behavior.__defaultValue == null){
            //return;
        }
        modeBehavior.defaultFormData[behavior.__key]  = behavior.__defaultValue;
        modeBehavior.formData[behavior.__key]  = behavior.__defaultValue;
    }

    return this;
}

Mode.defaultRequestModel = request;

Mode.HierarchyIntercept = HierarchyIntercept

Mode.BindingTree = BindingTree

module.exports = Mode

//export default  Mode;
