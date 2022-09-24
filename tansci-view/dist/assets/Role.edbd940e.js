import{_ as H}from"./Table.0a83fa61.js";import{l as k,r as G,t as J,o as Q,aa as W,a as d,b as X,c as Y,d as t,w as a,g as s,e as f,q as C,s as i,ab as V,E as u,ac as Z,S as ee,ad as le,ae as oe,af as te,ag as ae}from"./index.3389bda0.js";import{_ as se}from"./plugin-vue_export-helper.21dcd24c.js";const ne={class:"role"},re=i("\u6DFB\u52A0\u89D2\u8272"),de=i("\u7F16\u8F91"),ie=i("\u6743\u9650"),ce=i("\u5220\u9664"),ue={class:"dialog-footer"},pe=i("\u53D6\u6D88"),me=i("\u786E\u5B9A"),_e={class:"dialog-footer"},fe=i("\u53D6\u6D88"),ge=i("\u786E\u5B9A"),he={__name:"Role",setup(ye){const g=k(null),h=k(null),e=G({loading:!1,page:{current:1,size:10,total:0},tableTitle:[{prop:"id",label:"\u7F16\u7801"},{prop:"name",label:"\u540D\u79F0"},{prop:"type",alias:"typeName",label:"\u7C7B\u578B"},{prop:"status",alias:"statusName",label:"\u72B6\u6001",type:"switch",option:{activeValue:1,activeColor:"#13ce66",activeText:"\u662F",inactiveValue:0,inactiveColor:"#ff4949",inactiveText:"\u5426"}},{prop:"updateTime",label:"\u66F4\u65B0\u65F6\u95F4"},{prop:"createTime",label:"\u521B\u5EFA\u65F6\u95F4"}],tableData:[],operate:1,title:"\u65B0\u589E\u89D2\u8272",addRoleVisible:!1,addRoleForm:{id:"",name:"",type:""},roleLoading:!1,roleVisible:!1,roleTrees:[],checkedKeys:[],roleId:null}),{loading:w,page:T,tableHeight:be,tableTitle:x,tableData:F,operate:ve,title:I,addRoleVisible:p,addRoleForm:m,roleLoading:N,roleVisible:_,roleTrees:$,checkedKeys:z,roleId:Re}=J(e);Q(()=>{c()});const c=()=>{e.loading=!0,W(Object.assign(e.page)).then(l=>{l&&(e.loading=!1,e.tableData=l.result.records,e.page.current=l.result.current,e.page.size=l.result.size,e.page.total=l.result.total)})},B=l=>{e.page.size=l,c()},K=l=>{e.page.current=l,c()},E=l=>{V({id:l.id,status:l.status}).then(o=>{o&&(u.success("\u64CD\u4F5C\u6210\u529F\uFF01"),c())})},L=async()=>{if(e.operate==1){const l=s(g);if(!l)return;await l.validate(),Z(e.addRoleForm).then(o=>{o&&(e.addRoleVisible=!1,u.success("\u6DFB\u52A0\u6210\u529F\uFF01"),e.addRoleForm={id:"",name:"",type:""},c())})}else e.operate==2&&V(e.addRoleForm).then(l=>{l&&(e.addRoleVisible=!1,u.success("\u66F4\u65B0\u6210\u529F\uFF01"),c(),e.addRoleForm={id:"",name:"",type:""})})},S=l=>{e.addRoleForm={id:l.column.row.id,name:l.column.row.name,type:Number(l.column.row.type)},e.operate=2,e.title="\u7F16\u8F91\u89D2\u8272",e.addRoleVisible=!0},D=l=>{ee.confirm("\u6B64\u64CD\u4F5C\u5C06\u6C38\u4E45\u5220\u9664\u8BE5\u6570\u636E, \u662F\u5426\u7EE7\u7EED?","\u63D0\u793A",{confirmButtonText:"\u786E\u5B9A",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{le({id:l.column.row.id}).then(o=>{o&&(u.success({type:"success",message:"\u5220\u9664\u6210\u529F!"}),c())})}).catch(o=>{console.log(o)})},M=l=>{e.roleTrees=[],e.checkedKeys=[],oe({roleId:l.column.row.id}).then(o=>{o&&(e.roleTrees=o.result,e.checkedKeys=te(o.result),e.roleId=l.column.row.id,e.roleVisible=!0)})},O=()=>{const l=s(h);if(l.getCheckedKeys().length==0)return u.error("\u8BF7\u5148\u9009\u62E9\u83DC\u5355\uFF01"),!1;e.roleLoading=!0;let o={roleId:e.roleId,menuIds:l.getCheckedKeys()};ae(o).then(r=>{r&&(u.success("\u5206\u914D\u6743\u9650\u6210\u529F\uFF01"),e.roleLoading=!1,e.roleVisible=!1)})};return(l,o)=>{const r=d("el-button"),U=d("el-input"),y=d("el-form-item"),b=d("el-option"),q=d("el-select"),A=d("el-form"),v=d("el-dialog"),P=d("el-tree"),j=d("el-card");return X(),Y("div",ne,[t(j,null,{default:a(()=>[t(H,{data:s(F),column:s(x),page:s(T),operation:!0,loading:s(w),onOnSizeChange:B,onOnCurrentChange:K,onOnSwitchChange:E},{search:a(()=>[f("div",null,[t(r,{type:"primary",onClick:o[0]||(o[0]=n=>p.value=!0)},{default:a(()=>[re]),_:1})])]),column:a(n=>[t(r,{onClick:R=>S(n),type:"text",style:{color:"var(--edit)"}},{default:a(()=>[de]),_:2},1032,["onClick"]),t(r,{onClick:R=>M(n),type:"text",style:{color:"var(--role)"}},{default:a(()=>[ie]),_:2},1032,["onClick"]),t(r,{onClick:R=>D(n),type:"text",style:{color:"var(--delete)"}},{default:a(()=>[ce]),_:2},1032,["onClick"])]),_:1},8,["data","column","page","loading"]),t(v,{title:s(I),modelValue:s(p),"onUpdate:modelValue":o[4]||(o[4]=n=>C(p)?p.value=n:null),width:"30%","show-close":!1},{footer:a(()=>[f("span",ue,[t(r,{onClick:o[3]||(o[3]=n=>p.value=!1)},{default:a(()=>[pe]),_:1}),t(r,{type:"primary",onClick:L},{default:a(()=>[me]),_:1})])]),default:a(()=>[t(A,{model:s(m),rules:l.rules,ref_key:"addRoleRuleForm",ref:g,"label-position":"right","label-width":"100px"},{default:a(()=>[t(y,{label:"\u89D2\u8272\u540D\u79F0",prop:"name",rules:[{required:!0,message:"\u89D2\u8272\u540D\u79F0\u4E0D\u80FD\u4E3A\u7A7A",trigger:"blur"}]},{default:a(()=>[t(U,{modelValue:s(m).name,"onUpdate:modelValue":o[1]||(o[1]=n=>s(m).name=n),placeholder:"\u8BF7\u8F93\u5165\u89D2\u8272\u540D\u79F0",style:{width:"90%"}},null,8,["modelValue"])]),_:1}),t(y,{label:"\u89D2\u8272\u7C7B\u578B",prop:"type",rules:[{required:!0,message:"\u8BF7\u9009\u62E9\u89D2\u8272\u7C7B\u578B",trigger:"change"}]},{default:a(()=>[t(q,{modelValue:s(m).type,"onUpdate:modelValue":o[2]||(o[2]=n=>s(m).type=n),placeholder:"\u8BF7\u9009\u89D2\u8272\u7C7B\u578B",style:{width:"90%"}},{default:a(()=>[t(b,{label:"\u5E73\u53F0\u89D2\u8272",value:0}),t(b,{label:"\u975E\u5E73\u53F0\u89D2\u8272",value:1})]),_:1},8,["modelValue"])]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["title","modelValue"]),t(v,{title:"\u5206\u914D\u6743\u9650",modelValue:s(_),"onUpdate:modelValue":o[6]||(o[6]=n=>C(_)?_.value=n:null),width:"30%","show-close":!1},{footer:a(()=>[f("span",_e,[t(r,{onClick:o[5]||(o[5]=n=>_.value=!1)},{default:a(()=>[fe]),_:1}),t(r,{type:"primary",onClick:O,loading:s(N)},{default:a(()=>[ge]),_:1},8,["loading"])])]),default:a(()=>[t(P,{data:s($),"show-checkbox":"","node-key":"id",ref_key:"roleTree",ref:h,"default-checked-keys":s(z),props:{children:"children",label:"chineseName"}},null,8,["data","default-checked-keys"])]),_:1},8,["modelValue"])]),_:1})])}}};var we=se(he,[["__scopeId","data-v-07013cb4"]]);export{we as default};
