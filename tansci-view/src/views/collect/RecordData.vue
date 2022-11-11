<template>
    <div class="record_data" style="display: flex;
  justify-content: space-between;">
  <div style="width:60%;">
        <el-tabs v-model="activeName" model-value="first" type="card" >
    <el-tab-pane label="数据详情" name="first">
        <el-card>
              
              <!-- <p>PPT直播测试searchForm.docId=========={{ searchForm.docId }}</p> -->
            <Table :data="tableData" :column="tableTitle" :operation="false" :page="page" :loading="loading"
                @onSizeChange="onSizeChange" @onCurrentChange="onCurrentChange">
                <template #search>
                    <div><el-button type="primary" @click="onExportTxt">导出</el-button></div>
                    <div><el-input v-model="searchForm.docId" type="hidden" ></el-input></div>
                       <div> <el-select v-model="searchForm.property" placeholder="请选择语言类型" style="width:70%" >
                            <el-option label="英语" value="en" ></el-option>
                            <el-option label="中文" value="zh-Hans"></el-option>
                             <el-option label="德语" value="de"></el-option>
                              <el-option label="法语" value="fr"></el-option>
                               <el-option label="日语" value="ja"></el-option>
                        </el-select></div>
                     <!-- <div><el-input v-model="searchForm.subtitle" type="text" placeholder="请输入内容" ></el-input></div> -->
                    <!-- <div><el-input v-model="searchForm.timestamp" type="text" placeholder="请输入时间" ></el-input></div> -->
                    <!-- <div><el-button @click="onRefresh" icon="RenfreshRight" circle></el-button></div> -->
                    <div><el-button @click="onSearch" type="primary" icon="Search">查询</el-button></div>
                </template>
                <!-- <template #column="scope">
                    <el-button @click="onEdit(scope)" type="text" style="color:var(--edit)">详情/el-button>
                    <el-button @click="onDelete(scope)" type="text" style="color:var(--delete)">删除</el-button>
                </template> -->
            </Table>
        </el-card>
        
    </el-tab-pane>
    <el-tab-pane label="词频列表" name="second">

  <Table
        :data="fqcTableData"
        :column="fqcTableTitle"
        :operation="false"
        :page="page"
        :loading="loading"
        @onSizeChange="onSizeChange"
        @onCurrentChange="onCurrentChange"
      >
   
      </Table>
    </el-tab-pane>
  </el-tabs>
       </div>
    <div style="width:40%;">
    <el-card >
      <div id="myPie" style="height:500px"></div>
      <div id="tlrealtimewordcloud"  style="height:500px" ></div>
    </el-card>
    </div>
    </div>
</template>
// <script>
// export default {
    
//     data () {
//       return {
//         // 保存传递过来的index
//         searchForm:{
//           docId: '',
//           property:''
//         }
        
        
//       }
//     }, 
// watch: {
//     '$route': 'gettingData'
//   },
//   created() {
//     this.gettingData()
    
//   },
//   methods: {
//     // 获取数据
//     gettingData() {
//       var docId2 = this.$route.query.docId;
//       this.searchForm.docId=docId2;
//       this.searchForm.property="en";
//     }
//   }
// }
// </script>
<script setup>

    import {onMounted, reactive, nextTick, ref, unref, toRefs} from 'vue'
    import {ElMessage, ElMessageBox} from 'element-plus'
    import Table from '../../components/Table.vue'
    import {collectDataPage,exportTxt,getMyData} from '../../api/systemApi.js'
    import {timeFormate2} from '../../utils/utils.js'
    import * as echarts from "echarts";
import "echarts-wordcloud";


