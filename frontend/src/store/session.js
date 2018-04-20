import api from '../axios/api'
import oauth from '../axios/oauth'
import Cookies from 'cookies-js'
const querystring = require('querystring')

const session = {
  state: {
    token: {}
  },
  mutations: {
    SET_TOKEN (state, { token }) {
      state.token = token
      api.setToken(token.access_token)
      Cookies.set('token', JSON.stringify(token), {expires: ((new Date()) + (1000 * token.expires_in)), secure: true})
      sessionStorage.setItem('token', JSON.stringify(token))
    },
    ERASE_TOKEN (state) {
      state.token = {}
      api.clearToken()
      Cookies.expire('token')
      sessionStorage.removeItem('token')
    }
  },
  actions: {
    LOGIN_USER ({ commit, dispatch }, { username, password }) {
      oauth.post('/oauth/token',
        querystring.stringify({
          grant_type: 'password',
          username: username,
          password: password
        }))
      .then(({ data: token }) => {
        dispatch('CHECK_TOKEN', { token })
      }).catch((err) => {
        console.log(err)
        commit('ERASE_TOKEN')
      })
    },
    CHECK_TOKEN ({ commit }, { token }) {
      oauth.post('/oauth/check_token',
          querystring.stringify({
            token: token.access_token
          })
        )
        .then(({data: tokenInfo}) => {
          token.info = tokenInfo
          commit('SET_TOKEN', { token })
        }).catch((rror) => {
          console.log(rror)
          commit('ERASE_TOKEN')
        })
    },
    LOGOUT ({ commit, state }) {
      api.get('/api/users/logout?' +
          querystring.stringify({
            'access_token': state.token.access_token
          })
        )
        .then(() => {
          commit('ERASE_TOKEN')
        }).catch((rror) => {
          console.log(rror)
        })
    },
    LOAD_SESSION ({commit, dispatch}) {
      try {
        var token = JSON.parse(Cookies.get('token') || sessionStorage.getItem('token'))
        commit('SET_TOKEN', { token }) //  trust token first
        dispatch('CHECK_TOKEN', { token }) // then check it asynchronously
      } catch (e) {
        commit('ERASE_TOKEN')
      }
    }
  },
  getters: {
    username: state => {
      return (state.token.info && state.token.info.user_name)
    }
  }
}

export default session
