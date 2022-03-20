import apiUser from '@/api/user'
import { getToken, setToken, removeToken } from '@/common/utils/auth'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: 'data:image/png;base64,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',
    roles: [],

    userId: '1', // 用户id
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },

    SET_USER_ID: (state, id) => {
      state.userId = id
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        apiUser.login({
          uiName: username,
          uiPassword: userInfo.password
        }).then(res => {
          const data = res.data
          console.log('登录token==>', data.uiToken)
          setToken(data.uiToken)
          commit('SET_TOKEN', data.uiToken)
          commit('SET_USER_ID', data.uiId)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
		return new Promise((resolve, reject) => {
			apiUser.userInfo({
				uiId: state.userId,
			}).then(res => {
			  const data = res.data
			  // if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
			  //   commit('SET_ROLES', data.roles)
			  // } else {
			  //   reject('getInfo: roles must be a non-null array !')
			  // }
			  commit('SET_NAME', data.uiName)
			  // commit('SET_AVATAR', data.avatar)
			  resolve(res)
			}).catch(error => {
				console.log('查询失败==>', error)
			  reject(error)
			})
		})
    },

    // 登出
    LoginOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        apiUser.logout().then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
