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

export default api;

api.queryNodeBaseListByOiId = function(data,page){
    return request({
        url: '/lamp/electron/instanceAndInterface/queryNodeBaseListByOiId',
        method: 'post',
        data:data
      })
}

api.queryNodeBaseListByFrom = function(data,page){
    return request({
        url: '/lamp/electron/instanceAndInterface/queryNodeBaseListByFrom',
        method: 'post',
        data:data
      })
}
