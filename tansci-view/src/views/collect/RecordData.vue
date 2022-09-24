<template>
    <div class="record_data">
        <el-card>
              
              <p>测试this.searchForm.docId=========={{ this.searchForm.docId }}</p>
            <Table :data="tableData" :column="tableTitle" :operation="false" :page="page" :loading="loading"
                @onSizeChange="onSizeChange" @onCurrentChange="onCurrentChange">
                <template #search>
                    <div><el-button type="primary" @click="onExportTxt">导出</el-button></div>
                    <div><el-input v-model="this.searchForm.docId" type="hidden" ></el-input></div>
                       <div> <el-select v-model="this.searchForm.property" placeholder="请选择语言类型" style="width:70%" >
                            <el-option label="英语" value="en" ></el-option>
                            <el-option label="中文" value="zh-Hans"></el-option>
                             <el-option label="德语" value="de"></el-option>
                              <el-option label="法语" value="fr"></el-option>
                               <el-option label="日语" value="ja"></el-option>
                        </el-select></div>
                     <!-- <div><el-input v-model="searchForm.subtitle" type="text" placeholder="请输入内容" ></el-input></div> -->
                    <!-- <div><el-input v-model="searchForm.timestamp" type="text" placeholder="请输入时间" ></el-input></div> -->
                    <div><el-button @click="onRefresh" icon="RefreshRight" circle></el-button></div>
                    <div><el-button @click="onSearch" type="primary" icon="Search">查询</el-button></div>
                </template>
                <!-- <template #column="scope">
                    <el-button @click="onEdit(scope)" type="text" style="color:var(--edit)">详情/el-button>
                    <el-button @click="onDelete(scope)" type="text" style="color:var(--delete)">删除</el-button>
                </template> -->
            </Table>
        </el-card>
      
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
    import {collectDataPage,exportTxt} from '../../api/systemApi.js'
    import {timeFormate2} from '../../utils/utils.js'
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
            size: 10,
            total: 1,
        },
        tableTitle: [
            {prop:'id',label:'ID'},
            {prop:'docId',label:'文档ID'},
            // {prop:'status',alias:'statusName',label:'状态',
            //     type:'switch',
            //     option:{
            //         activeValue:1,activeColor:'#13ce66',activeText:'启用',
            //         inactiveValue:0,inactiveColor:'#ff4949',inactiveText:'禁用'
            //     }
            // },
            // {prop:'creater',label:'创建人'},
            {prop:'subtitle',label:'内容'},
            {prop:'timestamp',label:'创建时间'},
        ],
        tableData:[],
        taskVisible: false,
        taskTitle: '创建采集任务',
        taskForm:{
            domain: '',
            docId: '',
            reamrk: ''
        }
    })

    const {
        searchForm,loading,page,tableHeight,tableTitle,tableData,
        taskVisible,taskTitle,taskForm
    } = toRefs(state)

    onMounted(() => {
        onCollectPage();
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
</script>
<style lang="scss" scoped>
</style>