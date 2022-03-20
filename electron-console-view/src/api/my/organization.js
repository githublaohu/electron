/**
 * 组织模块接口
 */
import request from '@/common/utils/request'

export default {
  /**
   * 添加组织
   * @params {Object}  data { ioSuperiorId, ioName, ioEnglishName， ioType，oiExplain，ioLabel}
   */
  addOrganization(data) {
    return request({
      url: '/organization/insertOrganizationInfo',
      method: 'post',
      data: data
    })
  },

  /**
   * 修改组织信息
   * @params {Object}  data { ioId, oiExplain, ioLabel }
   */
  modifyOrganization(data) {
    return request({
      url: '/organization/updateExplainById',
      method: 'post',
      data: data
    })
  },

  /**
   * 修改拥有人信息
   * @params {Object}  data { ioId, oiOwnerId，oiOwnerName }
   */
  modifyOrganizationOwner(data) {
    return request({
      url: '/organization/insertOrganizationInfo',
      method: 'post',
      data: data
    })
  },

  /**
   * 查询单个组织
   * @params {Object}  data { oiId }
   */
  queryOrganization(data) {
    return request({
      url: '/organization/queryOrganizationInfoByOiId',
      method: 'post',
      data: data
    })
  },

  /**
   * 查询组织列表
   * @params {Object}  data { ioName，ioEnglishName，ioType，oiExplain，ioLabel }
   */
  getOrganizationList(data) {
    return request({
      url: '/organization/queryOrganizationInfoByForm',
      method: 'post',
      data: data
    })
  },

  /**
   * 查询下属组织
   * @params {Object}  data { ioSuperiorId }
   */
  querySubOrganization(data) {
    return request({
      url: '/organization/queryOrganizationInfoByTypeAndSuperior',
      method: 'post',
      data: data
    })
  },

  /**
   * 添加下属组织
   * @params {Object}  data { oiEnglishName, oiExplain, oiName, oiSuperiorId, oiType }
   */
  addSubOrganization(data) {
    return request({
      url: '/organization/insertOrganizationInfo',
      method: 'post',
      data: data
    })
  },

  /**
   * 查询用户的空间和组织
   */
  userOrganization(data) {
    return request({
      url: '/organization/queryOrganizationInfoByUserId',
      method: 'post',
      data: data
    })
  },

  /**
   * 添加组织成员
   * @params {Object}  data { organizationId，organizationName，organizationTypeEnum，organizationEnglistName，uiId，uiName, opPower }
   */
  addOrganizationMember(data) {
    return request({
      url: '/organization/insertOrganizationUserPower',
      method: 'get'
    })
  },

  /**
   * 删除组织成员
   * @param {Object} data
   */
  deleteOrganizationMember(data) {
    return request({
      url: '/organization/updateUserPowerByOpId',
      method: 'get',
      data: data
    })
  },

  /**
   * 修改组织成员权限
   * @param {Object} data
   */
  modifyOrganizationAuthority(data) {
    return request({
      url: '/organization/updateOrganizationPowerStatusByOpId',
      method: 'get',
      data: data
    })
  },

  /**
   * 查询组织下的成员
   * @param {Object} data
   */
  queryOrganizationMember(data) {
    return request({
      url: '/organizationPower/queryOrganizationPowerByOiId',
      method: 'post',
      data: data
    })
  }
}

