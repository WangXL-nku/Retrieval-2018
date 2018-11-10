// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Vuex from 'vuex'
import router from './router'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'


var Instance = axios.create();

Instance.defaults.baseURL = 'http://localhost:8080'
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(Vuex)
// Vue.config.productionTip = false
// // 将axios挂载到prototype上，在组件中可以直接使用this.axios访问
Vue.prototype.axios = Instance
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
