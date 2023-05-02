<template>
  <div class="ai-asr">
    <div class="header">
      <div class="box">
        <div class="btn">
          <ArrowLeftIcon class="h-6 w-6 text-slate-950" />
        </div>
        <div class="title">
          <p class="main">实时语音识别</p>
          <p class="sub">online</p>
        </div>
      </div>
      <div class="box">
        <div class="avatar">
          <UserIcon class="h-6 w-6 text-slate-950" />
        </div>
      </div>
      
    </div>
    <div class="asr-container">
      <div class="clear-btn">
        <p>清空识别内容</p>
        <XCircleIcon class="h-6 w-6 text-slate-950" />
      </div>
      <textarea class="result-textarea"  readonly>可以吗？可以吗？
你在听吗？在识别吗？
好的。
好的，好的。
这里是实时语音识别测试</textarea>
    </div>
    <div class="footer">
      <div class="upper">
        <select id="languageOptions">
          <option v-for="(item,index) in languages" :value="item.val" :selected="selectedLanguages.val === item.val">{{item.name}}</option>
        </select>
        <input placeholder="请输入备注：">
      </div>
      <div class="down">
        <div class="s-btn">
          <SpeakerWaveIcon class="h-6 w-6 text-slate-950" />
          <p>开始识别</p></div>
        <div class="s-btn">
          <SpeakerXMarkIcon class="h-6 w-6 text-slate-950" />
          <p>停止识别</p></div>
      </div>
      
    </div>
  </div>
</template>

<script>
import '../../styles/reset.scss'
import '../../styles/mobile.scss'
import { ArrowLeftIcon } from '@heroicons/vue/24/solid'
import { UserIcon } from '@heroicons/vue/24/solid'
import {Languages} from './arg'
import {SpeakerWaveIcon,SpeakerXMarkIcon,XCircleIcon} from '@heroicons/vue/24/solid'
export default {
components:{
    ArrowLeftIcon,
    UserIcon,
    SpeakerWaveIcon,
    SpeakerXMarkIcon,
    XCircleIcon
  },
  data(){
    return {
        languages:Languages,
        selectedLanguages:{}
      }
    },
    created(){
      this.selectedLanguages = this.languages[1]
    }
}
</script>

<style lang="scss">
#app{
  width: 100%;
  height: 100%;
}
.ai-asr{
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
  .asr-container{
    position: relative;
    width: 100%;
    height: calc(100% - 32%);
    background: #fff;
    border-bottom-left-radius: 2rem;
    border-bottom-right-radius: 2rem;
    .result-textarea{
      width: 90%;
      margin:2rem 5%;
      height: 80%;
      resize: none;
    }
    .clear-btn{
      position: absolute;
      bottom: 1rem;
      right: 1.2rem;
      
      display: flex;
      justify-content: center;
      align-items: center;
      >p{
        margin-right: 4px;
        margin-top: 2px;
      }
    }
  }
  .footer{
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 1rem 0 1.5rem 0;
    .upper,.down{
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 2rem;
    }
    .upper{
      width: 100%;
      margin-bottom: 1rem;
      select{
        width: 10.8rem;
        height: 4.8rem;
        border-radius: 1rem;
         padding: 0 1.6rem;
        background: #fff;
        -moz-appearance: none;
        -webkit-appearance: none;
        appearance: none;
        font-size: 1rem;
        color: #333;
      }
      input{
          width: 10.8rem;
        height: 4.8em;
        border-radius: 1rem;
        background: #fff;
         padding: .6rem 1.6rem;
         resize: none;
         text-align: center;
         color: #333;
         font-size: 1rem;
    }
    input::placeholder {
        font-weight: bold;
        opacity: 0.5;
        color: #333;
        line-height: 4.8rem;
    }
    }
    .down{
      width: 100%;
      .s-btn{
        display: flex;
    align-items: center;
    justify-content: center;
    width: 10.8rem;
    background: #fff;
    height: 4.8rem;
    border-radius: 1rem;
    > p{
      margin-left: 8px;

    }
      }
      
      }
    }
  }

</style>