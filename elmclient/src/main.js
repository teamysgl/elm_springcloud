import {
    createApp
} from 'vue';
import App from './App.vue';
import router from './router';
import 'font-awesome/css/font-awesome.min.css';
import axios from 'axios';
import qs from 'qs';
import {
    getCurDate,
    setSessionStorage,
    getSessionStorage,
    removeSessionStorage,
    setLocalStorage,
    getLocalStorage,
    removeLocalStorage,
} from './common.js';

const app = createApp(App);
app.use(router);
app.config.productionTip = false;

// 设置 axios 基础 URL 部分
axios.defaults.baseURL = 'http://localhost:14000/';
//设置相应拦截器
axios.interceptors.response.use(function (response) {
    if (response.data.code == 403) {
        location.href = '/error403'
    }
    return response
}, function (error) {
    console.log(error)
    return Promise.reject(error)
})
app.config.globalProperties.$qs = qs;
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$getCurDate = getCurDate;
app.config.globalProperties.$setSessionStorage = setSessionStorage;
app.config.globalProperties.$getSessionStorage = getSessionStorage;
app.config.globalProperties.$setLocalStorage = setLocalStorage;
app.config.globalProperties.$getLocalStorage = getLocalStorage;
app.config.globalProperties.$removeLocalStorage = removeLocalStorage;
app.config.globalProperties.$removeSessionStorage = removeSessionStorage;

router.beforeEach(function (to, from, next) {
    let user = sessionStorage.getItem('user');
    //除了登录、注册、首页、商家列表、商家信息之外，都需要判断是否登录
    if (!(to.path == '/' || to.path == '/index' || to.path == '/businessList' || to.path == '/businessInfo' ||
        to.path == '/login' || to.path == '/register' || to.path == '/my' || to.path == '/error403')) {
        if (user == null) {
            router.push('/login');
            // location.reload();
        }
    }
    next();
});

app.mount('#app');