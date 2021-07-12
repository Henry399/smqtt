//跨域代理前缀
const API_PROXY_PREFIX='/api'
const BASE_URL = process.env.NODE_ENV === 'production' ? process.env.VUE_APP_API_BASE_URL : API_PROXY_PREFIX
// const BASE_URL = process.env.VUE_APP_API_BASE_URL
module.exports = {
  LOGIN: `${BASE_URL}/auth/login`,
  CLUSTERS: `${BASE_URL}/smqtt/cluster`,
  CONNECTIONS: `${BASE_URL}/smqtt/connection`,
  SUBSCRIBES:`${BASE_URL}/smqtt/subscribe`,
  ISCLUESTER:`${BASE_URL}/smqtt/is/cluster`,
  PUBLISH:`${BASE_URL}/smqtt/publish`,
  ROUTES: `${BASE_URL}/routes`,
  GOODS: `${BASE_URL}/goods`,
  GOODS_COLUMNS: `${BASE_URL}/columns`,
}