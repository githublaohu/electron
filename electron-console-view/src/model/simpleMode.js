//  Copyright (c) [Year] [name of copyright holder]
//  [Software Name] is licensed under Mulan PSL v2.
//  You can use this software according to the terms and conditions of the Mulan PSL v2.
//  You may obtain a copy of Mulan PSL v2 at:
//           http://license.coscl.org.cn/MulanPSL2
//  THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
//  EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
//  MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
//  See the Mulan PSL v2 for more details.

import{RequestInfo} from './assembly/request'
import{DataModeBehavior } from './assembly/behavior'
import{ViewModel} from './assembly/view'



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
        dataModeBehaviorBuild.__queryFormBehavior = this.__queryFormBehavior.clone();
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
        this.__viewModel.queryModel.__requsetInfo =  new RequestInfo(url , method);
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

    this.primaryKey = function (primaryKey) {
        this.__viewModel.primaryKey = primaryKey;
        return this;
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
        this.createDataModeBehavior(this.__viewModel.primaryKey,  this.__viewModel.activationModel);
        this.createDataModeBehavior(this.__viewModel.primaryKey,  this.__viewModel.deleteModel);
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
        if(behavior == null || behavior == undefined){
            console.log(modeBehavior);
            return;
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

//module.exports = Mode

export default  Mode;
