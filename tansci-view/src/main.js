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

export default {
    components: {
    //   "remote-css": {
    //     render(createElement) {
    //       return createElement("link", {
    //         attrs: { rel: "stylesheet", href: this.href },
    //       });
    //     },
    //     props: {
    //       href: { type: String, required: true },
    //     },
    //   },
      "remote-js": {
        render(createElement) {
          return createElement("script", {
            attrs: { type: "text/javascript", src: this.src },
          });
        },
        props: {
          src: { type: String, required: true },
        }
      }
    }
  }
  
  
