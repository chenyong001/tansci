<template>
  <div class="ai-asr">
    <mobile-header title="实时语音识别"></mobile-header>
    <div class="asr-container">
      <div class="clear-btn">
        <p>清空识别内容</p>
        <XCircleIcon class="h-6 w-6 text-slate-950" />
      </div>
      <textarea class="result-textarea"  readonly>{{resultContent}}</textarea>
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
import {Languages} from './arg'
import {SpeakerWaveIcon,SpeakerXMarkIcon,XCircleIcon} from '@heroicons/vue/24/solid'
import MobileHeader from './component/MobileHeader.vue'

export default {
components:{
    MobileHeader,
    SpeakerWaveIcon,
    SpeakerXMarkIcon,
    XCircleIcon
  },
  data(){
    return {
        languages:Languages,
        selectedLanguages:{},
        resultContent:''
      }
    },
    created(){
      this.selectedLanguages = this.languages[1]
    }
}
</script>

<style lang="scss">
.ai-asr{
  width: 100%;
  height: 100%;
  
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
        box-shadow: 0 8px 24px 0 rgba(18,97,255,.1);
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
         box-shadow: 0 8px 24px 0 rgba(18,97,255,.1);
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
    box-shadow: 0 8px 24px 0 rgba(18,97,255,.1);
    > p{
      margin-left: 8px;

    }
      }
      
      }
    }
  }

</style>