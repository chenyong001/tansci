import{_ as C}from"./Table.231e28f6.js";import{l as b}from"./messageApi.613776d5.js";import{r as z,t as N,o as v,a as s,b as w,c as R,d as l,w as r,g as n,e as x}from"./index.155e7f5c.js";const B={class:"message-log"},M={__name:"MessageLog",setup(D){const t=z({loading:!1,page:{current:1,size:10,total:1},tableTitle:[{prop:"code",label:"\u6A21\u677F\u7F16\u7801"},{prop:"state",alias:"stateName",label:"\u72B6\u6001"},{prop:"sendTime",label:"\u53D1\u9001\u65F6\u95F4"},{prop:"content",label:"\u53D1\u9001\u5185\u5BB9"}],tableData:[]}),{loading:c,page:i,tableHeight:S,tableTitle:g,tableData:p}=N(t);v(()=>{a()});const a=()=>{t.loading=!0,b(t.page).then(e=>{t.loading=!1,t.tableData=e.result.records,t.page.current=e.result.current,t.page.size=e.result.size,t.page.total=e.result.total})},u=e=>{t.page.size=e,a()},d=e=>{t.page.current=e,a()},m=()=>{a()},_=(e,o)=>{e.row.state===0&&e.column.property==="stateName"?o({color:"#67C23A",fontWeight:"700"}):e.row.state===1&&e.column.property==="stateName"?o({color:"#f00",fontWeight:"700"}):o({})};return(e,o)=>{const f=s("el-button"),h=s("el-card");return w(),R("div",B,[l(h,null,{default:r(()=>[l(C,{data:n(p),column:n(g),page:n(i),loading:n(c),onOnSizeChange:u,onOnCurrentChange:d,onSetCellColor:_},{search:r(()=>[x("div",null,[l(f,{onClick:m,icon:"RefreshRight",circle:""})])]),_:1},8,["data","column","page","loading"])]),_:1})])}}};export{M as default};