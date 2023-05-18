<template>
  <div class="ai-asr">
    <mobile-header title="实时语音识别"></mobile-header>
    <div class="asr-container">
      <div class="language-choose">
        <p class="title">请选择语言</p>
        <select class="languages">
          <option v-for="(item,index) in languages" :value="item.val" :selected="selectedLanguages.val === item.val">{{item.name}}</option>
        </select>
      </div>
      <input class="remark" v-model="remarkStr" placeholder="请输入备注：">
      <textarea id="result_textarea" class="result-textarea" readonly placeholder="识别结果：">{{resultContent}}</textarea>
      <div class="clear-btn" @click="clearContent">
        <p>清空识别内容</p>
        <XCircleIcon class="h-6 w-6 text-slate-950" />
      </div>
    </div>
    <div class="footer">
      <div class="down">
        <div class="s-btn" @click="recognize">
          <div class="icon">
            <SpeakerWaveIcon v-if="!isRecognizing" class="h-6 w-6 text-slate-950" />
            <SpeakerXMarkIcon v-else class="h-6 w-6 text-slate-950" />
          </div>
          <p>{{isRecognizing?'停止识别':'开始识别'}}</p>
        </div>
        <!-- <div class="s-btn" @click="stop">
          <SpeakerXMarkIcon class="h-6 w-6 text-slate-950" />
          <p>停止识别</p>
        </div> -->
      </div>
      
    </div>
  </div>
</template>

