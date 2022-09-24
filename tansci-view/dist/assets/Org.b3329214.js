import{_ as G}from"./Table.8aa6ccfc.js";import{m as J,r as K,t as P,o as Q,a4 as W,a as d,b as V,c as x,d as t,w as a,g as r,e as h,s as w,F as X,h as Y,v as i,E as u,a5 as Z,a6 as ee,T as le,a7 as oe,a8 as te,a9 as ae,aa as ne,i as re}from"./index.a325e992.js";const de={class:"org"},se=i("\u6DFB\u52A0"),ie=i("\u7F16\u8F91"),ue=i("\u6743\u9650"),me=i("\u5220\u9664"),ce={class:"dialog-footer"},pe=i("\u53D6 \u6D88"),_e=i("\u786E \u5B9A"),fe={class:"dialog-footer"},ge=i("\u53D6\u6D88"),be=i("\u786E\u5B9A"),ke={__name:"Org",setup(Ve){const I=J(null),e=K({searchForm:{name:null},loading:!1,tableTitle:[{prop:"name",label:"\u7EC4\u7EC7\u540D\u79F0",align:"left"},{prop:"sort",label:"\u6392\u5E8F"},{prop:"updateTime",label:"\u66F4\u65B0\u65F6\u95F4"},{prop:"createTime",label:"\u521B\u5EFA\u65F6\u95F4"}],tableData:[],addVisible:!1,addForm:{id:"",parentId:"",name:"",sort:0},operate:"",roleVisible:!1,roleLoading:!1,roleData:[],roleId:null,orgId:null}),{searchForm:y,loading:R,tableHeight:he,tableTitle:T,tableData:B,addVisible:p,addForm:m,operate:we,roleVisible:_,roleLoading:D,roleData:O,roleId:g,orgId:Ie}=P(e);Q(()=>{c()});const c=()=>{e.loading=!0,W(e.searchForm).then(o=>{e.loading=!1,e.tableData=o.result})},$=()=>{c()},L=o=>{e.addForm={id:null,parentId:o.column.row.id,name:"",sort:0},e.addVisible=!0,e.operate=1},U=o=>{if(o.column.row.parentId==0){u.warning("\u7CFB\u7EDF\u6839\u8282\u70B9\u4E0D\u5141\u8BB8\u7F16\u8F91!");return}e.addForm={id:o.column.row.id,parentId:o.column.row.parentId,name:o.column.row.name,sort:o.column.row.sort},e.addVisible=!0,e.operate=2},E=async()=>{if(e.operate==1){const o=r(I);if(!o)return;await o.validate(),Z(e.addForm).then(l=>{l&&(e.addVisible=!1,u.success("\u6DFB\u52A0\u6210\u529F\uFF01"),e.addForm={parentId:"",name:"",sort:0},c())})}else e.operate==2&&ee(e.addForm).then(o=>{o&&(e.addVisible=!1,u.success("\u66F4\u65B0\u6210\u529F\uFF01"),e.addForm={id:"",parentId:"",name:"",sort:0},c())})},A=o=>{if(o.column.row.parentId==0){u.warning("\u7CFB\u7EDF\u6839\u8282\u70B9\u4E0D\u5141\u8BB8\u5220\u9664!");return}le.confirm("\u6B64\u64CD\u4F5C\u5C06\u6C38\u4E45\u5220\u9664\u8BE5\u6587\u4EF6, \u662F\u5426\u7EE7\u7EED?","\u63D0\u793A",{confirmButtonText:"\u786E\u5B9A",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{let l={id:o.column.row.id};oe(l).then(f=>{f&&(u.success("\u5220\u9664\u6210\u529F!"),c())})}).catch(l=>{console.log(l)})},M=o=>{e.orgId=null,e.roleId=null,te({status:1}).then(l=>{l&&(e.roleData=l.result)}),ae({orgId:o.column.row.id}).then(l=>{l.result&&(e.roleId=l.result.roleId)}),e.orgId=o.column.row.id,e.roleVisible=!0},N=()=>{ne({orgId:e.orgId,roleId:e.roleId}).then(o=>{o&&u.success("\u5206\u914D\u6210\u529F\uFF01")}),e.roleVisible=!1};return(o,l)=>{const f=d("el-input"),s=d("el-button"),v=d("el-form-item"),k=d("el-col"),C=d("el-row"),q=d("el-input-number"),H=d("el-form"),F=d("el-dialog"),S=d("el-option"),j=d("el-select"),z=d("el-card");return V(),x("div",de,[t(z,null,{default:a(()=>[t(G,{data:r(B),column:r(T),operation:!0,page:!1,loading:r(R)},{search:a(()=>[h("div",null,[t(f,{onChange:$,modelValue:r(y).name,"onUpdate:modelValue":l[0]||(l[0]=n=>r(y).name=n),placeholder:"\u8BF7\u8F93\u5165\u540D\u79F0\u7B5B\u9009"},null,8,["modelValue"])])]),column:a(n=>[t(s,{onClick:b=>L(n),type:"text",style:{color:"var(--add)"}},{default:a(()=>[se]),_:2},1032,["onClick"]),t(s,{onClick:b=>U(n),type:"text",style:{color:"var(--edit)"}},{default:a(()=>[ie]),_:2},1032,["onClick"]),t(s,{onClick:b=>M(n),type:"text",style:{color:"var(--role)"}},{default:a(()=>[ue]),_:2},1032,["onClick"]),t(s,{onClick:b=>A(n),type:"text",style:{color:"var(--delete)"}},{default:a(()=>[me]),_:2},1032,["onClick"])]),_:1},8,["data","column","loading"]),t(F,{title:"\u65B0\u589E\u7EC4\u7EC7",modelValue:r(p),"onUpdate:modelValue":l[4]||(l[4]=n=>w(p)?p.value=n:null),"show-close":!1,width:"30%"},{footer:a(()=>[h("span",ce,[t(s,{onClick:l[3]||(l[3]=n=>p.value=!1)},{default:a(()=>[pe]),_:1}),t(s,{type:"primary",onClick:E},{default:a(()=>[_e]),_:1})])]),default:a(()=>[t(H,{model:r(m),rules:o.rules,ref_key:"addRuleForm",ref:I,"label-position":"right","label-width":"100px"},{default:a(()=>[t(C,{gutter:20},{default:a(()=>[t(k,null,{default:a(()=>[t(v,{label:"\u7EC4\u7EC7\u540D\u79F0",prop:"name",rules:[{required:!0,message:"\u7EC4\u7EC7\u540D\u4E0D\u80FD\u4E3A\u7A7A",trigger:"blur"}]},{default:a(()=>[t(f,{modelValue:r(m).name,"onUpdate:modelValue":l[1]||(l[1]=n=>r(m).name=n),placeholder:"\u8BF7\u8F93\u5165\u7EC4\u7EC7\u540D\u79F0",style:{width:"100%"}},null,8,["modelValue"])]),_:1})]),_:1})]),_:1}),t(C,{gutter:20},{default:a(()=>[t(k,null,{default:a(()=>[t(v,{label:"\u6392\u5E8F",prop:"sort",rules:[{required:!0,message:"\u6392\u5E8F\u4E0D\u80FD\u4E3A\u7A7A",trigger:"blur"}]},{default:a(()=>[t(q,{modelValue:r(m).sort,"onUpdate:modelValue":l[2]||(l[2]=n=>r(m).sort=n),min:0,max:999,style:{width:"100%"}},null,8,["modelValue"])]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["modelValue"]),t(F,{title:"\u5206\u914D\u6743\u9650",modelValue:r(_),"onUpdate:modelValue":l[7]||(l[7]=n=>w(_)?_.value=n:null),width:"30%","show-close":!1},{footer:a(()=>[h("span",fe,[t(s,{onClick:l[6]||(l[6]=n=>_.value=!1)},{default:a(()=>[ge]),_:1}),t(s,{type:"primary",onClick:N,loading:r(D)},{default:a(()=>[be]),_:1},8,["loading"])])]),default:a(()=>[t(j,{modelValue:r(g),"onUpdate:modelValue":l[5]||(l[5]=n=>w(g)?g.value=n:null),placeholder:"\u8BF7\u9009\u62E9\u89D2\u8272",style:{width:"100%"}},{default:a(()=>[(V(!0),x(X,null,Y(r(O),n=>(V(),re(S,{key:n.id,label:n.name,value:n.id},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1},8,["modelValue"])]),_:1})])}}};export{ke as default};
