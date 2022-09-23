<template>
    <div class="collect">
        <el-card>
            <Table :data="tableData" :column="tableTitle" :operation="true" :page="page" :loading="loading"
                @onSizeChange="onSizeChange" @onCurrentChange="onCurrentChange">
                <template #search>
                 
                    <div><el-button type="primary" @click="onAddCollect">创建采集任务</el-button></div>
                    <div><el-input v-model="searchForm.docId" placeholder="请输入docId"></el-input></div>
                    <div><el-button @click="onRefresh" icon="RefreshRight" circle></el-button></div>
                    <div><el-button @click="onSearch" type="primary" icon="Search">查询</el-button></div>
                </template>
         
                <template #column="scope">
                    <el-button @click="goDetail(scope)" type="success" >查看数据详情</el-button>
                </template>
            </Table>
        </el-card>
        <el-dialog :title="taskTitle" v-model="taskVisible" width="80%" :show-close="false">
               <div>
                      
docId可在分享链接页面按F12，切换到network TAB页面,输入StateServiceHandler过滤出的接口中，存在docId参数，复制粘贴到输入框
<br>
示列链接：https://wus.pptservicescast.officeapps.live.com/<nobr style="color:red;font-size:20px;word-break:keep-all;">StateServiceHandler</nobr>.ashx?action=deltas&<nobr style="color:red;font-size:20px;">docId=rdvPragueDocId_23b6a64a-e615-45d9-9b4d-54f26e783480_r3</nobr>&cid=7e1d31dd-40b0-45b1-9176-f358dcc38d29&objectId=finalSubtitles&property=en
<br>
示列：
<br>
domain填入：wus.pptservicescast.officeapps.live.com
<br>
docId填入：rdvPragueDocId_23b6a64a-e615-45d9-9b4d-54f26e783480_r3，提交
<br>
每30s记录一次
<br>
微软的数据特性：
<br>
//  英文(en)、中文(zh-Hans)、德语(de)、法语(fr)、日语(ja)
<br>
//  全量采集：切换语言之前的没有数据，只有按照具体的方言才有对应的数据，不能全量采集有数据缺失。
<br>
//  单个方言采集：请求连接必须制定特定的方言类型，目前采用上述5种方言采集，如
<br>
//  https://inc-dc.pptservicescast.officeapps.live.com/StateServiceHandler.ashx?action=deltas&docId=rdvPragueDocId_0f742155-7c0f-4fd5-8029-1ca7b1e8ef1e_r5&objectId=finalSubtitles&property=ja
<br>
//  存储时，根据docId_方言作为key，如
<br>
//  导出时，先获取数据，再导出到文件中
                    </div>
            <el-form :model="taskForm" :rules="rules" ref="addRuleForm" label-position="left" label-width="100px">
                <el-form-item label="域名" prop="domain" :rules="[{required: true, message: '域名不能为空', trigger: 'blur'}]">
                    <el-input v-model="taskForm.domain" placeholder="请输入域名" style="width:100%" />
                </el-form-item>
                <el-form-item label="docId" prop="docId" :rules="[{required: true, message: 'docId不能为空', trigger: 'blur'}]">
                    <el-input v-model="taskForm.docId" placeholder="请输入docId" style="width:100%" />
                </el-form-item>
                <el-form-item label="remark" prop="remark" >
                    <el-input v-model="taskForm.remark" placeholder="请输入备注说明" style="width:100%" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="taskVisible = false">取消</el-button>
                    <el-button type="primary" @click="onSubmit">提交</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script>
export default {
methods: {
    // 跳转到传递参数页
    goDetail(row) {
      this.$router.push({
        path: '/recordData',
        query: {
          docId: row.column.row.docId
        }// 要传递的参数
      })
    }
  }
}
</script>

<script setup>
    import {onMounted, reactive, nextTick, ref, unref, toRefs} from 'vue'
    import {ElMessage, ElMessageBox} from 'element-plus'
    import Table from '../../components/Table.vue'
    import {collectPage, collect} from '../../api/systemApi.js'


    const addRuleForm = ref(null)
    const state = reactive({
        searchForm:{
            docId: null,
        },
        loading: false,
        page: {
            current: 1,
            size: 10,
            total: 1,
        },
        tableTitle: [
            {prop:'id',label:'ID',width:100},
            {prop:'docId',label:'会话文档ID',width:450},
            // {prop:'status',alias:'statusName',label:'状态',
            //     type:'switch',
            //     option:{
            //         activeValue:1,activeColor:'#13ce66',activeText:'启用',
            //         inactiveValue:0,inactiveColor:'#ff4949',inactiveText:'禁用'
            //     }
            // },
            // {prop:'creater',label:'创建人'},
            // {prop:'updateTime',label:'更新时间'},
            {prop:'createTime',label:'创建时间'},
            {prop:'remark',label:'备注'}
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
        collectPage(Object.assign(state.page,state.searchForm)).then(res=>{
            state.loading = false;
            state.tableData = res.result.records;
            state.page.current = res.result.current;
            state.page.size = res.result.size;
            state.page.total = res.result.total;
        })
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

    // 添加
    const onAddCollect = () =>{
        state.taskTitle = "创建任务";
        state.taskForm = {
            id: '',
            domain: '',
            docId: '',
            reamrk: ''
        }

        state.taskVisible = true;
    }

    // 编辑
    // const goDetail = (val) =>{
    //     let _this=this;
    //     //页面加列表项锚点
    //     // location.href = `#${id}`;
        
	// 	_this.$router.push({
	// 		name: 'RecordData',
	// 		params: {
	// 			docId: val.column.row.docId
	// 		}
    //     });
        
        // state.taskTitle = "修改任务";
        // state.taskForm = {
        //     id: val.column.row.id,
        //     code: val.column.row.code,
        //     name: val.column.row.name,
        //     expression: val.column.row.expression,
        //     remarks: val.column.row.remarks
        // }
        // state.taskVisible = true;
    // }



    const onSubmit = async () =>{
        const form = unref(addRuleForm);
        if(!form) return;
        await form.validate();
            collect(state.taskForm).then(res => {
                if (res) {
                    ElMessage.success('添加成功!');
                     state.taskVisible = false;
                    onCollectPage();
                }
            });
       

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