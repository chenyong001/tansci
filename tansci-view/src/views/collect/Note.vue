<template>
    <div class="note">
        <el-card>
            <Table :data="tableData" :column="tableTitle" :operation="true" :page="page" :loading="loading"
                @onSizeChange="onSizeChange" @onCurrentChange="onCurrentChange">
                <template #search>
                 
                    <div><el-button type="primary" @click="onAddNote">实时语音识别(篇章式)</el-button></div>
                      <div><el-button type="primary" @click="onAddNoteTranslate">实时语音翻译(篇章式)</el-button></div>
                        <div><el-button type="primary" @click="onAddNoteUpload">上传音频语音识别</el-button></div>
                          <div><el-button type="primary" @click="onAddNoteUploadTranslate">上传音频语音翻译</el-button></div>
                       <div><el-button type="primary" @click="onAddNoteSubtitle">实时语音识别(字幕式)</el-button></div>
                      <div><el-button type="primary" @click="onAddNoteTranslateSubtitle">实时语音翻译(字幕式)</el-button></div>
                      <div><el-button type="primary" @click="onTranslate">文本翻译</el-button></div>
                       <!-- <div><el-button type="primary" @click="onChatGpt">chatGpt</el-button></div> -->
                           <!-- <div><el-button type="primary" @click="onAddNoteMp3">MP3</el-button></div> -->
                    <!-- <div><el-button type="primary" @click="onAddNote4and1">4合1</el-button></div> -->
                    <div><el-input v-model="searchForm.docId" placeholder="请输入docId"></el-input></div>
                    <div><el-button @click="onRefresh" icon="RefreshRight" circle></el-button></div>
                    <div><el-button @click="onSearch" type="primary" icon="Search">查询</el-button></div>
                </template>
         
                <template #column="scope"    >
                    <el-button @click="onEdit(scope)" type="success">编辑</el-button>
                    <el-button @click="menuClick(scope)" type="success" >详情</el-button>
                    <el-button @click="onDelete(scope)" type="warning" >删除</el-button>
                    <el-button @click="download(scope)" type="primary" >下载</el-button>
                    <el-button @click="onCut(scope)" type="warning" >切割</el-button>
                    <el-button @click="onMerge(scope)" type="warning" >合并</el-button>

                </template>
            </Table>
        </el-card>
        <el-dialog :title="taskTitle" v-model="taskVisible" width="80%" :show-close="false">
            <el-form :model="taskForm" :rules="rules" ref="addRuleForm" label-position="left" label-width="100px">
                <el-form-item label="备注" prop="备注" >
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

          <el-dialog :title="cutTitle" v-model="cutVisible" width="80%" :show-close="false">
            <el-form :model="taskForm" :rules="rules" ref="addRuleForm" label-position="left" label-width="100px">
                <span class="demonstration">切割比例</span>
                <el-slider v-model="taskForm.cutValue" show-input="true"></el-slider>
  
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="cutVisible = false">取消</el-button>
                    <el-button type="primary" @click="onCutSubmit">提交</el-button>
                </span>
            </template>
        </el-dialog>

         <el-dialog :title="mergeTitle" v-model="mergeVisible" width="80%" :show-close="false">
            <el-form :model="taskForm" :rules="rules" ref="addRuleForm" label-position="left" label-width="100px">
                              <el-form-item label="合并会话" prop="合并会话" >
                    <el-input v-model="taskForm.docId2" placeholder="请输入待合并的会话文档ID" style="width:100%" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="mergeVisible = false">取消</el-button>
                    <el-button type="primary" @click="onMergeSubmit">提交</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
// <script>
// export default {
// methods: {
//     // 跳转到传递参数页
//     goDetail(row) {111
//       this.$router.push({
//         path: '/recordData',
//         query: {
//           docId: row.column.row.docId
//         }// 要传递的参数
//       })
//     }
//   }
// }
// </script>

<script setup>
    import {onMounted, reactive, nextTick, ref, unref, toRefs} from 'vue'
    import {ElMessage, ElMessageBox} from 'element-plus'
    import Table from '../../components/Table.vue'
    import {collectPage, collect,deleteNote,exportWAV,createNote,cuttingRecord,mergeRecord} from '../../api/systemApi.js'
import { useRouter } from 'vue-router'
import { timeFormate2 } from "../../utils/utils.js";

