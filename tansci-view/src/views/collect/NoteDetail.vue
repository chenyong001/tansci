<template>
  <div class="record_data" style="display: flex;
  justify-content: space-between;">
    <!-- <v-chart class="chart" :option="option2" /> -->
    <div style="width:60%;">
      <el-tabs v-model="activeName" model-value="first" type="card">
        <el-tab-pane label="数据详情" name="first">
          <!-- border: 1px solid red; -->
          <el-card>
            <!-- <p>noteDetail测试searchForm.docId=========={{ searchForm.docId }}</p> -->
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
                  <el-button type="primary" @click="onExportSrt">导出srt</el-button>
                </div>
                <div>
                  <el-button type="primary" @click="onExportTxt">导出txt</el-button>
                </div>
                <div>
                  <el-button @click="onSearch" type="primary" icon="Search">刷新</el-button>
                </div>
                <el-select v-model="value1" multiple clearable placeholder="请选择排除的标记">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </template>
              <template #column="scope" style="width:100">
                <el-button @click="onEdit(scope)" type="success">编辑</el-button>
                <el-button @click="onMark(scope)" type="success">标记</el-button>
              </template>
            </Table>
          </el-card>
          <el-dialog :title="taskTitle" v-model="taskVisible" width="80%" :show-close="false">
            <el-form
              :model="taskForm"
              :rules="rules"
              ref="addRuleForm"
              label-position="left"
              label-width="100px"
            >
              <el-form-item label="内容" prop="内容">
                <el-input
                  type="textarea"
                  rows="10"
                  v-model="taskForm.subtitle"
                  placeholder="请输入内容"
                  style="width:100%"
                />
              </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="taskVisible = false">取消</el-button>
                <el-button type="primary" @click="onSubmit">提交</el-button>
              </span>
            </template>
          </el-dialog>

          <el-dialog :title="markTitle" v-model="markVisible" width="80%" :show-close="false">
            <el-form
              :model="taskForm"
              :rules="rules"
              ref="addRuleForm"
              label-position="left"
              label-width="100px"
            >
              <el-form-item label="标记" prop="标记">
                <el-input v-model="taskForm.mark" placeholder="请输入标记内容" style="width:100%" />
              </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="markVisible = false">取消</el-button>
                <el-button type="primary" @click="onSubmit">提交</el-button>
              </span>
            </template>
          </el-dialog>
        </el-tab-pane>
        <el-tab-pane label="词频列表" name="second">
          <Table
            :data="fqcTableData"
            :column="fqcTableTitle"
            :operation="false"
            :page="fqcPage"
            :loading="loading"
            @onSizeChange="onSizeChange"
            @onCurrentChange="onCurrentChange"
          ></Table>
        </el-tab-pane>

        <el-tab-pane label="停用词" name="third">
          <el-card>
            <Table
              :data="stopTableData"
              :column="stopTableTitle"
              :operation="true"
              :page="stopPage"
              :loading="stopLoading"
              @onSizeChange="onSizeChangeStop"
              @onCurrentChange="onCurrentChangeStop"
            >
              <template #search>
                <div>
                  <el-button type="primary" @click="onStopAdd">新增</el-button>
                </div>
              </template>
              <template #column="scope" style="width:100">
                <el-button @click="onStopEdit(scope)" type="success">编辑</el-button>
                <el-button @click="onStopDelete(scope)" type="warning">删除</el-button>
              </template>
            </Table>
          </el-card>
          <el-dialog :title="stopTitle" v-model="stopVisible" width="80%" :show-close="false">
            <el-form
              :model="stopForm"
              :rules="rules"
              ref="addRuleForm"
              label-position="left"
              label-width="100px"
            >
              <el-form-item label="内容" prop="内容">
                <el-input v-model="stopForm.value" placeholder="请输入内容" style="width:100%" />
              </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="stopVisible = false">取消</el-button>
                <el-button type="primary" @click="onStopSubmit">提交</el-button>
              </span>
            </template>
          </el-dialog>
        </el-tab-pane>

        <el-tab-pane label="关键词" name="fourth">
          <el-card>
            <Table
              :data="keyTableData"
              :column="keyTableTitle"
              :operation="true"
              :page="keyPage"
              :loading="keyLoading"
              @onSizeChange="onSizeChangekey"
              @onCurrentChange="onCurrentChangekey"
            >
              <template #search>
                <div>
                  <el-button type="primary" @click="onkeyAdd">新增</el-button>
                </div>
              </template>
              <template #column="scope" style="width:100">
                <el-button @click="onkeyEdit(scope)" type="success">编辑</el-button>
                <el-button @click="onkeyDelete(scope)" type="warning">删除</el-button>
              </template>
            </Table>
          </el-card>
          <el-dialog :title="keyTitle" v-model="keyVisible" width="80%" :show-close="false">
            <el-form
              :model="keyForm"
              :rules="rules"
              ref="addRuleForm"
              label-position="left"
              label-width="100px"
            >
              <el-form-item label="内容" prop="内容">
                <el-input v-model="keyForm.value" placeholder="请输入内容" style="width:100%" />
              </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="keyVisible = false">取消</el-button>
                <el-button type="primary" @click="onkeySubmit">提交</el-button>
              </span>
            </template>
          </el-dialog>
        </el-tab-pane>

        <el-tab-pane label="参数设置" name="fifth">
          <el-card>
            <Table
              :data="param2TableData"
              :column="param2TableTitle"
              :operation="true"
              :page="param2Page"
              :loading="param2Loading"
              @onSizeChange="onSizeChangeparam2"
              @onCurrentChange="onCurrentChangeparam2"
            >
              <template #column="scope" style="width:100">
                <el-button @click="onparam2Edit(scope)" type="success">编辑</el-button>
              </template>
            </Table>
          </el-card>
          <el-dialog :title="param2Title" v-model="param2Visible" width="80%" :show-close="false">
            <el-form
              :model="param2Form"
              :rules="rules"
              ref="addRuleForm"
              label-position="left"
              label-width="100px"
            >
              <el-form-item label="参数值" prop="参数值">
                <el-input v-model="param2Form.value" placeholder="请输入参数值" style="width:100%" />
              </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="param2Visible = false">取消</el-button>
                <el-button type="primary" @click="onparam2Submit">提交</el-button>
              </span>
            </template>
          </el-dialog>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div style="width:40%;">
      <el-card>
        <div id="myPie" style="height:500px"></div>
        <div id="tlrealtimewordcloud" style="height:500px"></div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, nextTick, ref, unref, toRefs } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import Table from "../../components/Table.vue";
