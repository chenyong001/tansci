<template>
    <div class="collect">
        <el-card>
            <p>记录</p>
docId可在分享链接页面按F12，切换到network TAB页面,输入StateServiceHandler过滤出的接口中，存在docId参数，复制粘贴到输入框
<br>
示列链接：https://wus.pptservicescast.officeapps.live.com/<nobr style="color:red;font-size:20px;word-break:keep-all;">StateServiceHandler</nobr>.ashx?action=deltas&<nobr style="color:red;font-size:20px;">docId=rdvPragueDocId_23b6a64a-e615-45d9-9b4d-54f26e783480_r3</nobr>&cid=7e1d31dd-40b0-45b1-9176-f358dcc38d29&objectId=finalSubtitles&property=en
<br>
示列：docId填入，rdvPragueDocId_23b6a64a-e615-45d9-9b4d-54f26e783480_r3，提交
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


       <el-form :model="taskForm" :rules="rules" ref="addRuleForm" label-position="left" label-width="100px">
                <el-form-item label="域名" prop="domain" :rules="[{required: true, message: '域名不能为空', trigger: 'blur'}]">
                    <el-input v-model="taskForm.domain" placeholder="请输入域名" style="width:100%" />
                </el-form-item>
                <el-form-item label="docId" prop="docId" :rules="[{required: true, message: 'docId不能为空', trigger: 'blur'}]">
                    <el-input v-model="taskForm.docId" placeholder="请输入docId" style="width:100%" />
                </el-form-item>
                                <span class="dialog-footer">
                    <!-- <el-button @click="taskVisible = false">取消</el-button> -->
                    <el-button type="primary" @click="onSubmit">导出</el-button>
                </span>
            </el-form>
        
        </el-card>
     
    </div>
</template>
<script setup>
    import {onMounted, reactive, nextTick, ref, unref, toRefs} from 'vue'
    import {ElMessage, ElMessageBox} from 'element-plus'
    // import Table from '../../components/Table.vue'
    import {collect} from '../../api/systemApi.js'
    import {timeFormate2} from '../../utils/utils.js'

    const addRuleForm = ref(null)
    const state = reactive({
      
        taskForm:{
            domain: '',
            docId: ''   
        }
    })

    const {
       taskForm
    } = toRefs(state)

    onMounted(() => {
        
    })

 

    const onSubmit = async () =>{
        const form = unref(addRuleForm);
        if(!form) return;
        await form.validate();
        // if (state.taskForm.id == null || state.taskForm.id == '') {
            // 添加
            collect(state.taskForm).then(res => {
                 if (!res.data) {
        return
      }
      let url = window.URL.createObjectURL(new Blob([res.data]))
      let link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      let fileName='data_'+timeFormate2(new Date())+".zip";
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
      // 释放URL对象所占资源
      window.URL.revokeObjectURL(url)
      // 用完即删
      document.body.removeChild(link)
            
            });
      
    }

  
</script>
<style lang="scss" scoped>
</style>