// 切换页面
    const router = useRouter()

    const onAddNote = () => {
        window.open('/index-azure.html' )
        // window.open('/index.html' )

    //   router.replace({
    //     path: '/note/noteCreate'
    //   })
    }
       const onAddNoteTranslate = () => {
        window.open('/index-azure-translate.html' )
    }
           const onAddNoteUpload = () => {
        window.open('/index-azure-upload.html' )
    }
           const onAddNoteUploadTranslate = () => {
        window.open('/index-azure-upload-translate.html' )
    }
               const onAddNote4and1 = () => {
        window.open('/index-azure-4and1.html' )
    }
                   const onAddNoteMp3 = () => {
        window.open('/mp3test.html' )
    }
       const onAddNoteSubtitle = () => {
        window.open('/index-azure-subtitle.html' )
    }
       const onAddNoteTranslateSubtitle = () => {
        window.open('/index-azure-translate-subtitle.html' )
    }
           const onTranslate = () => {
        window.open('/translate.html' )
    }
       const onChatGpt = () => {
        window.open('/chatGpt.html' )
    }
    

 const menuClick = (val) => {
      router.replace({
        path: '/note/noteDetail',
        query: {
          docId:val.column.row.docId
         
        }
      })
    }


    // return {
    //   menuClick
    // }

    const addRuleForm = ref(null)
    const state = reactive({
        searchForm:{
            docId: null,
            type:2
        },
        loading: false,
        page: {
            current: 1,
            size: 10,
            total: 1,
        },
        tableTitle: [
            {prop:'id',label:'ID',width:100},
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
            {prop:'durationStr',label:'总时长'},
            {prop:'charactersCount',label:'总字符量'},
            {prop:'userName',label:'创建者'},
            {prop:'remark',label:'备注'}
        ],
        tableData:[],
        taskVisible: false,
        taskTitle: '创建采集任务',
                cutVisible: false,
        cutTitle: '切割',
        mergeVisible: false,
        mergeTitle: '合并',
        taskForm:{
            domain: '',
            docId: '',
            reamrk: '',
            cutValue: '',
            docId2:''
        }
    })

    const {
        searchForm,loading,page,tableHeight,tableTitle,tableData,
        taskVisible,taskTitle,taskForm,cutVisible,cutTitle,mergeVisible,mergeTitle
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
    // const onAddNote = () =>{
// window.open('index.html' )
        // state.taskTitle = "创建任务";
        // state.taskForm = {
        //     id: '',
        //     domain: '',
        //     docId: '',
        //     reamrk: ''
        // }

    //     state.taskVisible = true;
    // }

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

    // 编辑
    const onEdit = (val) =>{
        state.taskTitle = "编辑";
        state.taskForm = {
            id: val.column.row.id,
            docId: val.column.row.docId,
            remark: val.column.row.remark
        }
        state.taskVisible = true;
    }
        // 切割
    const onCut = (val) =>{
        state.cutTitle = "切割";
        state.taskForm = {
            id: val.column.row.id,
            docId: val.column.row.docId,
            cutValue: 100
        }
        state.cutVisible = true;
    }
            // 合并
    const onMerge = (val) =>{
        state.cutTitle = "合并";
        state.taskForm = {
            id: val.column.row.id,
            docId: val.column.row.docId,
        }
        state.mergeVisible = true;
    }

    const onSubmit = async () =>{
        const form = unref(addRuleForm);
        if(!form) return;
        await form.validate();
          if (state.taskForm.id == null || state.taskForm.id == '') {
            collect(state.taskForm).then(res => {
                if (res) {
                    ElMessage.success('添加成功!');
                     state.taskVisible = false;
                }
                onCollectPage();
            });
        } else {
            // 修改
            createNote(state.taskForm).then(res => {
                if (res) {
                    ElMessage.success('修改成功!');
                }
                  onCollectPage();
            });
           
        }
        state.taskVisible = false;
        
       

    }
    
    const onCutSubmit = async () =>{
        const form = unref(addRuleForm);
        if(!form) return;
        await form.validate();
            // 修改
            cuttingRecord(state.taskForm).then(res => {
                if (res) {
                    ElMessage.success('切割成功!');
                     onCollectPage();
                }
                 
            });
           
        state.cutVisible = false;
        
       

    }

        const onMergeSubmit = async () =>{
        const form = unref(addRuleForm);
        if(!form) return;
        await form.validate();
            // 修改
            mergeRecord(state.taskForm).then(res => {
                if (res) {
                    ElMessage.success('切割成功!');
                     onCollectPage();
                }
                 
            });
           
        state.mergeVisible = false;
        
       

    }

    // 删除
    const onDelete = (val) =>{
        ElMessageBox.confirm('此操作将永久删除, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            let param = {
                docId: val.column.row.docId
            }
            deleteNote(param).then(res=>{
                if(res){
                    ElMessage.success('删除成功!');
                    onCollectPage();
                }
            })
        }).catch(e=>{
            console.log(e)
        })
    }
    
    const download = async (val) =>{
        // const form = unref(addRuleForm);
        // if(!form) return;
        // await form.validate();
         let param = {
                docId: val.column.row.docId
            }
            exportWAV(param).then(res => {
                if (res.data.size==0) {
                return;
                }
                let url = window.URL.createObjectURL(new Blob([res.data]));
                let link = document.createElement("a");
                link.style.display = "none";
                link.href = url;

                let filePath = 'audio_recording_' +val.column.row.docId+"_"+timeFormate2(new Date())+".mp3";
                var fileName = filePath.substring(filePath.indexOf('/')+1,filePath.length);

                link.setAttribute("download", fileName);
                document.body.appendChild(link);
                link.click();
                // 释放URL对象所占资源
                window.URL.revokeObjectURL(url);
                // 用完即删
                document.body.removeChild(link);
                // 刷新页面
                 onCollectPage();
               
            });
       

    }
    

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