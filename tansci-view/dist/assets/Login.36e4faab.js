import{r as B,t as E,g as t,b as m,c as V,e as p,J as Z,f as L,s as q,K as G,A as S,L as K,u as j,M as Y,G as ee,l as W,H as te,a,d as o,w as u,n as se,i as k,F as oe,h as ne,N as le,p as re,k as ie,O as ae,E as $}from"./index.2f725967.js";import{_ as N}from"./plugin-vue_export-helper.21dcd24c.js";const ue={key:0,class:"sliding-verify"},ce=["onMousedown"],de={__name:"SlidingVerify",props:{successFun:{type:Function},successIcon:{type:String,default:"CircleCheckFilled"},successText:{type:String,default:"\u9A8C\u8BC1\u6210\u529F"},startIcon:{type:String,default:"DArrowRight"},startText:{type:String,default:"\u8BF7\u62D6\u52A8\u6ED1\u5757\u9A8C\u8BC1"},errorFun:{type:Function},status:{type:Boolean,default:!1}},setup(_,{expose:F}){const y=_,s=B({verifyRefresh:!0,disX:0,rangeStatus:y.status}),{verifyRefresh:x,disX:U,rangeStatus:v}=E(s);function e(c){let i=c.target,g=c.clientX,R=i.offsetWidth,w=i.parentElement.offsetWidth-R-2;if(s.rangeStatus)return!1;document.onmousemove=b=>{let X=b.clientX;s.disX=X-g,s.disX<=0&&(s.disX=0),s.disX>=w-R&&(s.disX=w),i.style.transition=".1s all",i.style.transform="translateX("+s.disX+"px)",b.preventDefault()},document.onmouseup=()=>{s.disX!==w?(i.style.transition=".5s all",i.style.transform="translateX(0)",y.errorFun(!1)):(s.rangeStatus=!0,y.successFun(!0)),document.onmousemove=null,document.onmouseup=null}}return F({onRefresh:()=>{s.verifyRefresh=!1,K(()=>{s.rangeStatus=!1,s.verifyRefresh=!0})}}),(c,i)=>t(x)?(m(),V("div",ue,[p("div",{class:G(["slider",t(v)?"success":""])},[p("div",{class:"i",onMousedown:Z(e,["self"])},L(t(v)?"\u2714":"\u203A\u203A"),41,ce),q(L(t(v)?_.successText:_.startText),1)],2)])):S("",!0)}};var fe=N(de,[["__scopeId","data-v-129bd281"]]);const me=_=>(re("data-v-2fc453d0"),_=_(),ie(),_),pe={class:"login-main"},_e={class:"login-logo"},ge={class:"login-form"},he=me(()=>p("div",{class:"login-form-title"},"\u6B22\u8FCE\u767B\u5F55",-1)),ye=q("\u767B\u5F55"),ve={key:1,class:"other-form"},we={class:"login-form-title"},ke=q("\u8D26\u53F7\u5BC6\u7801\u767B\u5F55"),Se={class:"other-login"},Fe={__name:"Login",setup(_){const F=j(),y=Y(),s=ee();let x=W(null),U=W();const v=new URL("/assets/login-left.c1712715.png",self.location).href,e=B({loginStyle:{height:""},loginForm:{username:"",password:"",verifyStatus:null,keepPassword:null},loginMode:!0,otherForm:{modes:[{id:1,name:"\u5FAE\u4FE1\u626B\u7801\u767B\u5F55",icon:new URL("/assets/wechat.72810488.svg",self.location).href},{id:2,name:"\u5FAE\u535A\u626B\u7801\u767B\u5F55",icon:new URL("/assets/weibo.7065d62c.svg",self.location).href},{id:3,name:"QQ\u626B\u7801\u767B\u5F55",icon:new URL("/assets/qq.63959c69.svg",self.location).href}],qrcodeTitle:"",qrcodeUrl:"",status:0},socket:null,socketUrl:"ws://localhost:8005/tansci/ws/"}),{loginStyle:T,loginForm:c,loginMode:i,otherForm:g,socket:R,socketUrl:z}=E(e);te(()=>{e.loginStyle.height=(document.body.clientHeight||document.documentElement.clientHeight)+"px"});const w=l=>{e.loginForm.verifyStatus=l},b=l=>{e.loginForm.verifyStatus=l},X=async()=>{const l=t(x);if(!l)return;await l.validate();let n={username:e.loginForm.username,password:e.loginForm.password};le(n).then(r=>{r&&(F.setUser(r.result),y.setToken(r.result.token),s.push({path:"main"}))}).catch(()=>{e.loginForm.verifyStatus=null,U.value.onRefresh()})},A=l=>{let n=e.otherForm.modes;if(l===1){let r=n.find(function(f){return f.id==l});e.otherForm.qrcodeTitle=r.name,e.otherForm.status=0,ae({}).then(f=>{if(f){e.otherForm.qrcodeUrl=f.result.qrcode;let h=e.socketUrl+f.result.state;M(h)}else $.warning(f.message)}),e.loginMode=!1}else $.warning("\u6682\u4E0D\u652F\u6301\u8BE5\u767B\u5F55\u65B9\u5F0F\uFF01")},M=l=>{"WebSocket"in window&&(e.socket=new WebSocket(l),e.socket.onmessage=function(n){let r=JSON.parse(n.data);r.status==200?(e.otherForm.status=1,F.setUser(r),y.setToken(r.token),s.push({path:"main"})):e.otherForm.status=2},e.socket.onopen=function(){console.log("\u8FDE\u63A5\u5DF2\u5EFA\u7ACB")},e.socket.onclose=function(){console.log("\u8FDE\u63A5\u5DF2\u5173\u95ED")},e.socket.onerror=function(){console.log("\u8FDE\u63A5\u5F02\u5E38,\u5C1D\u8BD5\u91CD\u65B0\u8FDE\u63A5"),M()},window.onbeforeunload=function(){e.socket.onclose()})};return(l,n)=>{const r=a("el-image"),f=a("el-input"),h=a("el-form-item"),D=a("el-checkbox"),C=a("el-button"),H=a("el-form"),I=a("el-result"),P=a("star-filled"),J=a("el-icon"),O=a("el-divider"),Q=a("el-card");return m(),V("div",{class:"login",style:se(t(T))},[o(Q,{shadow:"always"},{default:u(()=>[p("div",pe,[p("div",_e,[o(r,{src:t(v),style:{width:"100%",height:"100%"}},null,8,["src"])]),p("div",ge,[t(i)?(m(),k(H,{key:0,model:t(c),rules:l.rules,ref_key:"loginRuleForm",ref:x},{default:u(()=>[he,o(h,{prop:"username",rules:[{required:!0,message:"\u8BF7\u8F93\u5165\u7528\u6237\u540D",trigger:"blur"},{pattern:/^[a-zA-Z]\w{4,17}$/,message:"\u7528\u6237\u540D\u5F0F\u6709\u8BEF\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165",trigger:"blur"}]},{default:u(()=>[o(f,{modelValue:e.loginForm.username,"onUpdate:modelValue":n[0]||(n[0]=d=>e.loginForm.username=d),"prefix-icon":"Avatar",placeholder:"\u8BF7\u8F93\u5165\u7528\u6237\u540D\u79F0",style:{width:"100%"}},null,8,["modelValue"])]),_:1}),o(h,{prop:"password",rules:[{required:!0,message:"\u8BF7\u8F93\u5165\u5BC6\u7801",trigger:"blur"},{pattern:/^[a-zA-Z]\w{5,17}$/,message:"\u5BC6\u7801\u683C\u5F0F\u6709\u8BEF\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165",trigger:"blur"}]},{default:u(()=>[o(f,{type:"password",modelValue:t(c).password,"onUpdate:modelValue":n[1]||(n[1]=d=>t(c).password=d),"prefix-icon":"Lock","show-password":"",placeholder:"\u8BF7\u8F93\u5165\u5BC6\u7801",style:{width:"100%"}},null,8,["modelValue"])]),_:1}),o(h,{prop:"verifyStatus",rules:[{required:!0,message:"\u8BF7\u62D6\u52A8\u6ED1\u5757\u9A8C\u8BC1",trigger:"blur"}]},{default:u(()=>[o(fe,{ref_key:"slidingVerify",ref:U,status:t(c).verifyStatus,successFun:w,errorFun:b},null,8,["status"])]),_:1}),o(h,null,{default:u(()=>[o(D,{modelValue:t(c).keepPassword,"onUpdate:modelValue":n[2]||(n[2]=d=>t(c).keepPassword=d),label:"\u8BB0\u4F4F\u5BC6\u7801"},null,8,["modelValue"])]),_:1}),o(h,null,{default:u(()=>[o(C,{type:"primary",round:"",onClick:X,style:{width:"100%"}},{default:u(()=>[ye]),_:1})]),_:1})]),_:1},8,["model","rules"])):t(i)?S("",!0):(m(),V("div",ve,[p("div",we,L(t(g).qrcodeTitle),1),t(g).status==1?(m(),k(I,{key:0,icon:"success",title:"\u626B\u7801\u6210\u529F","sub-title":"\u6B63\u5728\u767B\u5F55\u4E2D\uFF0C\u8BF7\u7A0D\u540E\u3002"})):S("",!0),t(g).status==2?(m(),k(I,{key:1,icon:"error",title:"\u6388\u6743\u5931\u8D25","sub-title":"\u767B\u5F55\u6388\u6743\u5931\u8D25\uFF0C\u8BF7\u5237\u65B0\u91CD\u8BD5\u3002"})):S("",!0),t(g).status==0?(m(),k(r,{key:2,src:t(g).qrcodeUrl,lazy:!0,style:{width:"180px",height:"180px",cursor:"pointer"}},null,8,["src"])):S("",!0),p("div",null,[o(C,{onClick:n[3]||(n[3]=d=>i.value=!0),type:"text",icon:"UserFilled"},{default:u(()=>[ke]),_:1})])])),o(O,null,{default:u(()=>[o(J,null,{default:u(()=>[o(P)]),_:1})]),_:1}),p("div",Se,[(m(!0),V(oe,null,ne(t(g).modes,d=>(m(),k(r,{key:d.id,onClick:xe=>A(d.id),src:d.icon,style:{width:"32px",height:"32px",padding:"0 0.4rem",cursor:"pointer"}},null,8,["onClick","src"]))),128))])])])]),_:1})],4)}}};var Ue=N(Fe,[["__scopeId","data-v-2fc453d0"]]);export{Ue as default};
