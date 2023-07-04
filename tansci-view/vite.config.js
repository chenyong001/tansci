import path from "path"
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

const url = 'https://opencast.tet-ai.com.sg';
const testUrl = 'https://opencast.site'
const localUrl='http://localhost:8005'

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
                target: url,//url,testUrl,localUrl
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
