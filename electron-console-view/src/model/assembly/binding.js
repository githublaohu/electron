//  Copyright (c) [Year] [name of copyright holder]
//  [Software Name] is licensed under Mulan PSL v2.
//  You can use this software according to the terms and conditions of the Mulan PSL v2.
//  You may obtain a copy of Mulan PSL v2 at:
//           http://license.coscl.org.cn/MulanPSL2
//  THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
//  EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
//  MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
//  See the Mulan PSL v2 for more details.

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

export{
    BindingTree,
    BindingView
}