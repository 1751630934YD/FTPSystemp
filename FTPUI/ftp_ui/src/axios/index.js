import axios from "axios";
import Cookies from 'js-cookie';


const instance = axios.create({
  baseURL: 'http://localhost:8081',
  timeout: 0,

});



instance.interceptors.request.use(config => {
  const val = Cookies.get('value') == null ? {} : JSON.parse(Cookies.get('value'));

    if(config.headers['Content-Type']!='multipart/form-data'){
      config.data = {
        hostname: val.FTPurl,
        password: val.password,
        username: val.user,
         ...config.data // 将传递的请求参数合并到默认请求参数中
      }
    }

  
 

  return config;
});


// 4. 导出
export default instance