<script>
import {Languages} from './arg'
import {SpeakerWaveIcon,SpeakerXMarkIcon,XCircleIcon} from '@heroicons/vue/24/solid'
import MobileHeader from './component/MobileHeader.vue'
import delayLoad from '../../utils/delayLoad'
import {getAzureToken,createNote,sendNote,uploadFile} from '../../api/asrApi'
import env from '../../config/env'
import {getUuid} from '../../utils/utils'

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
        resultContent:'',
        azureTokenStr:'',//azure token
        recorder:null,// mp3 recorder
        soundContext:null,
        audioConfig:null,
        speechConfig:null,
        speechRecognizer:null,
        sessionId:null,
        remarkStr:'',//备注
        tokenTimer:null,
        regionOption:"eastasia",
        mp3Blob:null,
        isRecognizing:false,//识别中
      }
    },
    created(){
      this.selectedLanguages = this.languages[1]
    },
    mounted(){
      this.initSDK()
    },
    methods:{
      clearContent(){
          this.resultContent = ''
      },
      initSDK(){
        if(window.SpeechSDK){
          this.getInitAzureToken()
          this.createRecorder()
          return
        }
        'http://opencast.tsi.edu.sg/SpeechSDK-JavaScript-1.24.0/microsoft.cognitiveservices.speech.sdk.bundle.js'
        'http://opencast.tsi.edu.sg/js/recordmp3.js'
        const speechSDK =  `${env.host.base}/SpeechSDK-JavaScript-1.24.0/microsoft.cognitiveservices.speech.sdk.bundle.js`
        const mp3SDK = `${env.host.base}/js/recordmp3.js`
        delayLoad.delayLoadJS(speechSDK).then(()=>{
          delayLoad.delayLoadJS(mp3SDK).then(()=>{
              this.getInitAzureToken()
              this.createRecorder()
          })
        })
      },
      getInitAzureToken(){
        getAzureToken().then(res=>{
          this.azureTokenStr = res
        })
      },
      createRecorder(){
          this.recorder = new MP3Recorder({
            debug:true,
            funOk: () => {
                console.log('初始化成功');
            },
            funCancel: (msg) => {
                console.log(msg);
                this.recorder = null;
            }
        });
      },
      getUUID(){
        return getUuid()
      },
      checkAudioContent(){
         try {
            const AudioContext = window.AudioContext // our preferred impl
                || window.webkitAudioContext       // fallback, mostly when on Safari
                || false;                          // could not find.

            if (AudioContext) {
                this.soundContext = new AudioContext();
            } else {
                alert("Audio context not supported");
            }
        } catch (e) {
            window.console.log("no sound context found, no audio output. " + e);
        }
      },
      getDate(){
        const date = new Date();
        const Y = date.getFullYear() ;
        const M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) ;
        const D = String(date.getDate()).padStart(2, '0');
        const h = String(date.getHours()).padStart(2, '0');
        const m = String(date.getMinutes()).padStart(2, '0');
        const s = String(date.getSeconds()).padStart(2, '0');
        console.log(Y+M+D+h+m+s); 
        return Y + M + D + h + m + s
      },
      uploadMP3File(fileName){
        uploadFile({
          fileName,
          mp3Blob:this.mp3Blob,
          sessionId:this.sessionId
        }).then(res=>{
          console.log('uploadFile',res)
        })
      },
      stop(){
        if(this.recorder){
          // 停止录音&上传录音
          this.recorder.stop();
          const fileName='audio_recording_' + this.sessionId + "_" + this.getDate();
          this.recorder.getMp3Blob((blob) => {
              this.mp3Blob = blob;
              this.uploadMP3File(fileName);
          })
        }
        // stop  speechRecognizerContinuous
        clearTimeout(this.tokenTimer);
        if(this.speechRecognizer != undefined) {
            this.speechRecognizer.stopContinuousRecognitionAsync(() => {
                    this.speechRecognizer.close();
                    this.speechRecognizer = undefined;
                  },
                 (err) => {
                    this.speechRecognizer.close();
                    this.speechRecognizer = undefined;
                }
            );
        }
      },
      recognize(){
        if(this.isRecognizing){//停止识别
          this.stop()
          this.isRecognizing = false
        } else { // 开始识别
          // start speechRecognizerContinuous
          // const uuid = this.getUUID()
          this.doContinuousRecognition()
          this.isRecognizing = true
        }
          
          
      },
      doContinuousRecognition() {
            this.audioConfig = this.getAudioConfig();
            this.speechConfig = this.getSpeechConfig(SpeechSDK.SpeechConfig);
            if (!this.speechConfig) {
              return;
            }
            // Create the SpeechRecognizer and set up common event handlers and PhraseList data
            this.speechRecognizer = new SpeechSDK.SpeechRecognizer(this.speechConfig, this.audioConfig);
            this.applyCommonConfigurationTo(this.speechRecognizer);

            // Start the continuous recognition. Note that, in this continuous scenario, activity is purely event-
            // driven, as use of continuation (as is in the single-shot sample) isn't applicable when there's not a
            // single result.
            this.speechRecognizer.startContinuousRecognitionAsync();
            this.recorder.start();
            // scenarioStopButton.disabled = false;
            this.renewToken();

      },
      getAudioConfig() {
          return SpeechSDK.AudioConfig.fromDefaultMicrophoneInput();
      },
      getSpeechConfig(sdkConfigType) {
          let speechConfig;
          if (this.azureTokenStr) {
              speechConfig = sdkConfigType.fromAuthorizationToken(this.azureTokenStr, this.regionOption);
          } else {
              speechConfig = sdkConfigType.fromSubscription("using authorization token (hit F5 to refresh)", this.regionOption);
          }

          // Defines the language(s) that speech should be translated to.
          // Multiple languages can be specified for text translation and will be returned in a map.
          if (sdkConfigType == SpeechSDK.SpeechTranslationConfig) {
              speechConfig.addTargetLanguage('en-US, ZiraRUS');
          }

          speechConfig.speechRecognitionLanguage = this.selectedLanguages.val;
          return speechConfig;
      },
      applyCommonConfigurationTo(recognizer) {
          recognizer.recognizing = this.onRecognizing;
          recognizer.recognized = this.onRecognized;
          recognizer.canceled = this.onCanceled;
          recognizer.sessionStarted = this.onSessionStarted;
          recognizer.sessionStopped = this.onSessionStopped;
      },
      onRecognizing(sender, recognitionEventArgs) {
          console.log("onRecognizing============");
          // const result = recognitionEventArgs.result;
          // this.resultContent = this.resultContent.replace(/(.*)(^|[\r\n]+).*\[\.\.\.\][\r\n]+/, '$1$2') + `${result.text} [...]\r\n`;
          //  this.resultContent += `${result.text}\r\n`;
      },
      onRecognized(sender, recognitionEventArgs) {
          console.log("onRecognized====result========");
          this.onRecognizedResult(recognitionEventArgs.result);
          this.scrollToBottom()
      },
      onRecognizedResult(result) {
          switch (result.reason) {
              case SpeechSDK.ResultReason.NoMatch:
                  break;
              case SpeechSDK.ResultReason.Canceled:
                  break;
              case SpeechSDK.ResultReason.RecognizedSpeech:
              case SpeechSDK.ResultReason.TranslatedSpeech:
              case SpeechSDK.ResultReason.RecognizedIntent:
                  if (result.text) {
                      this.resultContent += `${result.text}\r\n`;
                  }
                  const intentJson = result.properties.getProperty(SpeechSDK.PropertyId.LanguageUnderstandingServiceResponse_JsonResult);
                  if (intentJson) {
                      this.resultContent += `${intentJson}\r\n`;
                  }
                  if (result.translations) {
                      const resultJson = JSON.parse(result.json);
                      resultJson['privTranslationPhrase']['Translation']['Translations'].forEach((translation) => {
                          this.resultContent += ` [${translation.Language}] ${translation.Text}\r\n`;
                      });
                  }
                  let resultText;
                  if (result.translations) {
                    const resultJson = JSON.parse(result.json);
                    resultText=resultJson['privTranslationPhrase']['Translation']['Translations'][0].Text
                  }else{
                      resultText=result.text;
                  }
                  const offset=  result.offset;
                  const duration=   result.duration;

                  if(resultText !== null  && resultText !== '' ){
                      sendNote({
                        resultText,
                        language:result.language,
                        sessionId:this.sessionId,
                        offset,
                        duration
                      }).then(res=>{
                        console.log('send note',res)
                      })
                  }
                  break;
          }
      },
      onSessionStarted(sender, sessionEventArgs) {
        console.log("onSessionStarted========");
        this.sessionId = sessionEventArgs.sessionId;
          createNote({
          sessionId:this.sessionId,
          remark:this.remarkStr
          }).then(res=>{
          console.log('create note',res)
          })
      },
      onCanceled (sender, cancellationEventArgs) {
          window.console.log(e);
          if (e.reason === SpeechSDK.CancellationReason.Error) {
              console.log(e.errorDetails);
          }
      },
      onSessionStopped(sender, sessionEventArgs) {
        // todo 
        console.log('onSessionStopped=====')
      },
      renewToken() {
        this.getInitAzureToken()
        this.tokenTimer = setTimeout(this.renewToken, 9*60*1000);
      },
      scrollToBottom(){
        const textarea = document.getElementById('result_textarea');
        textarea.scrollTop = textarea.scrollHeight
      }
    },
    
}
</script>

