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
          <li v-for="(item,index) in resultList"
            :style="{'flex-direction':index % 2 === 0 ? 'row':'row-reverse'}">
            <div
              class="avatar"
              :style="[{backgroundImage:`url(${item.avatar})`}]"
            />
            <div class="box"
            :style="[{'flex-direction':index % 2 === 0 ? 'row':'row-reverse'},{'margin-left':index % 2 === 0 ? '0.4rem':'0'},{'margin-right':index % 2 !== 0 ? '0.4rem':'0'}]">
              <div :class="[{'arrow-l':index % 2 === 0 },{'arrow-r':index % 2 !== 0 }]"></div>
              <div class="inner-box"
              :style="[{'align-items': index % 2 === 0 ?'flex-start':'flex-end'},{'margin-right': index % 2 !== 0 ? '6px':'0'},{'margin-left': index % 2 === 0 ? '6px':'0'}]">
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
        <input placeholder="请输入最多200字" type="text" maxlength="200">
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
import aiHeadImg from '../../assets/image/avatar_ai.png'
import userHeadImg from '../../assets/image/avatar_2.png'
import {dateTimeFormat} from '../../utils/utils'

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
      showResult:false,
      aiHeadImg,
      userHeadImg,
      resultList:[
        {
          avatar:userHeadImg,
          timeStr:dateTimeFormat(new Date()),
          content:'简单介绍你自己'
        },
        {
          avatar:aiHeadImg,
          timeStr:dateTimeFormat(new Date()),
          content:'我是 Touchstone 训练的 AI 大型语言模型。我可以回答各种问题，并生成文本。如果您有任何问题，请告诉我，我将尽力帮助您'
        },
        {
          avatar:userHeadImg,
          timeStr:dateTimeFormat(new Date()),
          content:'空气是什么?'
        },
        {
          avatar:aiHeadImg,
          timeStr:dateTimeFormat(new Date()),
          content:'空气是一种由几种气体混合而成的物质，主要是氮气（约78％）和氧气（约21％）。剩余1％的空气由其他气体，如二氧化碳，氖和氩组成。空气对生命至关重要，因为它提供了生物呼吸所需的氧气。它还有助于调节温度和分配水分。空气在我们周围随处可见，它具有质量和重量。它是一种流体，这意味着它可以随着温度，压力和其他环境因素的变化流动和移动。空气还可以传播声音，有助于分配热和冷。空气污染是世界许多地区的一个主要问题，因为它可能对环境和人类健康产生严重影响。化石燃料的燃烧，森林砍伐和其他人类活动导致了空气污染物的增加，如烟雾，温室气体和颗粒物。采取行动以减少空气污染并保护我们呼吸的空气质量非常重要。'
        },
      ]
    }
  },
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