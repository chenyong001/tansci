import{r as O,t as U,a as c,y as E,b as l,c as p,e as f,z as v,A as L,i,w as r,F as y,h as R,d as s,v as b,f as C,B as _,g as h}from"./index.5c6e7eb3.js";const W={class:"table-container"},q={class:"search-wrap"},G={class:"table-wrap"},I={key:0,class:"pagination-wrap"},K={__name:"Table",props:{loading:{type:Boolean,default:!1},page:{type:Object,default:{current:1,size:10,total:0}},column:{type:Array,default:[]},operation:{type:Boolean,default:!1},tableHeight:{type:Number,default:null},headerCellStyle:{type:Object,default:{color:"#606266",fontWeight:700,background:"var(--bg1)"}},data:{type:Array,default:[]}},emits:["onSizeChange","onCurrentChange","onSelectionChange","setCellColor","onButtonClick","onSwitchChange"],setup(o,{emit:g}){const w=o,x=O({maxHeight:window.innerHeight-280,tableHeight:w.tableHeight,headerCellStyle:w.headerCellStyle,cellStyle:function(t){let d={};return g("setCellColor",t,(n={})=>{d=n}),d.padding="2px",d}}),{maxHeight:k,tableHeight:z,headerCellStyle:S,cellStyle:V}=U(x),B=t=>{g("onSizeChange",t)},H=t=>{g("onCurrentChange",t)};return(t,d)=>{const n=c("el-table-column"),$=c("el-tag"),N=c("el-button"),j=c("el-switch"),T=c("el-progress"),A=c("el-table"),D=c("el-pagination"),F=E("loading");return l(),p("div",W,[f("div",q,[v(t.$slots,"search")]),f("div",G,[L((l(),i(A,{data:o.data,border:"",stripe:"",size:"mini",height:h(z),"max-height":h(k),"row-key":"id","tree-props":{children:"children",hasChildren:"hasChildren"},"header-cell-style":h(S),"cell-style":h(V),onSelectionChange:t.onSelectionChange,style:{width:"100%"}},{default:r(()=>[(l(!0),p(y,null,R(o.column,e=>(l(),p(y,{key:e},[!e.prop&&!e.label?(l(),i(n,{key:0,fixed:e.fixed,type:"selection",width:"45"},null,8,["fixed"])):e.type=="tag"?(l(),i(n,{key:1,"show-overflow-tooltip":"",label:e.label,align:e.align!=null?e.align:"center",width:e.width},{default:r(a=>[s($,{size:e.option.size,effect:e.option.effect,type:e.option.type},{default:r(()=>[b(C(a.row[e.prop]),1)]),_:2},1032,["size","effect","type"])]),_:2},1032,["label","align","width"])):e.type=="button"?(l(),i(n,{key:2,"show-overflow-tooltip":"",label:e.label,align:e.align!=null?e.align:"center",width:e.width},{default:r(a=>[s(N,{onClick:u=>t.$emit("onButtonClick",a.row),type:"text",size:e.option.size},{default:r(()=>[b(C(a.row[e.prop]),1)]),_:2},1032,["onClick","size"])]),_:2},1032,["label","align","width"])):e.type=="switch"?(l(),i(n,{key:3,"show-overflow-tooltip":"",label:e.label,align:e.align!=null?e.align:"center",width:e.width},{default:r(a=>[s(j,{onChange:u=>t.$emit("onSwitchChange",a.row),"inline-prompt":"","active-value":e.option.activeValue,"active-color":e.option.activeColor,"active-text":e.option.activeText,"inactive-value":e.option.inactiveValue,"inactive-color":e.option.inactiveColor,"inactive-text":e.option.inactiveText,modelValue:a.row[e.prop],"onUpdate:modelValue":u=>a.row[e.prop]=u},null,8,["onChange","active-value","active-color","active-text","inactive-value","inactive-color","inactive-text","modelValue","onUpdate:modelValue"])]),_:2},1032,["label","align","width"])):e.type=="progress"?(l(),i(n,{key:4,"show-overflow-tooltip":"",label:e.label,align:e.align!=null?e.align:"center",width:e.width},{default:r(a=>[s(T,{percentage:a.row[e.prop],status:e.option.status,color:e.option.color},null,8,["percentage","status","color"])]),_:2},1032,["label","align","width"])):(l(),i(n,{key:5,"show-overflow-tooltip":"",prop:e.alias==null?e.prop:e.alias,label:e.label,align:e.align!=null?e.align:"center",width:e.width,fixed:e.fixed},null,8,["prop","label","align","width","fixed"]))],64))),128)),o.operation?(l(),i(n,{key:0,fixed:"right",label:"\u64CD\u4F5C",align:"center",width:"220"},{default:r(e=>[v(t.$slots,"column",{column:e})]),_:3})):_("",!0)]),_:3},8,["data","height","max-height","header-cell-style","cell-style","onSelectionChange"])),[[F,o.loading]])]),o.page?(l(),p("div",I,[s(D,{onSizeChange:B,onCurrentChange:H,layout:"total, sizes, prev, pager, next, jumper","current-page":o.page.current,"page-sizes":[10,20,50,100],"page-size":o.page.size,total:o.page.total},null,8,["current-page","page-size","total"])])):_("",!0)])}}};export{K as _};