import {
  collectDataPage,
  exportTxt,
  exportSrt,
  getMyData,
  getMarksByDocId,
  updateNote,
  paramPage,
  createParam,
  updateParam,
  deleteParam
} from "../../api/systemApi.js";
import { timeFormate2 } from "../../utils/utils.js";
import * as echarts from "echarts";
import "echarts-wordcloud";

const addRuleForm = ref(null);
import { useRoute } from "vue-router";
const route = useRoute();
const state = reactive({
  searchForm: {
    docId: route.query.docId,
    mark:''
    // subtitle: '',
    // property: 'en',
    //  timestamp: ''
  },
  loading: false,
  page: {
    current: 1,
    size: 500,
    total: 1
  },
  tableTitle: [
    { prop: "id", label: "ID", width: 100 },
    { prop: "subtitle", label: "内容" },
    { prop: "timestamp", label: "创建时间", width: 160 },
    { prop: "mark", label: "标记", width: 200 }
  ],
  tableData: [],
  options: [],
  value1: [],
  taskVisible: false,
  taskTitle: "创建采集任务",
  markVisible: false,
  markTitle: "创建采集任务",
  taskForm: {
    domain: "",
    docId: "",
    reamrk: ""
  },
  activeName: "first",
  fqcTableTitle: [
    { prop: "name", label: "单词" },
    { prop: "value", label: "次数" },
    { prop: "wordLength", label: "词长" }
  ],
  fqcTableData: [],
  fqcLoading: false,
  fqcPage: {
    current: 1,
    size: 500,
    total: 1
  },
  stopTableTitle: [
    { prop: "id", label: "ID", width: 100 },
    { prop: "value", label: "内容" }
  ],
  stopTableData: [],
  stopLoading: false,
  stopVisible: false,
  stopPage: {
    current: 1,
    size: 500,
    total: 1
  },
  stopSearchForm: {
    docId: route.query.docId,
    type: 0
  },
  stopForm: {
    id: "",
    docId: "",
    value: "",
    type: 0
  },
  stopTitle: "",
  keyTableTitle: [
    { prop: "id", label: "ID", width: 100 },
    { prop: "value", label: "内容" }
  ],
  keyTableData: [],
  keyLoading: false,
  keyVisible: false,
  keyPage: {
    current: 1,
    size: 500,
    total: 1
  },
  keySearchForm: {
    docId: route.query.docId,
    type: 1
  },
  keyForm: {
    id: "",
    docId: "",
    value: "",
    type: 1
  },
  keyTitle: "",
  param2TableTitle: [
    { prop: "id", label: "ID", width: 100 },
    { prop: "name", label: "参数名" },
    { prop: "value", label: "参数值" }
  ],
  param2TableData: [],
  param2Loading: false,
  param2Visible: false,
  param2Page: {
    current: 1,
    size: 500,
    total: 1
  },
  param2SearchForm: {
    docId: route.query.docId,
    type: 2
  },
  param2Form: {
    id: "",
    docId: "",
    value: "",
    type: 2,
    name: ""
  },
  param2Title: ""
});

