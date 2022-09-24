import{m as me,r as ce,t as ge,o as fe,ai as _e,a4 as be,a as u,b as f,c as U,d as l,w as o,g as r,e as b,s as I,F as R,h as P,i as w,B as S,A as B,a0 as $,v as i,T as Ve,aj as ye,E as k,a8 as he,ak as we,al as ke,S as Ce,am as Fe,an as Ue,ao as Ie,f as ve}from"./index.5c6e7eb3.js";import{_ as Te}from"./Table.4fed16e0.js";const De={class:"user"},Le=i("\u6DFB\u52A0\u7528\u6237"),Ne=i("\u67E5\u8BE2"),xe=i("\u7F16\u8F91"),Re=i("\u6743\u9650"),Pe=i("\u5220\u9664"),Se={class:"dialog-footer"},Be=i("\u53D6\u6D88"),$e=i("\u786E\u5B9A"),ze=i("\u7537"),qe=i("\u5973"),Ae={class:"dialog-footer"},Ee=i("\u53D6\u6D88"),Oe=i("\u63D0\u4EA4"),Je={__name:"User",setup(Me){const v=me(null),t=ce({searchForm:{nickname:null},loading:!1,page:{current:1,size:10,total:1},tableTitle:[{prop:"",label:"",fixed:"left"},{prop:"username",label:"\u540D\u79F0"},{prop:"nickname",label:"\u6635\u79F0"},{prop:"type",alias:"typeName",label:"\u7C7B\u578B"},{prop:"gender",alias:"genderName",label:"\u6027\u522B"},{prop:"birthday",label:"\u51FA\u751F\u65E5\u671F"},{prop:"address",label:"\u5730\u5740"},{prop:"phone",label:"\u624B\u673A\u53F7"},{prop:"openId",label:"\u5FAE\u4FE1\u552F\u4E00\u6807\u8BC6"},{prop:"email",label:"\u90AE\u7BB1"},{prop:"delFlag",alias:"delFlagName",label:"\u5220\u9664\u72B6\u6001"},{prop:"updateTime",label:"\u66F4\u65B0\u65F6\u95F4"},{prop:"createTime",label:"\u521B\u5EFA\u65F6\u95F4"},{prop:"remarks",label:"\u5907\u6CE8"}],tableData:[],roleVisible:!1,roleLoading:!1,roleData:[],roleId:null,userId:null,userVisible:!1,userTitle:"\u6DFB\u52A0\u7528\u6237",operate:0,userTypeList:[],userForm:{id:"",username:"",nickname:"",type:"",gender:"",birthday:"",address:"",phone:"",email:"",password:"",rePassword:"",orgId:""},orgData:[]}),{searchForm:T,loading:z,page:q,tableHeight:je,tableTitle:A,tableData:E,statusList:We,roleVisible:V,roleLoading:O,roleData:M,roleId:C,userId:Ze,userVisible:y,userTitle:j,operate:h,userTypeList:W,userForm:n,orgData:Z}=ge(t);fe(()=>{g(),Q()});const g=()=>{t.loading=!0,_e(Object.assign(t.page,t.searchForm)).then(s=>{t.loading=!1,t.tableData=s.result.records,t.page.current=s.result.current,t.page.size=s.result.size,t.page.total=s.result.total})},G=s=>{t.page.size=s,g()},H=s=>{t.page.current=s,g()},J=()=>{t.searchForm={nickname:null},g()},K=()=>{g()},Q=()=>{be().then(s=>{t.orgData=s.result})},X=s=>{t.userTitle="\u4FEE\u6539\u7528\u6237",t.operate=1,t.userForm={id:s.column.row.id,username:s.column.row.username,nickname:s.column.row.nickname,type:Number(s.column.row.type),gender:Number(s.column.row.gender),birthday:s.column.row.birthday,address:s.column.row.address,phone:s.column.row.phone,email:s.column.row.email,password:"p123456",rePassword:"p123456",orgId:s.column.row.orgId},D(),t.userVisible=!0},Y=s=>{Ve.confirm("\u6B64\u64CD\u4F5C\u5C06\u6C38\u4E45\u5220\u9664\u8BE5\u6587\u4EF6, \u662F\u5426\u7EE7\u7EED?","\u63D0\u793A",{confirmButtonText:"\u786E\u5B9A",cancelButtonText:"\u53D6\u6D88",type:"warning"}).then(()=>{let e={id:s.column.row.id};ye(e).then(d=>{d&&(k.success("\u5220\u9664\u6210\u529F!"),g())})}).catch(e=>{console.log(e)})},ee=s=>{t.userId=null,t.roleId=null,he({status:1}).then(e=>{e&&(t.roleData=e.result)}),we({userId:s.column.row.id}).then(e=>{e.result&&(t.roleId=e.result.roleId)}),t.userId=s.column.row.id,t.roleVisible=!0},le=()=>{ke({userId:t.userId,roleId:t.roleId}).then(s=>{s&&k.success("\u5206\u914D\u6210\u529F\uFF01")}),t.roleVisible=!1},oe=(s,e)=>{s.row.delFlag===0&&s.column.property==="delFlagName"?e({color:"#67C23A",fontWeight:"700"}):s.row.delFlag===1&&s.column.property==="delFlagName"?e({color:"#f00",fontWeight:"700"}):e({})},ae=()=>{t.userTitle="\u6DFB\u52A0\u7528\u6237",t.operate=0,t.userForm={id:"",username:"",nickname:"",type:"",gender:"",birthday:"",address:"",phone:"",email:"",password:"",rePassword:"",orgId:""},D(),t.userVisible=!0},re=(s,e,d)=>{e===""?d(new Error("\u8BF7\u8F93\u5165\u786E\u8BA4\u5BC6\u7801")):e!==t.userForm.password?d(new Error("\u4E24\u6B21\u8F93\u5165\u5BC6\u7801\u4E0D\u4E00\u81F4!")):d()},D=()=>{Ce({groupName:"user_type"}).then(s=>{t.userTypeList=s.result})},te=async()=>{const s=r(v);!s||(await s.validate(),t.userForm.birthday instanceof Date&&(t.userForm.birthday=Fe(t.userForm.birthday)),t.operate==0?Ue(t.userForm).then(e=>{e&&(k.success({message:"\u6DFB\u52A0\u6210\u529F\uFF01",type:"success"}),g(),t.userVisible=!1)}):(t.userForm.password=null,Ie(t.userForm).then(e=>{e&&(k.success({message:"\u4FEE\u6539\u6210\u529F\uFF01",type:"success"}),g(),t.userVisible=!1)})))};return(s,e)=>{const d=u("el-button"),c=u("el-input"),se=u("el-option"),ne=u("el-select"),L=u("el-dialog"),p=u("el-form-item"),m=u("el-col"),_=u("el-row"),F=u("el-radio"),N=u("el-radio-group"),de=u("el-date-picker"),ue=u("el-cascader"),ie=u("el-form"),pe=u("el-card");return f(),U("div",De,[l(pe,null,{default:o(()=>[l(Te,{data:r(E),column:r(A),operation:!0,page:r(q),loading:r(z),onOnSizeChange:G,onOnCurrentChange:H,onSetCellColor:oe},{search:o(()=>[b("div",null,[l(d,{type:"primary",onClick:ae},{default:o(()=>[Le]),_:1})]),b("div",null,[l(c,{modelValue:r(T).nickname,"onUpdate:modelValue":e[0]||(e[0]=a=>r(T).nickname=a),placeholder:"\u8BF7\u8F93\u5165\u7528\u6237\u540D\u79F0"},null,8,["modelValue"])]),b("div",null,[l(d,{onClick:J,icon:"RefreshRight",circle:""})]),b("div",null,[l(d,{onClick:K,type:"primary",icon:"Search"},{default:o(()=>[Ne]),_:1})])]),column:o(a=>[l(d,{onClick:x=>X(a),type:"text",style:{color:"var(--edit)"}},{default:o(()=>[xe]),_:2},1032,["onClick"]),l(d,{onClick:x=>ee(a),type:"text",style:{color:"var(--role)"}},{default:o(()=>[Re]),_:2},1032,["onClick"]),l(d,{onClick:x=>Y(a),type:"text",style:{color:"var(--delete)"}},{default:o(()=>[Pe]),_:2},1032,["onClick"])]),_:1},8,["data","column","page","loading"]),l(L,{title:"\u5206\u914D\u6743\u9650",modelValue:r(V),"onUpdate:modelValue":e[3]||(e[3]=a=>I(V)?V.value=a:null),width:"30%","show-close":!1},{footer:o(()=>[b("span",Se,[l(d,{onClick:e[2]||(e[2]=a=>V.value=!1)},{default:o(()=>[Be]),_:1}),l(d,{type:"primary",onClick:le,loading:r(O)},{default:o(()=>[$e]),_:1},8,["loading"])])]),default:o(()=>[l(ne,{modelValue:r(C),"onUpdate:modelValue":e[1]||(e[1]=a=>I(C)?C.value=a:null),placeholder:"\u8BF7\u9009\u62E9\u89D2\u8272",style:{width:"100%"}},{default:o(()=>[(f(!0),U(R,null,P(r(M),a=>(f(),w(se,{key:a.id,label:a.name,value:a.id},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1},8,["modelValue"]),l(L,{title:r(j),modelValue:r(y),"onUpdate:modelValue":e[17]||(e[17]=a=>I(y)?y.value=a:null),"show-close":!1},{footer:o(()=>[b("span",Ae,[l(d,{onClick:e[16]||(e[16]=a=>y.value=!1)},{default:o(()=>[Ee]),_:1}),l(d,{type:"primary",onClick:te},{default:o(()=>[Oe]),_:1})])]),default:o(()=>[l(ie,{model:r(n),rules:s.rules,ref_key:"userRuleForm",ref:v,"status-icon":"","label-width":"100px"},{default:o(()=>[l(_,{gutter:20},{default:o(()=>[l(m,{span:12},{default:o(()=>[l(p,{prop:"username",label:"\u7528\u6237\u540D",rules:[{required:!0,message:"\u8BF7\u8F93\u5165\u540D\u79F0",trigger:"blur"},{pattern:/^[a-zA-Z]\w{4,17}$/,message:"\u4EE5\u5B57\u6BCD\u5F00\u5934\uFF0C\u957F\u5EA6\u57285~18\u4E4B\u95F4\uFF0C\u53EA\u80FD\u5305\u542B\u5B57\u6BCD\u3001\u6570\u5B57",trigger:"blur"}]},{default:o(()=>[r(h)==0?(f(),w(c,{key:0,modelValue:r(n).username,"onUpdate:modelValue":e[4]||(e[4]=a=>r(n).username=a),placeholder:"\u8BF7\u8F93\u5165\u540D\u79F0"},null,8,["modelValue"])):S("",!0),r(h)==1?(f(),w(c,{key:1,disabled:"",modelValue:r(n).username,"onUpdate:modelValue":e[5]||(e[5]=a=>r(n).username=a),placeholder:"\u8BF7\u8F93\u5165\u540D\u79F0"},null,8,["modelValue"])):S("",!0)]),_:1})]),_:1}),l(m,{span:12},{default:o(()=>[l(p,{prop:"nickname",label:"\u6635\u79F0",rules:[{required:!0,message:"\u8BF7\u8F93\u5165\u6635\u79F0",trigger:"blur"}]},{default:o(()=>[l(c,{modelValue:r(n).nickname,"onUpdate:modelValue":e[6]||(e[6]=a=>r(n).nickname=a),placeholder:"\u8BF7\u8F93\u5165\u6635\u79F0"},null,8,["modelValue"])]),_:1})]),_:1})]),_:1}),l(_,{gutter:20},{default:o(()=>[l(m,{span:12},{default:o(()=>[l(p,{prop:"type",label:"\u7C7B\u578B",rules:[{required:!0,message:"\u8BF7\u9009\u62E9\u7C7B\u578B",trigger:"change"}]},{default:o(()=>[l(N,{modelValue:r(n).type,"onUpdate:modelValue":e[7]||(e[7]=a=>r(n).type=a)},{default:o(()=>[(f(!0),U(R,null,P(r(W),a=>(f(),w(F,{key:a,label:a.dicValue},{default:o(()=>[i(ve(a.dicLabel),1)]),_:2},1032,["label"]))),128))]),_:1},8,["modelValue"])]),_:1})]),_:1}),l(m,{span:12},{default:o(()=>[l(p,{prop:"gender",label:"\u6027\u522B",rules:[{required:!0,message:"\u8BF7\u9009\u62E9\u6027\u522B",trigger:"change"}]},{default:o(()=>[l(N,{modelValue:r(n).gender,"onUpdate:modelValue":e[8]||(e[8]=a=>r(n).gender=a)},{default:o(()=>[l(F,{label:0},{default:o(()=>[ze]),_:1}),l(F,{label:1},{default:o(()=>[qe]),_:1})]),_:1},8,["modelValue"])]),_:1})]),_:1})]),_:1}),l(_,{gutter:20},{default:o(()=>[l(m,{span:12},{default:o(()=>[l(p,{prop:"birthday",label:"\u51FA\u751F\u65E5\u671F"},{default:o(()=>[l(de,{modelValue:r(n).birthday,"onUpdate:modelValue":e[9]||(e[9]=a=>r(n).birthday=a),type:"date",placeholder:"\u9009\u62E9\u65E5\u671F"},null,8,["modelValue"])]),_:1})]),_:1}),l(m,{span:12},{default:o(()=>[l(p,{prop:"email",label:"\u90AE\u7BB1\u5730\u5740"},{default:o(()=>[l(c,{modelValue:r(n).email,"onUpdate:modelValue":e[10]||(e[10]=a=>r(n).email=a),placeholder:"\u8BF7\u8F93\u5165\u90AE\u7BB1\u5730\u5740"},null,8,["modelValue"])]),_:1})]),_:1})]),_:1}),l(_,{gutter:20},{default:o(()=>[l(m,{span:12},{default:o(()=>[l(p,{prop:"phone",label:"\u624B\u673A\u53F7",rules:[{required:!0,message:"\u8BF7\u8F93\u5165\u624B\u673A\u53F7",trigger:"blur"},{pattern:/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/,message:"\u8BF7\u8F93\u5165\u6B63\u786E\u7684\u624B\u673A\u53F7",trigger:"blur"}]},{default:o(()=>[l(c,{modelValue:r(n).phone,"onUpdate:modelValue":e[11]||(e[11]=a=>r(n).phone=a),placeholder:"\u8BF7\u8F93\u5165\u624B\u673A\u53F7"},null,8,["modelValue"])]),_:1},8,["rules"])]),_:1}),l(m,{span:12},{default:o(()=>[l(p,{prop:"address",label:"\u5730\u5740"},{default:o(()=>[l(c,{modelValue:r(n).address,"onUpdate:modelValue":e[12]||(e[12]=a=>r(n).address=a),placeholder:"\u8BF7\u8F93\u5165\u5730\u5740"},null,8,["modelValue"])]),_:1})]),_:1})]),_:1}),l(_,{gutter:20},{default:o(()=>[l(m,{span:12},{default:o(()=>[B(l(p,{prop:"password",label:"\u5BC6\u7801",rules:[{required:!0,message:"\u8BF7\u8F93\u5165\u5BC6\u7801",trigger:"blur"},{pattern:/^[a-zA-Z]\w{5,17}$/,message:"\u4EE5\u5B57\u6BCD\u5F00\u5934\uFF0C\u957F\u5EA6\u57286~18\u4E4B\u95F4\uFF0C\u53EA\u80FD\u5305\u542B\u5B57\u6BCD\u3001\u6570\u5B57\u548C\u4E0B\u5212\u7EBF",trigger:"blur"}]},{default:o(()=>[l(c,{modelValue:r(n).password,"onUpdate:modelValue":e[13]||(e[13]=a=>r(n).password=a),type:"password",placeholder:"\u8BF7\u8F93\u5165\u5BC6\u7801"},null,8,["modelValue"])]),_:1},512),[[$,r(h)==0]])]),_:1}),l(m,{span:12},{default:o(()=>[B(l(p,{prop:"rePassword",label:"\u786E\u8BA4\u5BC6\u7801",rules:[{validator:re,trigger:"blur"}]},{default:o(()=>[l(c,{modelValue:r(n).rePassword,"onUpdate:modelValue":e[14]||(e[14]=a=>r(n).rePassword=a),type:"password",placeholder:"\u8BF7\u8F93\u5165\u786E\u8BA4\u5BC6\u7801"},null,8,["modelValue"])]),_:1},8,["rules"]),[[$,r(h)==0]])]),_:1})]),_:1}),l(_,null,{default:o(()=>[l(m,null,{default:o(()=>[l(p,{label:"\u7EC4\u7EC7\u673A\u6784",prop:"orgId",rules:[{required:!0,message:"\u8BF7\u9009\u62E9\u7EC4\u7EC7\u673A\u6784",trigger:"change"}]},{default:o(()=>[l(ue,{modelValue:r(n).orgId,"onUpdate:modelValue":e[15]||(e[15]=a=>r(n).orgId=a),options:r(Z),props:{value:"id",label:"name",children:"children",checkStrictly:!0,emitPath:!1},clearable:"",filterable:"",style:{width:"100%"}},null,8,["modelValue","options"])]),_:1})]),_:1})]),_:1})]),_:1},8,["model","rules"])]),_:1},8,["title","modelValue"])]),_:1})])}}};export{Je as default};
