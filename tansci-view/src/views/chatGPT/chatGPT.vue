<template>
  <div class="note">
    <el-card>
      <Table
        :data="tableData"
        :column="tableTitle"
        :operation="true"
        :page="page"
        :loading="loading"
        @onSizeChange="onSizeChange"
        @onCurrentChange="onCurrentChange"
      >
        <template #search>

    
           <div>
            <el-button type="primary" @click="onChatGpt">ChatGPT文字版</el-button>
          </div>
                    <div>
            <el-button type="primary" @click="onChatGptOpenAI">ChatGPT语音版</el-button>
          </div>
          <div><el-button type="primary" @click="onExportChatGPTTxt">导出</el-button></div>

          <div>
            <el-button @click="onRefresh" icon="RefreshRight" circle></el-button>
          </div>
          <div>
            <el-button @click="onSearch" type="primary" icon="Search">查询</el-button>
          </div>
        </template>
             <template #column="scope"    >
                    <!-- <el-button @click="onEdit(scope)" type="success">编辑</el-button>
                    <el-button @click="menuClick(scope)" type="success" >详情</el-button> -->
                    <el-button @click="onDelete(scope)" type="warning" >删除</el-button>
                </template>
      
      </Table>
    </el-card>
   
  </div>
</template>


<script setup>
import { onMounted, reactive, nextTick, ref, unref, toRefs } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import Table from "../../components/Table.vue";
import { chatGPTPage,exportChatGPTTxt ,deleteChatGPT} from "../../api/systemApi.js";
import { useRouter } from "vue-router";
import { timeFormate2 } from "../../utils/utils.js";

// 切换页面
const router = useRouter();

const onChatGpt = () => {
  window.open("/index-chatgpt-openai.html");
};
const onChatGptOpenAI = () => {
  window.open("/index-azure-chatgpt-openai.html");
};

const menuClick = val => {
  router.replace({
    path: "/note/noteDetail",
    query: {
      docId: val.column.row.docId
    }
  });
};

// 初始化数据
const onExportChatGPTTxt = () => {
  state.loading = true;
  exportChatGPTTxt(Object.assign(state.searchForm)).then(res => {
    if (!res.data) {
      return;
    }
    let url = window.URL.createObjectURL(new Blob([res.data]));
    let link = document.createElement("a");
    link.style.display = "none";
    link.href = url;
    let fileName = "data_" + timeFormate2(new Date()) + ".txt";
    link.setAttribute("download", fileName);
    document.body.appendChild(link);
    link.click();
    // 释放URL对象所占资源
    window.URL.revokeObjectURL(url);
    // 用完即删
    document.body.removeChild(link);
    onPage();
  });
};



const addRuleForm = ref(null);
const state = reactive({
  searchForm: {
    docId: null,
    type: 2
  },
  loading: false,
  page: {
    current: 1,
    size: 10,
    total: 1
  },
  tableTitle: [
    { prop: "id", label: "ID", width: 100 },
    { prop: "createTime", label: "创建时间", width: 180 },
    { prop: "userName", label: "创建者", width: 120 },
     { prop: "speechText", label: "识别原文", width: 180 },
    { prop: "prompt", label: "提问", width: 180 },
    { prop: "content", label: "内容" }
  ],
  tableData: [],
  taskVisible: false,
  taskTitle: "创建采集任务",
  cutVisible: false,
  cutTitle: "切割",
  mergeVisible: false,
  mergeTitle: "合并",
  taskForm: {
    domain: "",
    docId: "",
    reamrk: "",
    cutValue: "",
    docId2: ""
  }
});

const {
  searchForm,
  loading,
  page,
  tableHeight,
  tableTitle,
  tableData,
  taskVisible,
  taskTitle,
  taskForm,
  cutVisible,
  cutTitle,
  mergeVisible,
  mergeTitle
} = toRefs(state);

onMounted(() => {
  onPage();
});

// 初始化数据
const onPage = () => {
  state.loading = true;
  chatGPTPage(Object.assign(state.page, state.searchForm)).then(res => {
    state.loading = false;
    state.tableData = res.result.records;
    state.page.current = res.result.current;
    state.page.size = res.result.size;
    state.page.total = res.result.total;
  });
};
const onSizeChange = e => {
  state.page.size = e;
  onPage();
};
const onCurrentChange = e => {
  state.page.current = e;
  onPage();
};
const onRefresh = () => {
  state.searchForm = {
    name: null
  };
  onPage();
};
const onSearch = () => {
  onPage();
};

// 编辑
const onEdit = val => {
  state.taskTitle = "编辑";
  state.taskForm = {
    id: val.column.row.id,
    docId: val.column.row.docId,
    remark: val.column.row.remark
  };
  state.taskVisible = true;
};

const onSubmit = async () => {
  const form = unref(addRuleForm);
  if (!form) return;
  await form.validate();
  if (state.taskForm.id == null || state.taskForm.id == "") {
    collect(state.taskForm).then(res => {
      if (res) {
        ElMessage.success("添加成功!");
        state.taskVisible = false;
      }
      onPage();
    });
  } else {
    // 修改
    createNote(state.taskForm).then(res => {
      if (res) {
        ElMessage.success("修改成功!");
      }
      onPage();
    });
  }
  state.taskVisible = false;
};

// 删除
const onDelete = val => {
  ElMessageBox.confirm("此操作将永久删除, 是否继续?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  })
    .then(() => {
      let param = {
        id: val.column.row.id
      };
      deleteChatGPT(param).then(res => {
        if (res) {
          ElMessage.success("删除成功!");
          onPage();
        }
      });
    })
    .catch(e => {
      console.log(e);
    });
};

</script>
<style lang="scss" scoped>
</style>