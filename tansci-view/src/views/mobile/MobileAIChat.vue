<template>
  <div class="ai-chat">
    <div class="header">
      <div class="box">
        <div class="btn">
          <ArrowLeftIcon class="h-6 w-6 text-slate-950" />
        </div>
        <div class="title">
          <p class="main">Chat AI</p>
          <p class="sub">online</p>
        </div>
      </div>
      <div class="box">
        <div class="avatar">
          <UserIcon class="h-6 w-6 text-slate-950" />
        </div>
      </div>
      
    </div>
    <div class="container">
      <div 
        v-if="!showResult"
        class="guide"
      >
        <LightBulbIcon class="h-6 w-6 text-slate-950" />
        <p class="text">欢迎使用Touchstone版Chat AI</p>
        <p class="sub-text">你想向 AI 问些什么?</p>
        <p class="example-tip">什么是AI-Chat</p>
      </div>
      <div 
      v-if="showResult"
      class="result">
        <ul>
          <li>
            <div class="avatar">头像</div>
            <div class="box">
              <p class="time">时间</p>
              <div class="desc">input</div>
            </div>
          </li>
          <li>
            <div class="avatar">头像</div>
            <div class="box">
              <p class="time">时间</p>
              <div class="desc">output</div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="footer">
      <FireIcon class="h-6 w-6 text-slate-950" style="color:#EC784A;"/>
      <div class="input-box">
        <input>
      </div>
      <div class="btn send">
        <PaperAirplaneIcon class="h-6 w-6 text-slate-950" />
      </div>
    </div>
  </div>
</template>

<script>
import '../../styles/reset.scss'
import '../../styles/mobile.scss'
import { ArrowLeftIcon } from '@heroicons/vue/24/solid'
import { UserIcon } from '@heroicons/vue/24/solid'
import { LightBulbIcon } from '@heroicons/vue/24/solid'
import { FireIcon } from '@heroicons/vue/24/solid'
import { PaperAirplaneIcon } from '@heroicons/vue/24/solid'
export default {
  components:{
    ArrowLeftIcon,
    UserIcon,
    LightBulbIcon,
    FireIcon,
    PaperAirplaneIcon
  },
  data(){
    return {
      showResult:true,
    }
  }
}
</script>

<style lang="scss">
#app{
  width: 100%;
  height: 100%;
}
.ai-chat{
  width: 100%;
  height: 100%;
  .btn{
      width: 3rem;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  .header{
    width: 100%;
    height: 3.6rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #fff;
    border-bottom: 1px solid #efefef;
    
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
          margin-right: 1rem;
          display: flex;
        align-items: center;
        justify-content: center;
    }

  }
  .container{
    width:100%;
    height: calc(100% - 3.6rem - 4.28rem);
    background: #ffffff;
        display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-start;
    .guide{
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: flex-start;
      width: 100%;
      margin-top: 6rem;
      .text{
        margin: 2rem 0 0 0;
        font-size: 1.2rem;
      }
      .sub-text{
        margin: 1rem 0;
        font-size: 1.0rem;
      }
      .example-tip{
        width: 80%;
    text-align: center;
    background: rgba(36, 189, 184, 0.4);//#24BDB8;
    padding: 1rem;
    border-radius: 1rem;
    color: #fff;
      }
    }
    .result{
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-start;
        width: 100%;
    }
  }
  .footer{
    width: 100%;
    height: 4.28rem;
    display: flex;
    align-items: center;
    justify-content: space-around;
    background: #fff;
    border-top: 1px solid #efefef;
    .input-box{
       min-height: 2rem;
      width: 74%;
      border-radius: 8rem;
      background: #d1d5daa6;
      input{
      color: #333;
    font-size: 1rem;
    width: 90%;
    height: 2rem;
    background: transparent;
    margin: 0.5rem 5%;
     
    }
    }
    
    .send{
      transform: rotate(-45deg);
    }
  }
}

</style>