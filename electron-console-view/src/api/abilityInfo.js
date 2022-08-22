//  Copyright (c) [Year] [name of copyright holder]
//  [Software Name] is licensed under Mulan PSL v2.
//  You can use this software according to the terms and conditions of the Mulan PSL v2.
//  You may obtain a copy of Mulan PSL v2 at:
//           http://license.coscl.org.cn/MulanPSL2
//  THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
//  EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
//  MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
//  See the Mulan PSL v2 for more details.
import request from '@/common/utils/request'

var api  = {}

api.insertAbilityInfo = function(data){
    return request({
        url: '/abilityInfo/insertAbilityInfo',
        method: 'post',
        data:data
      })
}

api.deleteAbilityInfo = function(data){
    return request({
        url: '/abilityInfo/deleteAbilityInfo',
        method: 'post',
        data:data
      })
}

api.queryAbilityInfoById = function(data){
    return request({
        url: '/abilityInfo/queryAbilityInfoById',
        method: 'post',
        data:data
      })
}

api.queryAbilityInfoByParentId = function(data,page){
    return request({
        url: '/abilityInfo/queryAbilityInfoByForm',
        method: 'post',
        data:data
      })
}

api.queryAbilityInfoByParentId = function(data,page){
    return request({
        url: '/abilityInfo/queryAbilityInfoByParentId',
        method: 'post',
        data:data
      })
}