let myResult;
const {
  searchForm,
  loading,
  page,
  tableHeight,
  tableTitle,
  tableData,
  taskVisible,
  taskTitle,
  markVisible,
  markTitle,
  taskForm,
  options,
  value1,
  fqcTableTitle,
  fqcTableData,
  fqcLoading,
  fqcPage,
  stopTableTitle,
  stopTableData,
  stopPage,
  stopLoading,
  stopSearchForm,
  stopVisible,
  stopTitle,
  stopForm,
  keyTableTitle,
  keyTableData,
  keyPage,
  keyLoading,
  keySearchForm,
  keyVisible,
  keyTitle,
  keyForm,
  param2TableTitle,
  param2TableData,
  param2Page,
  param2Loading,
  param2SearchForm,
  param2Visible,
  param2Title,
  param2Form
} = toRefs(state);

onMounted(() => {
  getMarksByDocIdInit();
  onCollectPage();
  onStopPage();
  onKeyPage();
  onparam2Page();
  // noColumnar();
  // onPie();
  // onLtrealtimewordcloud();
  // onFqcPage();
});

// 编辑
const onEdit = val => {
  state.taskTitle = "编辑";
  state.taskForm = {
    id: val.column.row.id,
    subtitle: val.column.row.subtitle
  };
  state.taskVisible = true;
};

// // 初始化数据
// const handleClick = () => {
//   console.log(tab, event);
// };

// 初始化数据
const onCollectPage = () => {
  state.loading = true;
  // state.searchForm.docId=this.$route.params.id;
  // state.searchForm.docId=this.docId;
  collectDataPage(Object.assign(state.page, state.searchForm)).then(res => {
    state.loading = false;
    state.tableData = res.result.records;
    state.page.current = res.result.current;
    state.page.size = res.result.size;
    state.page.total = res.result.total;
  });
    onPie();
  onLtrealtimewordcloud();
};
// 初始化数据
const getMarksByDocIdInit = () => {
  getMarksByDocId(Object.assign(state.searchForm)).then(res => {
    state.options = res.result;
  });
};

// 初始化数据
const onFqcPage = () => {
  state.fqcLoading = true;
  // state.searchForm.docId=this.$route.params.id;
  // state.searchForm.docId=this.docId;
  // fqcDataPage(Object.assign(state.page, state.searchForm)).then(res => {
  state.fqcLoading = false;
  state.fqcTableData = myResult;
  state.fqcPage.total = myResult.length;
  // state.page.current = res.result.current;
  // state.page.size = res.result.size;
  // state.page.total = myResult.size;
  // });
};
// 初始化数据
const onExportTxt = () => {
  state.loading = true;
  // state.searchForm.docId=this.$route.params.docId;
  // state.searchForm.docId=this.docId;
  exportTxt(Object.assign(state.searchForm)).then(res => {
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
    onCollectPage();
  });
};

// 初始化数据
const onExportSrt = () => {
  state.loading = true;
  // state.searchForm.docId=this.$route.params.docId;
  // state.searchForm.docId=this.docId;
  exportSrt(Object.assign(state.searchForm)).then(res => {
    if (!res.data) {
      return;
    }
    let url = window.URL.createObjectURL(new Blob([res.data]));
    let link = document.createElement("a");
    link.style.display = "none";
    link.href = url;
    let fileName = "data_" + timeFormate2(new Date()) + ".srt";
    link.setAttribute("download", fileName);
    document.body.appendChild(link);
    link.click();
    // 释放URL对象所占资源
    window.URL.revokeObjectURL(url);
    // 用完即删
    document.body.removeChild(link);
    onCollectPage();
  });
};