import { useRoute } from "vue-router";
const route = useRoute();
    console.log(route.query.docId);  //通过query 传递过来的参数


    const addRuleForm = ref(null)
    const state = reactive({
        searchForm:{
            docId: route.query.docId,
            // subtitle: '',
            property: 'en',
            //  timestamp: ''
        },
        loading: false,
        page: {
            current: 1,
            size: 500,
            total: 1,
        },
        tableTitle: [
            {prop:'id',label:'ID',width:100},
            // {prop:'docId',label:'文档ID'},
            // {prop:'status',alias:'statusName',label:'状态',
            //     type:'switch',
            //     option:{
            //         activeValue:1,activeColor:'#13ce66',activeText:'启用',
            //         inactiveValue:0,inactiveColor:'#ff4949',inactiveText:'禁用'
            //     }
            // },
            // {prop:'creater',label:'创建人'},
            {prop:'subtitle',label:'内容'},
            {prop:'timestamp',label:'创建时间',width:200},
        ],
        tableData:[],
        taskVisible: false,
        taskTitle: '创建采集任务',
        taskForm:{
            domain: '',
            docId: '',
            reamrk: ''
        },
        activeName: 'first',
          fqcTableTitle: [
          { prop: "name", label: "单词"},
          { prop: "value", label: "次数" }
        ],
        fqcTableData: [],
    })
let myResult;
    const {
        searchForm,loading,page,tableHeight,tableTitle,tableData,
        taskVisible,taskTitle,taskForm,
        fqcTableTitle,
        fqcTableData
    } = toRefs(state)

    onMounted(() => {
        onCollectPage();
          onPie();
  onLtrealtimewordcloud();
    })

    // 初始化数据
    const onCollectPage = () =>{
        state.loading = true;
        // state.searchForm.docId=this.$route.params.id;
        // state.searchForm.docId=this.docId;
        collectDataPage(Object.assign(state.page,state.searchForm)).then(res=>{
            state.loading = false;
            state.tableData = res.result.records;
            state.page.current = res.result.current;
            state.page.size = res.result.size;
            state.page.total = res.result.total;
        })
    }
    
// 初始化数据
const onFqcPage = () => {
  state.loading = true;
    state.loading = false;
    state.fqcTableData = myResult;
};
        // 初始化数据
    const onExportTxt = () =>{
        state.loading = true;
        // state.searchForm.docId=this.$route.params.docId;
        // state.searchForm.docId=this.docId;
        exportTxt(Object.assign(state.searchForm)).then(res=>{
            if (!res.data) {
        return
      }
      let url = window.URL.createObjectURL(new Blob([res.data]))
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      let fileName='data_'+timeFormate2(new Date())+"_"+state.searchForm.property+".txt";
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      // 释放URL对象所占资源
      window.URL.revokeObjectURL(url)
      // 用完即删
      document.body.removeChild(link)
      onCollectPage();
            
            });
    }
    const onSizeChange = (e) =>{
        state.page.size = e;
        onCollectPage();
    }
    const onCurrentChange = (e) =>{
        state.page.current = e;
        onCollectPage();
    }
    const onRefresh = () =>{
        state.searchForm = {
            name: null,
        }
        onCollectPage();
    }
    const onSearch = () =>{
        onCollectPage();
                  onPie();
  onLtrealtimewordcloud();
    }
    
    // const exportTxt = () =>{
    //     onCollectPage();
    // }
    // 添加
    // const onAddCollect = () =>{
    //     state.taskTitle = "创建任务";
    //     state.taskForm = {
    //         id: '',
    //         domain: '',
    //         docId: '',
    //         reamrk: ''
    //     }

    //     state.taskVisible = true;
    // }
        const goBack = () => {
        router.go(-1)
    }


    // // 编辑
    // const onEdit = (val) =>{
    //     state.taskTitle = "修改任务";
    //     state.taskForm = {
    //         id: val.column.row.id,
    //         code: val.column.row.code,
    //         name: val.column.row.name,
    //         expression: val.column.row.expression,
    //         remarks: val.column.row.remarks
    //     }
    //     state.taskVisible = true;
    // }

    const onSubmit = async () =>{
        const form = unref(addRuleForm);
        if(!form) return;
        await form.validate();
        if (state.taskForm.id == null || state.taskForm.id == '') {
            // 添加
            collect(state.taskForm).then(res => {
                if (res) {
                    ElMessage.success('添加成功!');
                }
            });
        } else {
            // 修改
            updateTask(state.taskForm).then(res => {
                if (res) {
                    ElMessage.success('修改成功!');
                }
            });
        }
        state.taskVisible = false;
        onCollectPage();
    }

    // // 删除
    // const onDelete = (val) =>{
    //     ElMessageBox.confirm('此操作将永久删除, 是否继续?', '提示', {
    //         confirmButtonText: '确定',
    //         cancelButtonText: '取消',
    //         type: 'warning'
    //     }).then(() => {
    //         let param = {
    //             id: val.column.row.id
    //         }
    //         delTask(param).then(res=>{
    //             if(res){
    //                 ElMessage.success('删除成功!');
    //                 onCollectPage();
    //             }
    //         })
    //     }).catch(e=>{
    //         console.log(e)
    //     })
    // }

    // const onSwitchChange = (row) =>{
    //     updateTask({id:row.id, status: row.status}).then(res=>{
    //         if(res){
    //             ElMessage.success("操作成功！");
    //             onCollectPage();
    //         }
    //     })
    // }
    
