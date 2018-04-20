import axios from 'axios'
var baseReq = {
  withCredentials: true,
  auth: {
    username: 'test',
    password: 'test'
  }
}

if (location.port === '8082') {
  baseReq.baseURL = 'http://localhost:8080'
}
const oauth = axios.create(baseReq)

export default oauth
