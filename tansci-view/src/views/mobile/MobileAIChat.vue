<template>
  <div class="ai-chat">
    <mobile-header title="Chat AI"></mobile-header>
    <div class="chat-container">
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
          <li v-for="(item,index) in resultList"
            :style="{'flex-direction':item.isUser ? 'row':'row-reverse'}">
            <div
              class="avatar"
              :style="[{backgroundImage:`url(${item.avatar})`}]"
            />
            <div class="box"
            :style="[{'flex-direction':item.isUser ? 'row':'row-reverse'},{'margin-left':item.isUser ? '0.4rem':'0'},{'margin-right':item.isAI ? '0.4rem':'0'}]">
              <div :class="[{'arrow-l':item.isUser },{'arrow-r':item.isAI }]"></div>
              <div class="inner-box"
              :style="[{'align-items': item.isUser ?'flex-start':'flex-end'},{'margin-right': item.isAI ? '6px':'0'},{'margin-left': item.isUser ? '6px':'0'}]">
                <p class="time">时间:<span>{{item.timeStr}}</span></p>
                <div class="desc">{{item.content}}</div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="footer">
      <FireIcon class="h-6 w-6 text-slate-950" style="color:#EC784A;"/>
      <div class="input-box">
        <input placeholder="请输入最多200字" v-model="textVal" type="text" maxlength="200">
      </div>
      <div class="btn send" @click="requestAI">
        <PaperAirplaneIcon class="h-6 w-6 text-slate-950" />
      </div>
    </div>
  </div>
</template>

<script>
import { LightBulbIcon,FireIcon,PaperAirplaneIcon } from '@heroicons/vue/24/solid'
import aiHeadImg from '../../assets/image/avatar_ai.png'
import userHeadImg from '../../assets/image/avatar_2.png'
import {dateTimeFormat} from '../../utils/utils'
import MobileHeader from './component/MobileHeader.vue'
import {send} from '../../api/chatApi'

export default {
  components:{
    MobileHeader,
    LightBulbIcon,
    FireIcon,
    PaperAirplaneIcon
  },
  data(){
    return {
      textVal:'',
      showResult:false,
      aiHeadImg,
      userHeadImg,
      resultList:[]
    }
  },
  methods:{
    requestAI(){
      this.resultList.push({
          avatar:userHeadImg,
          timeStr:dateTimeFormat(new Date()),
          content:this.textVal,
          isUser:true
        },)
      this.showResult = true
      
      const item = {
        avatar:aiHeadImg,
        content:'抱歉,服务器无响应请稍后重试',
        isAI:true
      }
      send(this.textVal).then(res=>{
        if(res.code === 200){
            item.content = res.result  
        }
        item.timeStr= dateTimeFormat(new Date())
        this.resultList.push(item)
      }).catch(()=>{
        item.timeStr= dateTimeFormat(new Date())
        this.resultList.push(item)
      })
    }
  }
}
</script>

<style lang="scss">
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
  .chat-container{
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
    .result {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-start;
        width: 100%;
        height: 100%;
        overflow: scroll;
       > ul {
          height: auto;
          width: 100%;

          >li {
            position: relative;
            width: 100%;
            height: auto;
            padding: 6px 10px;
            background: #fff;
            display: flex;
            align-items: flex-start;
            justify-content: flex-start;
          
            .avatar {
              width: 2rem;
              height: 2rem;
              background-size: cover;
              border-radius: 1rem;
            }

            .box {
                position: relative;
                display: flex;
                align-items: flex-start;
                justify-content: flex-start;
                max-width: 80%;
                .arrow-r{
                  border-left: 6px solid #ecfccb;
                  right: 0px;
                  position: absolute;
                  top: 12px;
                  width: 0px;
                  height: 0px;
                  border-top: 6px solid transparent;
                  border-bottom: 6px solid transparent;
                  box-sizing: border-box;
                }
                .arrow-l {
                  border-right: 6px solid #ecfccb;
                  left: 0px;
                  position: absolute;
                  top: 12px;
                  width: 0px;
                  height: 0px;
                  border-top: 6px solid transparent;
                  border-bottom: 6px solid transparent;
                  box-sizing: border-box;
                }
                .inner-box{
                  width: 100%;
                  display: flex;
                  flex-direction: column;
                  justify-content: center;
                  background: #ecfccb;
                  padding: 0.6rem;
                  .time{
                    color:#083344;
                    font-weight: bold;
                  }
                  .desc{
                    margin-top: 0.2rem;
                  }
                }
            }
          }
       }  
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