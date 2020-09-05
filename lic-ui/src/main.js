import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

/*引入element-ui依赖*/
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
Vue.use(Element)

/*引入axios依赖*/
import axios from 'axios'
Vue.prototype.$axios = axios


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
