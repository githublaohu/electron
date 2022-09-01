/**
 * 动作模块模块接口
 */
import request from '@/common/utils/request'

export default {
  /**
   * 添加动作
   * @params {Object}  data { organizationId, organizationName, organizationTypeEnum, aiParentId, aiName, aiLabel, aiAbilityType, aiDescription, abilityPower }
   */
  addAction(data) {
    return request({
      url: '/lamp/electron/abilityInfo/insertAbilityInfo',
      method: 'post',
      data: data
    })
  },

  /**
   * 删除动作
   * @params {Object}  data { aiId }
   */
  deleteAction(data) {
    return request({
      url: '/lamp/electron/abilityInfo/deleteAbilityInfo',
      method: 'post',
      data: data
    })
  },

  /**
   * 查询单个动作
   * @params {Object}  data { aiId }
   */
  querySingleAction(data) {
    return request({
      url: '/lamp/electron/abilityInfo/queryAbilityInfoById',
      method: 'post',
      data: data
    })
  },

  /**
   * 查询组织绑定的动作
   * @params {Object}  data { organizationId, aiAbilityType }
   */
  queryBindAction(data) {
    return request({
      url: '/lamp/electron/abilityRelation/queryAbilityRelationListByOrganizationId',
      method: 'post',
      data: data
    })
  },

  /**
   * 查询组织未绑定的动作
   * @aprams {Object} data { organizationId, organizationTypeEnum, aiAbilityType}
   */
  queryUnBindAction(data) {
    return request({
      url: '/lamp/electron/abilityInfo/queryAbilityInfoByForm',
      method: 'post',
      data: data
    })
  },

  /**
   * 查询子动作
   * @params {Object}  data { organizationId, organizationName, organizationTypeEnum, aiId, naiName, abilityTypeEnum, protocelConfigEnum, arExplain }
   */
  queryChildAction(data) {
    return request({
      url: '/abilityInfo/queryAbilityInfoByParentId',
      method: 'post',
      data: data
    })
  },

  /**
   * 动作绑定
   * @params {Object}  data { uiId， uiName， uiNickname }
   */
  bindAction(data) {
    return request({
      url: '/abilityRelation/bindAbilityRelation',
      method: 'post',
      data: data
    })
  },

  /**
   * 动作解绑
   * @params {Object}  data { arId }
   */
  unBindAction(data) {
    return request({
      url: '/abilityRelation/unbindAbilityRelation',
      method: 'post',
      data: data
    })
  },

  /**
   * 查询绑定记录
   * @params {Object}  data {}
   */
  queryBindRecord(data) {
    return request({
      url: '/abilityRelation/queryAbilityRelationListByForm',
      method: 'post'
    })
  },
}