<style lang="scss">
.ai-asr{
  width: 100%;
  height: 100%;
  background: #fff;
  .asr-container {
    position: relative;
    width: 100%;
    height: calc(100% - 32%);
    background: #fff;
    border-bottom-left-radius: 2rem;
    border-bottom-right-radius: 2rem;
    border-bottom: 1px solid #f0f2f8;
    box-shadow: 0 2px 2px 2px #f0f2f8;
    .result-textarea {
      width: 90%;
      margin: 1rem 5%;
      height: 60%;
      resize: none;
      background: #f0f2f8;
      border-radius: 4px;
      text-indent: 0.6rem;
      padding-top: 0.4rem;
    }
    .clear-btn {
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
    .language-choose{
      display: flex;
      justify-content: flex-start;
      align-items: center;
      border-bottom: 1px solid #9ca3af;
      padding-left: 2rem;
      background: #fff;
      .title{
        font-size: 1rem;
        color: #333;
        font-weight: bold;
      }
      .languages {
        background: #fff;
        width: auto;
        height: 2rem;
        padding: 0 1.6rem;
        -moz-appearance: none;
        -webkit-appearance: none;
        appearance: none;
        font-size: 1rem;
        color: #333;
        font-weight: bold;
      }
    }
    
      .remark {
        width: 100%;
        height: 2rem;
        background: #fff;
        resize: none;
        color: #333;
        font-size: 1rem;
        text-indent: 2rem;
        border-bottom: 1px solid #9ca3af;
        &::placeholder {
          font-weight: bold;
          opacity: 0.5;
          color: #333;
          line-height: 2rem;
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
    .down{
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 1rem;
      .s-btn{
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        width: 7rem;
        height: 7rem;
        border-radius: 3.5rem;
        background: #0284c7;
        box-shadow: 0 8px 24px 0 rgba(18,97,255,.1);
        .icon {
          transform: scale(1.5);
          > svg {
            color: #fff;
          }
        }
        > p {
          margin-top: 0.6rem;
          color: #fff;
          font-weight: bold;
        }
      }
      
      }
    }
  }

</style>