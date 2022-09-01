//  Copyright (c) [Year] [name of copyright holder]
//  [Software Name] is licensed under Mulan PSL v2.
//  You can use this software according to the terms and conditions of the Mulan PSL v2.
//  You may obtain a copy of Mulan PSL v2 at:
//           http://license.coscl.org.cn/MulanPSL2
//  THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
//  EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
//  MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
//  See the Mulan PSL v2 for more details.

import {httpRequest} from './requestModel'

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
        
        var view = null;
        if(thiz.viewModel.slaveModel != undefined){
            view =thiz.viewModel.slaveModel;
            view.setViewContextData("miandata", row);
            view.initialization();
            view.queryFormModel.request();
        }
        thiz.formData = row;
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
        var requestInfo = {};
        requestInfo["url"] = thiz.__requsetInfo.url;
        requestInfo["method"] = thiz.__requsetInfo.method;

        var header = {};
        header["Content-type"] = "application/json";

        if(thiz.behavior == "queryForm"){
            //header[""] = JSON.stringify(vue.tableView.__pagination);
        }

        requestInfo["headers"] = header;
        requestInfo["data"] = thiz.__requsetInfo.dataIntercept( thiz.formData );

        httpRequest(requestInfo).then(res => {
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

export {DataModeBehavior , ModeBehavior}