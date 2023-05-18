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
        <ul v-if="!currentExample"
            class="examples">
          <li v-for="(item,index) in examples"
          @click="selectExample(item)">
            <div class="exp-box" :style="item.img">
              <div class="exp-title"><p>{{item.name}}</p></div>
            </div>
          </li>
        </ul>
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
              <!-- <div :class="[{'arrow-l':item.isUser },{'arrow-r':item.isAI }]"></div> -->
              <div class="inner-box"
              :style="[{'align-items': item.isUser ?'flex-start':'flex-end'},{'margin-right': item.isAI ? '6px':'0'},{'margin-left': item.isUser ? '6px':'0'},{'border-top-right-radius': item.isUser ? '1.6rem':'0.2rem'},{'border-top-left-radius': !item.isUser ? '1.6rem':'0.2rem'}]">
                <p class="time">时间:<span>{{item.timeStr}}</span></p>
                <div class="desc">{{item.content}}</div>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="footer">
      <!-- <FireIcon class="h-6 w-6 text-slate-950" style="color:#EC784A;"/> -->
      <div class="input-box">
        <input placeholder="请输入最多200字" v-model="textVal" type="text" maxlength="200">
      </div>
      <div class="btn send" @click="requestAI">
        <PaperAirplaneIcon class="h-6 w-6 text-slate-950" style="color:#EC784A;"/>
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
import icon1 from '../../assets/image/3-2school-life.png'
import icon2 from '../../assets/image/3-3movies.png'
import icon3 from '../../assets/image/3-4reading.png'
import icon4 from '../../assets/image/3-5history.png'
import icon5 from '../../assets/image/3-6technology-digital.png'
import icon6 from '../../assets/image/3-7music-singer.png'
export default {
  components:{
    MobileHeader,
    LightBulbIcon,
    FireIcon,
    PaperAirplaneIcon
  },
  data(){
    return {
      icon1,
      icon2,
      icon3,
      icon4,
      icon5,
      icon6,
      textVal:'',
      showResult:false,
      aiHeadImg,
      userHeadImg,
      resultList:[],
      examples:[
        {
          name:'校园生活',
          img:{backgroundImage: 'url(' + icon1 + ')',backgroundSize:'cover'},
          desc:'我是你的同班同学，我们可以讨论大学生活，课程，考试，作业和其他方向的学校生活',
        },
        {
          name:'电影',
          img:{backgroundImage: 'url(' + icon2 + ')',backgroundSize:'cover'},
          desc:'我是和你一样的电影爱好者，来和我一起讨论你最喜欢的电影和电视吧',
        },
        {
          name:'阅读',
          img:{backgroundImage: 'url(' + icon3 + ')',backgroundSize:'cover'},
          desc:'我是和你一样极爱读书的人，来和我一起讨论你喜欢的读物',
        },
        {
          name:'历史',
          img:{backgroundImage: 'url(' + icon4 + ')',backgroundSize:'cover'},
          desc:'我对历史非常感兴趣，如果你有时间，让我们一起来讨论历史吧',
        },
        {
          name:'科技前沿',
          img:{backgroundImage: 'url(' + icon5 + ')',backgroundSize:'cover'},
          desc:'我对科技和数字产品非常感兴趣，并有自己的理解。你可以问我任何有关科技和数字产品有关系的事情',
        },
        {
          name:'音乐',
          img:{backgroundImage: 'url(' + icon6 + ')',backgroundSize:'cover'},
          desc:'我非常喜欢听音乐，来和我一起讨论你最喜欢的音乐吧',
        },
      ],
      currentExample:null,
      showExample:true,
    }
  },
  methods:{
    selectExample(item){
      this.currentExample = item
    },
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
      send(this.textVal,'',this.currentExample?.desc).then(res=>{
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
      margin-top: 3rem;
      .text{
        margin: 2rem 0 0 0;
        font-size: 1.2rem;
      }
      .sub-text{
        margin: 1rem 0;
        font-size: 1.0rem;
      }
      .examples{
        width: 90%;
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        align-items: center;
        >li{
            width: 42%;
            height: 8rem;
            background: #fff;
            margin: 0.6rem;
            border-radius: 1rem;
            
            .exp-box{
              width:100%;
              height: 100%;
              position: relative;
              border-radius: 1rem;
              .exp-title{
                position: absolute;
                bottom: 0;
                left:0;
                width:100%;
                background: rgba(0,0,0,0.2);
                backdrop-filter: blur(10px);
                padding: 0.6rem;
                border-radius: 1rem;
                >p{
                  font-size: 1rem;
                  color:#fff;
                  font-weight: bold;
                  text-align: center;
                }
              }
            }
        }
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
              width: 3rem;
              height: 3rem;
              background-size: cover;
              border-radius: 1.5rem;
            }

            .box {
                position: relative;
                display: flex;
                align-items: flex-start;
                justify-content: flex-start;
                max-width: 80%;
                // .arrow-r{
                //   border-left: 6px solid #ecfccb;
                //   right: 0px;
                //   position: absolute;
                //   top: 12px;
                //   width: 0px;
                //   height: 0px;
                //   border-top: 6px solid transparent;
                //   border-bottom: 6px solid transparent;
                //   box-sizing: border-box;
                // }
                // .arrow-l {
                //   border-right: 6px solid #ecfccb;
                //   left: 0px;
                //   position: absolute;
                //   top: 12px;
                //   width: 0px;
                //   height: 0px;
                //   border-top: 6px solid transparent;
                //   border-bottom: 6px solid transparent;
                //   box-sizing: border-box;
                // }
                .inner-box{
                  width: 100%;
                  margin-top:1rem;
                  display: flex;
                  flex-direction: column;
                  justify-content: center;
                  background: #f0f2f8;
                  padding: 0.6rem;
                  border-radius: 1.6rem;
                  padding: 1rem;
                  .time{
                    color:#888;
                    font-size: 0.8rem;
                  }
                  .desc{
                    color:#111;
                    margin-top: 0.2rem;
                    font-weight: bold;
                    font-size: 1em;
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
        width: 80%;
        border-radius: 1rem;
        background: #f0f2f8;
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
      background: #f0f2f8;
      border-radius: 2rem;
      width: 3rem;
      height: 3rem;
      transform: rotate(-45deg);
    }
  }
}

</style>