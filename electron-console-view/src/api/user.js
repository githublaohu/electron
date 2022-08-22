/**
 * 用户模块接口
 */
import request from '@/common/utils/request'

export default {
  /**
	 * 用户注册
	 * @params {Object}  data { uiName, uiNickname, uiPassword}
	 */
  register(data) {
    return request({
      url: '/lamp/electron/userInfo/insertUserInfoByPassword',
      method: 'post',
      data: data
    })
  },

  /**
	 * 管理员注销用户账号
	 * @params {Object}  data { uiId }
	 */
  manageLoginOutUser(data) {
    return request({
      url: '/lamp/electron/userInfo/manageAndUpdateUserPasswordByUiId',
      method: 'post',
      data: data
    })
  },

  /**
	 * 管理员修改用户密码
	 * @params {Object}  data { uiId, uiPassword }
	 */
  manageModifyUserPassword(data) {
    return request({
      url: '/lamp/electron/userOperation/changePassword',
      method: 'post',
      data: data
    })
  },

  /**
	 * 用户修改自己信息
	 * @params {Object}  data { uiId, uiNickname }
	 */
  modifyUserInfo(data) {
    return request({
      url: '/lamp/electron/userInfo/updateUserInfoByUiId',
      method: 'post',
      data: data
    })
  },

  /**
	 * 获取用户信息
	 * @params {Object}  data { uiId }
	 */
  userInfo(data) {
    return request({
      url: '/lamp/electron/userInfo/queryUserInfoById',
      method: 'post',
      data: data
    })
  },

  /**
	 * 获取用户列表
	 * @params {Object}  data { uiId， uiName， uiNickname }
	 */
  userList(data) {
    return request({
      url: '/userInfo/queryUserInfoByForm',
      method: 'get',
      data: data
    })
  },

  /**
	 * 用户登录
	 * @params {Object}  data { uiName， uiPassword }
	 */
  login(data) {
    return request({
      url: '/lamp/electron/userOperation/psswordLogin',
      method: 'post',
      data: data
    })
  },

  /**
	 * 用户退出
	 * @params {Object}  data
	 */
  logout(data) {
    return request({
      url: '/lamp/electron/userOperation/signOut',
      method: 'post'
    })
  },

  /**
   * 获取用户列表
   * @params {Object} data {uiName}
   */
  getUserList(data) {
    return request({
      url: '/lamp/electron/userInfo/queryUserInfoByForm',
      method: 'post',
      data: data
    })
  }
}
