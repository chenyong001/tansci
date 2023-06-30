import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElIcons from '@element-plus/icons-vue'
import './styles/index.scss'
import ECharts from 'vue-echarts';
// import NoSleep from "NoSleep.js";
import NoSleep from "nosleep.js/dist/NoSleep.min.js";


const app = createApp(App)
app.use(store)
app.use(router)
app.use(ElementPlus, {
    locale: zhCn,
    size: "default"
})
// 统一导入el-icon图标
for(let icon in ElIcons){
    app.component(icon,ElIcons[icon])
}
app.component('VChart', ECharts);

app.mount('#app')

app.config.globalProperties.$NoSleep = NoSleep;
// var noSleep = NoSleep;

// var wakeLockEnabled = false;
// var toggleEl = document.querySelector("#toggle");
// toggleEl.addEventListener('click', function() {
//   if (!wakeLockEnabled) {
//     noSleep.enable(); // keep the screen on!
//     wakeLockEnabled = true;
//     toggleEl.value = "Wake Lock is enabled";
//     document.body.style.backgroundColor = "green";
//   } else {
//     noSleep.disable(); // let the screen turn off.
//     wakeLockEnabled = false;
//     toggleEl.value = "Wake Lock is disabled";
//     document.body.style.backgroundColor = "";
//   }
// }, false);


  
  
