import{_ as w}from"./Table.4fed16e0.js";import{C as D,m as R,r as x,t as z,o as T,D as O,a as c,b as U,c as j,d as a,w as d,e as n,f as S,g as i,v as h,G as B,H as L}from"./index.5c6e7eb3.js";const N={class:"record_data"},E=h("\u5BFC\u51FA"),H=h("\u67E5\u8BE2"),Q={__name:"RecordData",setup(q){const m=D();console.log(m.query.docId),R(null);const e=x({searchForm:{docId:m.query.docId,property:"en"},loading:!1,page:{current:1,size:10,total:1},tableTitle:[{prop:"id",label:"ID"},{prop:"docId",label:"\u6587\u6863ID"},{prop:"subtitle",label:"\u5185\u5BB9"},{prop:"timestamp",label:"\u521B\u5EFA\u65F6\u95F4"}],tableData:[],taskVisible:!1,taskTitle:"\u521B\u5EFA\u91C7\u96C6\u4EFB\u52A1",taskForm:{domain:"",docId:"",reamrk:""}}),{searchForm:P,loading:_,page:g,tableHeight:A,tableTitle:b,tableData:f,taskVisible:G,taskTitle:M,taskForm:$}=z(e);T(()=>{r()});const r=()=>{e.loading=!0,O(Object.assign(e.page,e.searchForm)).then(t=>{e.loading=!1,e.tableData=t.result.records,e.page.current=t.result.current,e.page.size=t.result.size,e.page.total=t.result.total})},y=()=>{e.loading=!0,B(Object.assign(e.searchForm)).then(t=>{if(!t.data)return;let l=window.URL.createObjectURL(new Blob([t.data])),o=document.createElement("a");o.style.display="none",o.href=l;let u="data_"+L(new Date)+"_"+e.searchForm.property+".txt";o.setAttribute("download",u),document.body.appendChild(o),o.click(),window.URL.revokeObjectURL(l),document.body.removeChild(o)})},v=t=>{e.page.size=t,r()},k=t=>{e.page.current=t,r()},C=()=>{e.searchForm={name:null},r()},F=()=>{r()};return(t,l)=>{const o=c("el-button"),u=c("el-input"),s=c("el-option"),I=c("el-select"),V=c("el-card");return U(),j("div",N,[a(V,null,{default:d(()=>[n("p",null,"\u6D4B\u8BD5this.searchForm.docId=========="+S(this.searchForm.docId),1),a(w,{data:i(f),column:i(b),operation:!1,page:i(g),loading:i(_),onOnSizeChange:v,onOnCurrentChange:k},{search:d(()=>[n("div",null,[a(o,{type:"primary",onClick:y},{default:d(()=>[E]),_:1})]),n("div",null,[a(u,{modelValue:this.searchForm.docId,"onUpdate:modelValue":l[0]||(l[0]=p=>this.searchForm.docId=p),type:"hidden"},null,8,["modelValue"])]),n("div",null,[a(I,{modelValue:this.searchForm.property,"onUpdate:modelValue":l[1]||(l[1]=p=>this.searchForm.property=p),placeholder:"\u8BF7\u9009\u62E9\u8BED\u8A00\u7C7B\u578B",style:{width:"70%"}},{default:d(()=>[a(s,{label:"\u82F1\u8BED",value:"en"}),a(s,{label:"\u4E2D\u6587",value:"zh-Hans"}),a(s,{label:"\u5FB7\u8BED",value:"de"}),a(s,{label:"\u6CD5\u8BED",value:"fr"}),a(s,{label:"\u65E5\u8BED",value:"ja"})]),_:1},8,["modelValue"])]),n("div",null,[a(o,{onClick:C,icon:"RefreshRight",circle:""})]),n("div",null,[a(o,{onClick:F,type:"primary",icon:"Search"},{default:d(()=>[H]),_:1})])]),_:1},8,["data","column","page","loading"])]),_:1})])}}};export{Q as default};
