import Vue from 'vue';
import ElementUI from 'element-ui';

import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import Cookies from 'js-cookie';
import axios from './axios/index';



Vue.use(ElementUI);

Vue.prototype.$cookies = Cookies
Vue.prototype.$axios = axios
// Vue.component('el-progress', ElProgress); // 注册 el-progress 组件

new Vue({
  el: '#app',
  render: h => h(App)
});