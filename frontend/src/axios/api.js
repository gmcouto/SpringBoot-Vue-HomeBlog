import axios from 'axios'
var baseReq = {
  withCredentials: true
}

if (location.port === '8082') {
  baseReq.baseURL = 'http://localhost:8080'
}
const api = axios.create(baseReq)

api.setToken = (accessToken) => {
  api.defaults.headers['Authorization'] = 'Bearer ' + accessToken
}

api.clearToken = () => {
  delete api.defaults.headers['Authorization']
}

api.setAuthenticationErrorHandler = (handler) => {
  api.interceptors.response.use(null, function (error) {
    if (error.response.status === 401) {
      handler(error)
      return Promise.reject(error)
    }
    return Promise.reject(error)
  })
}

export default api
