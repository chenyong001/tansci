import{_ as ne}from"./Table.4fed16e0.js";import{_ as ae}from"./plugin-vue_export-helper.21dcd24c.js";import{r as J,t as K,a as u,b as p,c as b,e as y,d as l,w as s,f as se,g as a,F as C,h as x,i as V,B as j,v,m as G,o as re,S as H,s as ue,M as I,E as q,T as pe}from"./index.5c6e7eb3.js";import{t as de,a as ie,b as me,c as ce}from"./messageApi.9542e8c7.js";const _e={class:"container-template"},fe={class:"template-content"},ye=v("\u6DFB\u52A0\u53D8\u91CF"),ge={__name:"MsgTemplate",props:{content:{type:String,default:"default"}},emits:["contentEvent"],setup(A,{expose:T,emit:F}){const n=A,r=J({nodes:[{id:1,val:"",param:!1,isShow:!0}],contentstr:""}),{nodes:E,contentstr:L}=K(r),O=()=>{if(n.content){let d=n.content.split(/\${[param\d]+\}/g),m=[];for(let i=0;i<d.length;i++)m.push({id:i+1,val:d[i],param:i!=d.length-1,isShow:i==0});r.nodes=m,r.contentstr=n.content}else r.nodes=[{id:1,val:"",param:!1,isShow:!0}],r.contentstr=""},w=()=>{let d="";r.nodes.forEach(m=>{d+=m.val,m.param&&(d+="${param"+m.id+"}")}),r.contentstr=d,F("contentEvent",r.contentstr)},B=()=>{r.nodes.push({id:r.nodes.length+1,val:"",param:!1,isShow:!1})},P=d=>{r.nodes.splice(d,1),w()};return T({onLoad:O}),(d,m)=>{const i=u("info-filled"),R=u("el-icon"),c=u("el-input"),_=u("el-col"),z=u("el-checkbox"),N=u("el-button"),D=u("el-row");return p(),b("div",_e,[y("div",fe,[l(R,null,{default:s(()=>[l(i)]),_:1}),y("span",null,se(a(L)),1)]),(p(!0),b(C,null,x(a(E),(g,S)=>(p(),b("div",{class:"template-nodes",key:S},[l(D,{gutter:20},{default:s(()=>[l(_,{span:18},{default:s(()=>[l(c,{onChange:w,modelValue:g.val,"onUpdate:modelValue":h=>g.val=h,size:"small",style:{width:"100%"}},null,8,["modelValue","onUpdate:modelValue"])]),_:2},1024),l(_,{span:4},{default:s(()=>[l(z,{onChange:w,modelValue:g.param,"onUpdate:modelValue":h=>g.param=h},{default:s(()=>[ye]),_:2},1032,["modelValue","onUpdate:modelValue"])]),_:2},1024),l(_,{span:1},{default:s(()=>[g.isShow?(p(),V(N,{key:0,onClick:m[0]||(m[0]=h=>B()),type:"text",icon:"circle-plus"})):j("",!0),g.isShow?j("",!0):(p(),V(N,{key:1,onClick:h=>P(S),type:"text",icon:"remove"},null,8,["onClick"]))]),_:2},1024)]),_:2},1024)]))),128))])}}};var be=ae(ge,[["__scopeId","data-v-0318b206"]]);const he={class:"template"},ve=v("\u65B0\u589E\u6A21\u677F"),Ve=v("\u67E5\u8BE2"),Te=v("\u7F16\u8F91"),we=v("\u5220\u9664"),ke={class:"dialog-footer"},Ce=v("\u53D6\u6D88"),xe=v("\u63D0\u4EA4"),$e={__name:"Template",setup(A){const T=G(null),F=G(null),n=J({searchForm:{type:null,businessType:null,name:null},loading:!1,page:{current:1,size:10,total:1},tableTitle:[{prop:"code",label:"\u6A21\u677F\u7F16\u7801"},{prop:"name",label:"\u6A21\u677F\u540D\u79F0"},{prop:"type",alias:"typeName",label:"\u6A21\u677F\u7C7B\u578B"},{prop:"businessType",alias:"businessTypeName",label:"\u4E1A\u52A1\u7C7B\u578B"},{prop:"state",alias:"stateName",label:"\u72B6\u6001"},{prop:"creater",label:"\u521B\u5EFA\u4EBA"},{prop:"updateTime",label:"\u66F4\u65B0\u65F6\u95F4"},{prop:"createTime",label:"\u521B\u5EFA\u65F6\u95F4"},{prop:"remarks",label:"\u5907\u6CE8"}],tableData:[],templateTypes:[],businessTypes:[],templateVisible:!1,templateTitle:"\u6DFB\u52A0\u6A21\u677F",templateForm:{id:"",type:"",businessType:"",name:"",content:"",remark:""}}),{searchForm:r,loading:E,page:L,tableHeight:O,tableTitle:w,tableData:B,statusList:P,templateTypes:d,businessTypes:m,templateVisible:i,templateTitle:R,templateForm:c}=K(n);re(()=>{_(),S()});const _=()=>{n.loading=!0,de(Object.assign(n.page,n.searchForm)).then(e=>{n.loading=!1,n.tableData=e.result.records,n.page.current=e.result.current,n.page.size=e.result.size,n.page.total=e.result.total})},z=e=>{n.page.size=e,_()},N=e=>{n.page.current=e,_()},D=()=>{n.searchForm={type:null,businessType:null,name:null},_()},g=()=>{_()},S=()=>{H({groupName:"template_type"}).then(e=>{e&&(n.templateTypes=e.result)}),H({groupName:"template_business_type"}).then(e=>{e&&(n.businessTypes=e.result)})},h=()=>{n.templateForm={id:"",type:"",businessType:"",name:"",content:"",remark:""},I(()=>{T.value.onLoad()}),n.templateVisible=!0},Q=e=>{n.templateTitle="\u4FEE\u6539\u6A21\u677F",n.templateForm={id:e.column.row.id,name:e.column.row.name,content:e.column.row.content,type:Number(e.column.row.type),businessType:Number(e.column.row.businessType),remark:e.column.row.remark},I(()=>{T.value.onLoad()}),n.templateVisible=!0},W=e=>{n.templateForm.content=e},X=async()=>{const e=a(F);!e||(await e.validate(),n.templateForm.id==null||n.templateForm.id==""?ie(n.templateForm).then(o=>{o&&q.success("\u6DFB\u52A0\u6A21\u677F\u6210\u529F!")}):me(n.templateForm).then(o=>{o&&q.success("\u4FEE\u6539\u6A21\u677F\u6210\u529F!")}),n.templateVisible=!1,_())},Y=e=>{pe.confirm("\u6B64\u64CD\u4F5C\u5C06\u6C38\u4E45\u5220\u9664, \u662F\u5426\u7EE7\u7EED?","\u63D0\u793A",{confirmButtonText:"\u786E\u5B9A",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{let o={id:e.column.row.id};ce(o).then(f=>{f&&(q.success("\u5220\u9664\u6210\u529F!"),_())})}).catch(o=>{console.log(o)})},Z=(e,o)=>{e.row.state===0&&e.column.property==="stateName"?o({color:"#E6A23C"}):e.row.state===1&&e.column.property==="stateName"?o({color:"#67C23A"}):e.row.state===2&&e.column.property==="stateName"?o({color:"#f56c6c"}):o({})};return(e,o)=>{const f=u("el-button"),U=u("el-option"),$=u("el-select"),M=u("el-input"),ee=u("el-card"),k=u("el-form-item"),te=u("el-form"),le=u("el-dialog");return p(),b("div",he,[l(ee,null,{default:s(()=>[l(ne,{data:a(B),column:a(w),operation:!0,page:a(L),loading:a(E),onOnSizeChange:z,onOnCurrentChange:N,onSetCellColor:Z},{search:s(()=>[y("div",null,[l(f,{type:"primary",onClick:h},{default:s(()=>[ve]),_:1})]),y("div",null,[l($,{modelValue:a(r).type,"onUpdate:modelValue":o[0]||(o[0]=t=>a(r).type=t),placeholder:"\u8BF7\u9009\u62E9\u5B57\u5178\u7C7B\u578B",style:{width:"100%"}},{default:s(()=>[(p(!0),b(C,null,x(a(d),t=>(p(),V(U,{key:t,label:t.dicLabel,value:t.dicValue},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),y("div",null,[l($,{modelValue:a(r).businessType,"onUpdate:modelValue":o[1]||(o[1]=t=>a(r).businessType=t),placeholder:"\u8BF7\u9009\u62E9\u4E1A\u52A1\u7C7B\u578B",style:{width:"100%"}},{default:s(()=>[(p(!0),b(C,null,x(a(m),t=>(p(),V(U,{key:t,label:t.dicLabel,value:t.dicValue},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),y("div",null,[l(M,{modelValue:a(r).name,"onUpdate:modelValue":o[2]||(o[2]=t=>a(r).name=t),placeholder:"\u8BF7\u8F93\u5165\u540D\u79F0"},null,8,["modelValue"])]),y("div",null,[l(f,{onClick:D,icon:"RefreshRight",circle:""})]),y("div",null,[l(f,{onClick:g,type:"primary",icon:"Search"},{default:s(()=>[Ve]),_:1})])]),column:s(t=>[l(f,{onClick:oe=>Q(t),type:"text",style:{color:"var(--edit)"}},{default:s(()=>[Te]),_:2},1032,["onClick"]),l(f,{onClick:oe=>Y(t),type:"text",style:{color:"var(--delete)"}},{default:s(()=>[we]),_:2},1032,["onClick"])]),_:1},8,["data","column","page","loading"])]),_:1}),l(le,{title:a(R),modelValue:a(i),"onUpdate:modelValue":o[8]||(o[8]=t=>ue(i)?i.value=t:null),width:"40%","show-close":!1},{footer:s(()=>[y("span",ke,[l(f,{onClick:o[7]||(o[7]=t=>i.value=!1)},{default:s(()=>[Ce]),_:1}),l(f,{type:"primary",onClick:X},{default:s(()=>[xe]),_:1})])]),default:s(()=>[l(te,{model:a(c),rules:e.rules,ref_key:"addRuleForm",ref:F,"label-position":"left","label-width":"100px"},{default:s(()=>[l(k,{label:"\u6A21\u677F\u7C7B\u578B",prop:"type",rules:[{required:!0,message:"\u6A21\u677F\u7C7B\u578B\u4E0D\u80FD\u4E3A\u7A7A",trigger:"change"}]},{default:s(()=>[l($,{modelValue:a(c).type,"onUpdate:modelValue":o[3]||(o[3]=t=>a(c).type=t),placeholder:"\u8BF7\u9009\u62E9\u5B57\u5178\u7C7B\u578B",style:{width:"100%"}},{default:s(()=>[(p(!0),b(C,null,x(a(d),t=>(p(),V(U,{key:t,label:t.dicLabel,value:t.dicValue},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1}),l(k,{label:"\u4E1A\u52A1\u7C7B\u578B",prop:"businessType",rules:[{required:!0,message:"\u4E1A\u52A1\u7C7B\u578B\u4E0D\u80FD\u4E3A\u7A7A",trigger:"change"}]},{default:s(()=>[l($,{modelValue:a(c).businessType,"onUpdate:modelValue":o[4]||(o[4]=t=>a(c).businessType=t),placeholder:"\u8BF7\u9009\u62E9\u4E1A\u52A1\u7C7B\u578B",style:{width:"100%"}},{default:s(()=>[(p(!0),b(C,null,x(a(m),t=>(p(),V(U,{key:t,label:t.dicLabel,value:t.dicValue},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1}),l(k,{label:"\u6A21\u677F\u540D\u79F0",prop:"name",rules:[{required:!0,message:"\u6A21\u677F\u540D\u79F0\u4E0D\u80FD\u4E3A\u7A7A",trigger:"blur"}]},{default:s(()=>[l(M,{modelValue:a(c).name,"onUpdate:modelValue":o[5]||(o[5]=t=>a(c).name=t),placeholder:"\u8BF7\u8F93\u5165\u6A21\u677F\u540D\u79F0",style:{width:"100%"}},null,8,["modelValue"])]),_:1}),l(k,{label:"\u6A21\u677F\u5185\u5BB9",prop:"content",rules:[{required:!0,message:"\u6A21\u677F\u5185\u5BB9\u4E0D\u80FD\u4E3A\u7A7A",trigger:"blur"}]},{default:s(()=>[l(be,{ref_key:"msgTemplateRef",ref:T,content:a(c).content,onContentEvent:W,style:{width:"100%"}},null,8,["content"])]),_:1}),l(k,{label:"\u7533\u8BF7\u8BF4\u660E",prop:"remark",rules:[{required:!0,message:"\u7533\u8BF7\u8BF4\u660E\u4E0D\u80FD\u4E3A\u7A7A",trigger:"blur"}]},{default:s(()=>[l(M,{modelValue:a(c).remark,"onUpdate:modelValue":o[6]||(o[6]=t=>a(c).remark=t),type:"textarea",placeholder:"\u8BF7\u8F93\u5165\u6A21\u677F\u7533\u8BF7\u8BF4\u660E",rows:3,maxlength:"100","show-word-limit":"",style:{width:"100%"}},null,8,["modelValue"])]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["title","modelValue"])])}}};export{$e as default};
