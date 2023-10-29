//  Copyright (c) [Year] [name of copyright holder]
//  [Software Name] is licensed under Mulan PSL v2.
//  You can use this software according to the terms and conditions of the Mulan PSL v2.
//  You may obtain a copy of Mulan PSL v2 at:
//           http://license.coscl.org.cn/MulanPSL2
//  THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
//  EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
//  MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
//  See the Mulan PSL v2 for more details.

import{BindingView} from './binding'
import{TableView} from './table'
import{ModeBehavior} from './behavior'


function ViewModel(viewName) {

    this.viewName = viewName;

    this.viewTitle;

    this.addModel = new ModeBehavior("add",viewName,this);

    this.updateModel = new ModeBehavior("update",viewName,this);

    this.queryFormModel = new ModeBehavior("queryForm",viewName,this);

    this.queryModel = new ModeBehavior("query",viewName,this);

    this.deleteModel = new ModeBehavior("delete",viewName,this);

    this.activationModel = new ModeBehavior("activation",viewName,this);

    this.primaryKey = null;

    this.tableView = new TableView();

    this.bindingView = new BindingView();

    this.viewContext = {}

    this.assemblyContext;

    this.requestModel;

    this.__vue;

    this.slaveName;

    this.slaveModel = null;

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
        if (this.primaryKey != null){
         viewModel.primaryKey = this.primaryKey.clone();
        }
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


export {ViewModel}