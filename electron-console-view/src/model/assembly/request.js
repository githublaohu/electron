//  Copyright (c) [Year] [name of copyright holder]
//  [Software Name] is licensed under Mulan PSL v2.
//  You can use this software according to the terms and conditions of the Mulan PSL v2.
//  You may obtain a copy of Mulan PSL v2 at:
//           http://license.coscl.org.cn/MulanPSL2
//  THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
//  EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
//  MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
//  See the Mulan PSL v2 for more details.
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

var hierarchyIntercept = new HierarchyIntercept()

export{ hierarchyIntercept,RequestInfo }