const   onPie = async  () => {
  let myPie = echarts.init(document.getElementById("myPie"));
  const { result } = await getMyData(Object.assign(state.searchForm))
      console.log(result)
       myResult=result;
  onFqcPage();

  myPie.setOption({
    title: {},
    legend: {},
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    // color: ["#94716B", "#6C5B7B", "#355C7D"],
    series: [
      {
        name: "",
        type: "pie",
        radius: "90%",
        avoidLabelOverlap: false,
        roseType: "radius",
        center: ["50%", "50%"],
        itemStyle: {
          borderRadius: 10,
          borderColor: "#fff",
          borderWidth: 2
        },
        label: {
          show: true,
          position: "inner"
        },
        labelLine: {
          show: false
        },
        data: result
        // [
        //   { value: 335, name: "指标一" },
        //   { value: 310, name: "指标二" },
        //   { value: 274, name: "指标三" }
        // ].sort(function(a, b) {
        //   return a.value - b.value;
        // })
      }
    ]
  });
  window.onresize = function() {
    myPie.resize();
  };
};

const onLtrealtimewordcloud = async () => {
  let tlrealtimewordcloud = echarts.init(
    document.getElementById("tlrealtimewordcloud")
  );

      const { result } = await getMyData(Object.assign(state.searchForm))
      console.log(result)
  tlrealtimewordcloud.setOption({
    // title: {},
    // legend: {},
    // tooltip: {
    // 	trigger: 'item',
    //     formatter: '{a} <br/>{b} : {c} ({d}%)'
    // },
    // color: ['#94716B', '#6C5B7B', '#355C7D'],
    series: [
      {
        type: "wordCloud",
        // shape这个属性虽然可配置，但是在词的数量不太多的时候，效果不明显，它会趋向于画一个椭圆
        shape: "circle",
        // 这个功能还没用过
        keepAspect: false,
        // 这个是可以自定义背景图片的，词云会按照图片的形状排布，所以有形状限制的时候，最好用背景图来实现，而且，这个背景图一定要放base64的，不然词云画不出来
        // maskImage: maskImage,
        // 下面就是位置的配置
        left: "center",
        top: "center",
        width: "70%",
        height: "80%",
        right: null,
        bottom: null,
        // 词的大小，最小12px，最大60px，可以在这个范围调整词的大小
        sizeRange: [12, 60],
        // 每个词旋转的角度范围
        rotationRange: [-90, 90],
        rotationStep: 45,
        // 词间距，数值越小，间距越小，这里间距太小的话，会出现大词把小词套住的情况，比如一个大的口字，中间会有比较大的空隙，这时候他会把一些很小的字放在口字里面，这样的话，鼠标就无法选中里面的那个小字
        gridSize: 8,
        // 允许词太大的时候，超出画布的范围
        drawOutOfBound: false,
        // 布局的时候是否有动画
        layoutAnimation: true,
        // 这是全局的文字样式，相对应的还可以对每个词设置字体样式
        textStyle: {
          fontFamily: "sans-serif",
          fontWeight: "bold",
          // 颜色可以用一个函数来返回字符串
          color: function() {
            // Random color
            return (
              "rgb(" +
              [
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160)
              ].join(",") +
              ")"
            );
          }
        },
        emphasis: {
          focus: "self",
          textStyle: {
            textShadowBlur: 10,
            textShadowColor: "#333"
          }
        },
        data: result
      }
    ]
  });
  window.onresize = function() {
    myPie.resize();
  };
};
</script>
<style lang="scss" scoped>
</style>