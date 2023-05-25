import path from "path"
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

const url = 'http://opencast.tsi.edu.sg';
const testUrl = 'http://opencast.site'

export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src'),
        },
    },
    // 全局样式
    css: {
        preprocessorOptions: {
            scss: {
                additionalData: `@use "@/styles/element/index.scss" as *;`,
            },
        },
        // postcss:{}
    },

    // 反向代理
    server: {
        headers: {
            'Access-Control-Allow-Origin': '*',
        },
        disableHostCheck: true,
        port: 8006,
        proxy: {
            '/tansci': {
                target: testUrl,//url,
                changeOrigin: true,
                pathRewrite: {
                    '^/tansci': '/tansci'
                }
            }
        },
        hmr:{
            overlay:true
        }
    }

})
