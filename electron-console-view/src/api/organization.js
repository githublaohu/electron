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

api.queryOrganizationInfoByUserId = function(data,page){
  return request({
    url: '/organization/queryOrganizationInfoByUserId',
    method: 'post',
    data:data
  })
}

/**
 * 通过id查询组织信息
 * @param {*} data
 */
api.queryOrganizationInfoByOiId = function(data) {
    return request({
      url: '/organization/queryOrganizationInfoByOiId',
      method: 'post',
      data:data
    })
  }

  /**
   * 查询下属组织
   * @param {*} data
   */
api.queryOrganizationInfoByTypeAndSuperior = function(data) {
    return request({
      url: '/organization/queryOrganizationInfoByOiId',
      method: 'post',
      data:data
    })
  }

/**
 * 修改组织拥有人
 * @param {} data
 */
api.updateOwnerById = function(data) {
    return request({
      url: '/organization/updateOwnerById',
      method: 'post',
      data:data
    })
  }

  /**
 * 修改组织说明
 * @param {} data
 */
api.updateExplainById  = function(data){
    return request({
      url: '/organization/updateExplainById',
      method: 'post',
      data:data
    })
  }

  /**
 * 作废组织
 * @param {} data
 */
api.deleteOrganizationById  = function(data){
    return request({
      url: '/organization/deleteOrganizationById',
      method: 'post',
      data:data
    })
  }

/**
 * 添加组织
 * @param {} data
 */
api.insertOrganizationInfo = function (data){
    return request({
      url: '/organization/insertOrganizationInfo',
      method: 'post',
      data:data
    })
  }
