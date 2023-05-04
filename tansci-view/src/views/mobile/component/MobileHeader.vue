<template>
    <div class="mob-header">
        <div class="box">
            <div v-if="!noBack" class="btn" @click="goBack()">
                <ArrowLeftIcon class="h-6 w-6 text-slate-950" />
            </div>
            <div class="title">
                <p class="main">{{title}}</p>
                <p class="sub">online</p>
            </div>
        </div>
        <div class="box">
            <div class="avatar">
                <UserIcon class="h-6 w-6 text-slate-950" />
            </div>
        </div>
    </div>
</template>

<script>
import '../../../styles/reset.scss'
import '../../../styles/mobile.scss'
import { ArrowLeftIcon,UserIcon } from '@heroicons/vue/24/solid'
export default {
    components:{
        ArrowLeftIcon,
        UserIcon
    },
    props:{
        title:{
            type: String,
            default:''
        },
        noBack:{
            type: Boolean,
            default: false
        }
    },
    methods:{
        goBack(){
            this.$router.push({path:'/mobile/main'})
        }
    }
}
</script>

<style lang="scss">
#app{
  width: 100%;
  height: 100%;
}
.mob-header{
    width: 100%;
    height: 3.6rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #fff;
    border-bottom: 1px solid #efefef;
    padding: 0 1rem;
    .box {
        width: auto;
        height: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .title {
        font-size: 1.14rem;
        color: #333333;
        height: 100%;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: center;

        .main{
            font-weight: 600;
        }

        .sub{
            font-size: 0.96rem;
            color:#2BD3C2;
        }
    }

    .avatar{
        width: 3.12rem;
        height: 3.12rem;
        background: #D1D5DA;
        border-radius: 1.71rem;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    
    .btn{
        width: 3rem;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
    }
}
</style>