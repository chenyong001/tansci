<template>
  <div class="mobile-main">
    <mobile-header title="首页" :no-back="Boolean(true)"></mobile-header>
    <ul class="menus">
        <li @click="goto('/mobile/AIChat')" style="background:#7dd3fc"><p>AIGC-Chat文字版</p></li>
        <li @click="goto('/mobile/ASR')" style="background:#86efac"><p>实时语音识别</p></li>
        <li class="disable"><p>AIGC-Chat语音版</p><p class="wait">敬请期待...</p></li>
        <li class="disable"><p>实时语音翻译</p><p class="wait">敬请期待...</p></li>
    </ul>
  </div>
</template>

<script>
import MobileHeader from './component/MobileHeader.vue'
export default {
    components:{
        MobileHeader
    },
    methods:{
        goto(path){
            this.$router.push({path:path})
        }
    }
}
</script>

<style lang="scss">
.mobile-main{
    width:100%;
    height: 100%;

    .menus {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        justify-content: space-around;
        >li {
            width: 40%;
            height: 10rem;
            background: #cbd5e1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            box-shadow: 0 8px 24px 0 rgba(18, 97, 255, 0.1);
            font-size: 1.2rem;
            margin: 1rem 0.2rem;
            border-radius: 0.4rem;
            color:#fff;
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