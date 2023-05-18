<template>
  <div class="mobile-main">
    <mobile-header title="首页" :no-back="Boolean(true)"></mobile-header>
    <p class="h-title">Unlock the power </p>
    <p class="h-title">of AI</p>
    <p class="h-sub-title">Chat with the smartest AI -</p>
    <p class="h-sub-title">Experience the power of AI with us</p>
    <ul class="menus">
        <li v-if="hasChatGPT" @click="goto('/mobile/AIChat')">
            <div class="img-box" :style="image1Style"/>
            <p>AIGC ChAT</p>
            <ArrowRightIcon class="h-6 w-6 text-slate-950" />
        </li>
        <li v-if="hasASRNote" @click="goto('/mobile/ASR')">
            <div class="img-box" :style="image2Style"/>
            <p>实时语音识别</p>
            <ArrowRightIcon class="h-6 w-6 text-slate-950" />
        </li>
        <li class="disable">
            <!-- <div class="img-box"/> -->
            <p>AIGC-Chat语音版</p>
            <p class="wait">敬请期待...</p>
        </li>
        <li class="disable">
            <!-- <div class="img-box"/> -->
            <p>实时语音翻译</p>
            <p class="wait">敬请期待...</p>
        </li>
    </ul>
  </div>
</template>

<script>
import MobileHeader from './component/MobileHeader.vue'
import { ArrowRightIcon } from '@heroicons/vue/24/solid'
import icon1 from '../../assets/image/home_icon_1.jpg'
import icon2 from '../../assets/image/home_icon_2.jpg'
import {menuList} from '@/api/systemApi'
export default {
    components:{
        MobileHeader,
        ArrowRightIcon
    },
    data(){
        return {
            icon1,
            icon2,
            image1Style:{background: 'url(' + icon1 + ')' + 'center center/contain no-repeat'},
            image2Style:{background: 'url(' + icon2 + ')' + 'center center/contain no-repeat'},
            hasChatGPT:false,
            hasASRNote:false,

        }
    },
    created(){
        this.getMenuList()
    },
    methods:{
        goto(path){
            this.$router.push({path:path})
        },
        getMenuList(){
            menuList({types:'1,2,3', status: 1}).then((res)=>{
                const chatGPT = "/chatGPT/chatGPT"
                const asr = '/collect/Note'
                const list = res.result
                
                const findURL = (arr,url)=>{
                    return !!arr.find(v=>v.url === url)
                }
                const chatGPTBoolean = []
                const hasASRNoteBoolean = []
                for(let i = 0; i < Array.from(list).length; i++){
                    const v = list[i]
                    if(v.children){
                        chatGPTBoolean.push(findURL(v.children,chatGPT))
                        hasASRNoteBoolean.push(findURL(v.children,asr))
                    } else {
                        chatGPTBoolean.push(v.url === chatGPT)
                        hasASRNoteBoolean.push(v.url === asr)
                    }
                }
                // console.log(chatGPTBoolean)
                // console.log(hasASRNoteBoolean)
                this.hasChatGPT = chatGPTBoolean.some(v=> v === true)
                this.hasASRNote = hasASRNoteBoolean.some(v=> v === true)
            }).catch(()=>{

            })
        
        }
    }
}
</script>

<style lang="scss">
.mobile-main{
    width:100%;
    height: 100%;
    background: #fff;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;
    .h-title{
        font-size: 24px;
        font-weight: 800;
        color:#333;
        padding: 1rem 0.5rem 0 0.5rem;
    }
    .h-sub-title{
        font-size: 14px;
        color:#333;
        padding: 0 0.5rem;
    }
    .menus {
        margin-top:1rem;
        width: 90%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-start;
        border: 1px solid #f0f2f8;
        border-radius: 0.6rem;
        >li {
            width: 80%;
            height: 6rem;
            background: #fff;
            display: flex;
            align-items: center;
            justify-content: space-around;
            box-shadow: 0 2px 8px 2px rgba(18, 97, 255, 0.1);
            font-size: 1.2rem;
            margin: 1rem 0.2rem;
            border-radius: 0.6rem;
            color: #333;
            border: 1px solid #f0f2f8;
            .img-box{
                width: 6rem;
                height: 4rem;
            }
            .wait{
                font-size: 1rem;
                color: #a1a1aa;
            }
        }
        .disable{
            box-shadow:none;
        }
    }
}

</style>