import{_ as $}from"./Table.231e28f6.js";import{m as M,r as P,t as j,o as A,ap as H,a as u,b as I,c as G,d as a,w as s,g as o,e as p,s as J,v as c,aq as K,E as k,ar as b,T as L,as as Q}from"./index.155e7f5c.js";const W={class:"task-config"},X=c("\u521B\u5EFA\u4EFB\u52A1"),Y=c("\u67E5\u8BE2"),Z=c("\u7F16\u8F91"),ee=c("\u5220\u9664"),te={class:"dialog-footer"},le=c("\u53D6\u6D88"),ae=c("\u63D0\u4EA4"),ie={__name:"TaskConfig",setup(oe){const _=M(null),l=P({searchForm:{name:null},loading:!1,page:{current:1,size:10,total:1},tableTitle:[{prop:"taskId",label:"\u4EFB\u52A1\u7F16\u7801"},{prop:"code",label:"\u4EFB\u52A1\u670D\u52A1"},{prop:"name",label:"\u4EFB\u52A1\u540D\u79F0"},{prop:"expression",label:"\u6267\u884C\u65F6\u95F4"},{prop:"status",alias:"statusName",label:"\u72B6\u6001",type:"switch",option:{activeValue:1,activeColor:"#13ce66",activeText:"\u542F\u7528",inactiveValue:0,inactiveColor:"#ff4949",inactiveText:"\u7981\u7528"}},{prop:"creater",label:"\u521B\u5EFA\u4EBA"},{prop:"updateTime",label:"\u66F4\u65B0\u65F6\u95F4"},{prop:"createTime",label:"\u521B\u5EFA\u65F6\u95F4"},{prop:"remarks",label:"\u5907\u6CE8"}],tableData:[],taskVisible:!1,taskTitle:"\u521B\u5EFA\u4EFB\u52A1",taskForm:{id:"",code:"",name:"",expression:"",remarks:""}}),{searchForm:h,loading:V,page:w,tableHeight:se,tableTitle:C,tableData:x,taskVisible:f,taskTitle:T,taskForm:r}=j(l);A(()=>{d()});const d=()=>{l.loading=!0,H(Object.assign(l.page,l.searchForm)).then(e=>{l.loading=!1,l.tableData=e.result.records,l.page.current=e.result.current,l.page.size=e.result.size,l.page.total=e.result.total})},v=e=>{l.page.size=e,d()},y=e=>{l.page.current=e,d()},F=()=>{l.searchForm={name:null},d()},R=()=>{d()},S=()=>{l.taskTitle="\u521B\u5EFA\u4EFB\u52A1",l.taskForm={id:"",code:"",name:"",expression:"",remarks:""},l.taskVisible=!0},z=e=>{l.taskTitle="\u4FEE\u6539\u4EFB\u52A1",l.taskForm={id:e.column.row.id,code:e.column.row.code,name:e.column.row.name,expression:e.column.row.expression,remarks:e.column.row.remarks},l.taskVisible=!0},B=async()=>{const e=o(_);!e||(await e.validate(),l.taskForm.id==null||l.taskForm.id==""?K(l.taskForm).then(t=>{t&&k.success("\u6DFB\u52A0\u6210\u529F!")}):b(l.taskForm).then(t=>{t&&k.success("\u4FEE\u6539\u6210\u529F!")}),l.taskVisible=!1,d())},U=e=>{L.confirm("\u6B64\u64CD\u4F5C\u5C06\u6C38\u4E45\u5220\u9664, \u662F\u5426\u7EE7\u7EED?","\u63D0\u793A",{confirmButtonText:"\u786E\u5B9A",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{let t={id:e.column.row.id};Q(t).then(i=>{i&&(k.success("\u5220\u9664\u6210\u529F!"),d())})}).catch(t=>{console.log(t)})},E=e=>{b({id:e.id,status:e.status}).then(t=>{t&&(k.success("\u64CD\u4F5C\u6210\u529F\uFF01"),d())})};return(e,t)=>{const i=u("el-button"),m=u("el-input"),q=u("el-card"),g=u("el-form-item"),D=u("el-form"),N=u("el-dialog");return I(),G("div",W,[a(q,null,{default:s(()=>[a($,{data:o(x),column:o(C),operation:!0,page:o(w),loading:o(V),onOnSizeChange:v,onOnCurrentChange:y,onOnSwitchChange:E},{search:s(()=>[p("div",null,[a(i,{type:"primary",onClick:S},{default:s(()=>[X]),_:1})]),p("div",null,[a(m,{modelValue:o(h).name,"onUpdate:modelValue":t[0]||(t[0]=n=>o(h).name=n),placeholder:"\u8BF7\u8F93\u5165\u540D\u79F0"},null,8,["modelValue"])]),p("div",null,[a(i,{onClick:F,icon:"RefreshRight",circle:""})]),p("div",null,[a(i,{onClick:R,type:"primary",icon:"Search"},{default:s(()=>[Y]),_:1})])]),column:s(n=>[a(i,{onClick:O=>z(n),type:"text",style:{color:"var(--edit)"}},{default:s(()=>[Z]),_:2},1032,["onClick"]),a(i,{onClick:O=>U(n),type:"text",style:{color:"var(--delete)"}},{default:s(()=>[ee]),_:2},1032,["onClick"])]),_:1},8,["data","column","page","loading"])]),_:1}),a(N,{title:o(T),modelValue:o(f),"onUpdate:modelValue":t[6]||(t[6]=n=>J(f)?f.value=n:null),width:"40%","show-close":!1},{footer:s(()=>[p("span",te,[a(i,{onClick:t[5]||(t[5]=n=>f.value=!1)},{default:s(()=>[le]),_:1}),a(i,{type:"primary",onClick:B},{default:s(()=>[ae]),_:1})])]),default:s(()=>[a(D,{model:o(r),rules:e.rules,ref_key:"addRuleForm",ref:_,"label-position":"left","label-width":"100px"},{default:s(()=>[a(g,{label:"\u4EFB\u52A1\u540D\u79F0",prop:"name",rules:[{required:!0,message:"\u4EFB\u52A1\u540D\u79F0\u4E0D\u80FD\u4E3A\u7A7A",trigger:"blur"}]},{default:s(()=>[a(m,{modelValue:o(r).name,"onUpdate:modelValue":t[1]||(t[1]=n=>o(r).name=n),placeholder:"\u8BF7\u8F93\u5165\u4EFB\u52A1\u540D\u79F0",style:{width:"100%"}},null,8,["modelValue"])]),_:1}),a(g,{label:"\u670D\u52A1\u540D\u79F0",prop:"code",rules:[{required:!0,message:"\u670D\u52A1\u540D\u79F0\u4E0D\u80FD\u4E3A\u7A7A",trigger:"blur"}]},{default:s(()=>[a(m,{modelValue:o(r).code,"onUpdate:modelValue":t[2]||(t[2]=n=>o(r).code=n),placeholder:"\u8BF7\u8F93\u5165\u670D\u52A1\u540D\u79F0",style:{width:"100%"}},null,8,["modelValue"])]),_:1}),a(g,{label:"cron\u8868\u8FBE\u5F0F",prop:"expression",rules:[{required:!0,message:"\u6267\u884C\u65F6\u95F4\u4E0D\u80FD\u4E3A\u7A7A",trigger:"blur"}]},{default:s(()=>[a(m,{modelValue:o(r).expression,"onUpdate:modelValue":t[3]||(t[3]=n=>o(r).expression=n),placeholder:"\u8BF7\u8F93\u5165\u6267\u884C\u65F6\u95F4",style:{width:"100%"}},null,8,["modelValue"])]),_:1}),a(g,{label:"\u4EFB\u52A1\u63CF\u8FF0",prop:"remarks"},{default:s(()=>[a(m,{modelValue:o(r).remarks,"onUpdate:modelValue":t[4]||(t[4]=n=>o(r).remarks=n),type:"textarea",placeholder:"\u8BF7\u8F93\u5165\u4EFB\u52A1\u63CF\u8FF0",rows:3,maxlength:"100","show-word-limit":"",style:{width:"100%"}},null,8,["modelValue"])]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["title","modelValue"])])}}};export{ie as default};