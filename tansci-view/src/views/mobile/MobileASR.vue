<template>
  <div class="ai-asr">
    <mobile-header title="实时语音识别"></mobile-header>
    <div class="asr-container">
      <div class="clear-btn" @click="clearContent">
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
        <input v-model="remarkStr" placeholder="请输入备注：">
      </div>
      <div class="down">
        <div class="s-btn" @click="start">
          <SpeakerWaveIcon class="h-6 w-6 text-slate-950" />
          <p>开始识别</p></div>
        <div class="s-btn" @click="stop">
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
        timeID:null,
        regionOption:"eastasia",
        mp3Blob:null,
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
        console.log('9999')
        'http://opencast.tsi.edu.sg/SpeechSDK-JavaScript-1.24.0/microsoft.cognitiveservices.speech.sdk.bundle.js'
        'http://opencast.tsi.edu.sg/js/recordmp3.js'
        const speechSDK =  `${env.host.base}/SpeechSDK-JavaScript-1.24.0/microsoft.cognitiveservices.speech.sdk.bundle.js`
        const mp3SDK = `${env.host.base}/js/recordmp3.js`
        delayLoad.delayLoadJS(speechSDK).then(()=>{
          delayLoad.delayLoadJS(mp3SDK).then(()=>{
            console.log('load',window.SpeechSDK)
              this.getInitAzureToken()
              this.createRecorder()
          })
        })
      },
      getInitAzureToken(){
        getAzureToken().then(res=>{
          console.log('1',res)
          this.azureTokenStr = res
        })
      },
      createRecorder(){
          this.recorder = new MP3Recorder({
            debug:true,
            funOk: function () {
                console.log('初始化成功');
            },
            funCancel: function (msg) {
                console.log(msg);
                recorder = null;
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
        // stop  speechRecognizerContinuous
        // 停止录音
        this.recorder.stop();
        const fileName='audio_recording_' + this.sessionId + "_" + this.getDate();
        this.recorder.getMp3Blob((blob) => {
            this.mp3Blob = blob;
            this.uploadMP3File(fileName);
        })
        clearTimeout(this.timeID);
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
      start(){
          // start speechRecognizerContinuous
          const uuid = this.getUUID()
          this.doContinuousRecognition()
          
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
          const result = recognitionEventArgs.result;
          this.resultContent = this.resultContent.replace(/(.*)(^|[\r\n]+).*\[\.\.\.\][\r\n]+/, '$1$2') + `${result.text} [...]\r\n`;
          
      },
      onRecognized(sender, recognitionEventArgs) {
          console.log("onRecognized====result========");
          this.onRecognizedResult(recognitionEventArgs.result);
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
        this.timeID = setTimeout(this.renewToken, 9*60*1000);
      }
        
    },
    
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