const onSizeChange = e => {
  state.page.size = e;
  onCollectPage();
};
const onCurrentChange = e => {
  state.page.current = e;
  onCollectPage();
};
// --------------------------------stop start-----------------------------------
// 添加
const onStopAdd = () => {
  state.stopTitle = "创建";
  state.stopForm = {
    docId: route.query.docId,
    value: ""
  };

  state.stopVisible = true;
};

// 编辑
const onStopEdit = val => {
  state.stopTitle = "编辑";
  state.stopForm = {
    id: val.column.row.id,
    value: val.column.row.value
  };
  state.stopVisible = true;
};

// 删除
const onStopDelete = val => {
  ElMessageBox.confirm("此操作将永久删除, 是否继续?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  })
    .then(() => {
      let param = {
        id: val.column.row.id
      };
      deleteParam(param).then(res => {
        if (res) {
          ElMessage.success("删除成功!");
          onStopPage();
          onPie();
          onLtrealtimewordcloud();
        }
      });
    })
    .catch(e => {
      console.log(e);
    });
};

// 初始化数据
const onStopPage = () => {
  state.stopLoading = true;
  paramPage(Object.assign(state.stopPage, state.stopSearchForm)).then(res => {
    state.stopLoading = false;
    state.stopTableData = res.result.records;
    state.stopPage.current = res.result.current;
    state.stopPage.size = res.result.size;
    state.stopPage.total = res.result.total;
  });
};

const onSizeChangeStop = e => {
  state.stopPage.size = e;
  onStopPage();
};
const onCurrentChangeStop = e => {
  state.stopPage.current = e;
  onStopPage();
};

const onStopSubmit = async () => {
  // const form = unref(addRuleForm);
  // if (!form) return;
  // await form.validate();
  if (state.stopForm.id == null || state.stopForm.id == "") {
    // 添加
    createParam(state.stopForm).then(res => {
      if (res) {
        ElMessage.success("添加成功!");
      }
      onStopPage();
      onPie();
      onLtrealtimewordcloud();
    });
  } else {
    // 修改
    updateParam(state.stopForm).then(res => {
      if (res) {
        ElMessage.success("修改成功!");
      }
      onStopPage();
      onPie();
      onLtrealtimewordcloud();
    });
  }
  state.stopVisible = false;
};
// --------------------------------stop end-----------------------------------

// --------------------------------key start-----------------------------------
// 添加
const onkeyAdd = () => {
  state.keyTitle = "创建";
  state.keyForm = {
    docId: route.query.docId,
    value: "",
    type: 1
  };

  state.keyVisible = true;
};

// 编辑
const onkeyEdit = val => {
  state.keyTitle = "编辑";
  state.keyForm = {
    id: val.column.row.id,
    value: val.column.row.value
  };
  state.keyVisible = true;
};

// 删除
const onkeyDelete = val => {
  ElMessageBox.confirm("此操作将永久删除, 是否继续?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  })
    .then(() => {
      let param = {
        id: val.column.row.id
      };
      deleteParam(param).then(res => {
        if (res) {
          ElMessage.success("删除成功!");
          onKeyPage();
          onPie();
          onLtrealtimewordcloud();
        }
      });
    })
    .catch(e => {
      console.log(e);
    });
};

// 初始化数据
const onKeyPage = () => {
  state.keyLoading = true;
  paramPage(Object.assign(state.keyPage, state.keySearchForm)).then(res => {
    state.keyLoading = false;
    state.keyTableData = res.result.records;
    state.keyPage.current = res.result.current;
    state.keyPage.size = res.result.size;
    state.keyPage.total = res.result.total;
  });
};

const onSizeChangekey = e => {
  state.keyPage.size = e;
  onKeyPage();
};
const onCurrentChangekey = e => {
  state.keyPage.current = e;
  onKeyPage();
};

const onkeySubmit = async () => {
  // const form = unref(addRuleForm);
  // if (!form) return;
  // await form.validate();
  if (state.keyForm.id == null || state.keyForm.id == "") {
    // 添加
    createParam(state.keyForm).then(res => {
      if (res) {
        ElMessage.success("添加成功!");
      }
      onKeyPage();
      onPie();
      onLtrealtimewordcloud();
    });
  } else {
    // 修改
    updateParam(state.keyForm).then(res => {
      if (res) {
        ElMessage.success("修改成功!");
      }
      onKeyPage();
      onPie();
      onLtrealtimewordcloud();
    });
  }
  state.keyVisible = false;
};
// --------------------------------key end-----------------------------------

// --------------------------------param2 start-----------------------------------
// 添加
const onparam2Add = () => {
  state.param2Title = "创建";
  state.param2Form = {
    docId: route.query.docId,
    value: "",
    type: 2,
    name: ""
  };

  state.param2Visible = true;
};

// 编辑
const onparam2Edit = val => {
  state.param2Title = "编辑";
  state.param2Form = {
    id: val.column.row.id,
    value: val.column.row.value
  };
  state.param2Visible = true;
};
// 标记
const onMark = val => {
  state.markTitle = "标记";
  state.taskForm = {
    id: val.column.row.id,
    mark: val.column.row.mark
  };
  state.markVisible = true;
};

// // 删除
// const onparam2Delete = val => {
//   ElMessageBox.confirm("此操作将永久删除, 是否继续?", "提示", {
//     confirmButtonText: "确定",
//     cancelButtonText: "取消",
//     type: "warning"
//   })
//     .then(() => {
//       let param2 = {
//         id: val.column.row.id
//       };
//       deleteparam2(param2).then(res => {
//         if (res) {
//           ElMessage.success("删除成功!");
//           onparam2Page();
//         }
//       });
//     })
//     .catch(e => {
//       console.log(e);
//     });
// };

// 初始化数据
const onparam2Page = () => {
  state.param2Loading = true;
  paramPage(Object.assign(state.param2Page, state.param2SearchForm)).then(
    res => {
      state.param2Loading = false;
      state.param2TableData = res.result.records;
      state.param2Page.current = res.result.current;
      state.param2Page.size = res.result.size;
      state.param2Page.total = res.result.total;
    }
  );
};

const onSizeChangeparam2 = e => {
  state.param2Page.size = e;
  onparam2Page();
};
const onCurrentChangeparam2 = e => {
  state.param2Page.current = e;
  onparam2Page();
};

const onparam2Submit = async () => {
  // const form = unref(addRuleForm);
  // if (!form) return;
  // await form.validate();
  if (state.param2Form.id == null || state.param2Form.id == "") {
    // 添加
    // createparam2(state.param2Form).then(res => {
    //   if (res) {
    //     ElMessage.success("添加成功!");
    //   }
    //   onparam2Page();
    //   onPie();
    //   onLtrealtimewordcloud();
    // });
  } else {
    // 修改
    updateParam(state.param2Form).then(res => {
      if (res) {
        ElMessage.success("修改成功!");
      }
      onparam2Page();
      onPie();
      onLtrealtimewordcloud();
    });
  }
  state.param2Visible = false;
};
// --------------------------------param2 end-----------------------------------
const onRefresh = () => {
  state.searchForm = {
    name: null
  };
  onCollectPage();
};
const onSearch = () => {
  onCollectPage();
};

const goBack = () => {
  router.go(-1);
};

const onSubmit = async () => {
  const form = unref(addRuleForm);
  if (!form) return;
  await form.validate();
  if (state.taskForm.id == null || state.taskForm.id == "") {
    // 添加
    // collect(state.taskForm).then(res => {
    //   if (res) {
    //     ElMessage.success("添加成功!");
    //   }
    //    onCollectPage();
    //     onPie();
    //      onLtrealtimewordcloud();
    // });
  } else {
    // 修改
    updateNote(state.taskForm).then(res => {
      if (res) {
        ElMessage.success("修改成功!");
      }
      onCollectPage();
      getMarksByDocIdInit()
      // onPie();
      // onLtrealtimewordcloud();
    });
  }
  state.taskVisible = false;
  state.markVisible = false;
};

const onPie = async () => {
  let myPie = echarts.init(document.getElementById("myPie"));
    state.searchForm.mark=state.value1.join(",")
  const { result } = await getMyData(Object.assign(state.searchForm));
  myResult = result;
  onFqcPage();
  // exportTxt(Object.assign(state.searchForm)).then(res => {

  // console.log(result)

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
  state.searchForm.mark=state.value1.join(",")
  const { result } = await getMyData(Object.assign(state.searchForm));
  // console.log(result)
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