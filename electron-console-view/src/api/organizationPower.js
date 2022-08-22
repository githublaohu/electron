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
import mode from '../config/simpleMode'

mode.

var api  = {}




api.insertOrganizationPower = function(data){
    return request({
        url: '/organizationPower/insertOrganizationPower',
        method: 'post',
        data:data
      })
}

api.updateUserPowerByOpId = function(data){
    return request({
        url: '/organizationPower/updateUserPowerByOpId',
        method: 'post',
        data:data
      })
}

api.updateOrganizationPowerStatusByOpId = function(data){
    return request({
        url: '/organizationPower/updateOrganizationPowerStatusByOpId',
        method: 'post',
        data:data
      })
}

api.queryOrganizationPowerByOiId = function(data,page){
    return request({
        url: '/organizationPower/queryOrganizationPowerByOiId',
        method: 'post',
        data:data
      })
}

api.insertOrganizationPower = function(data){
    return request({
        url: '/organizationPower/insertOrganizationPower',
        method: 'post',
        data:data
      })
}
export default api;
