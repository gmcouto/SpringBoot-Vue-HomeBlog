import Vue from 'vue'
import Vuex from 'vuex'
import session from './session'
import api from '../axios/api'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    session: session
  },
  state: {
    posts: [],
    userProfile: {},
    token: {}
  },
  actions: {
    LOAD_POST_LIST ({ commit }) {
      api.get('/api/posts')
      .then((response) => {
        commit('SET_POST_LIST', {list: response.data})
      })
      .catch((err) => {
        console.log(err)
      })
    }
  },
  mutations: {
    SET_POST_LIST (state, { list }) {
      state.posts = list
    }
  },
  getters: {

  }
})

// if our authentication fails eventually, clear session
api.setAuthenticationErrorHandler((cerr) => {
  store.commit('ERASE_TOKEN')
})

export default store
