package com.tansci.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Lists;
import com.google.common.collect.Maps;
import com.tansci.common.WrapMapper;
import com.tansci.common.Wrapper;
import com.tansci.common.annotation.Log;
import com.tansci.common.constant.Constants;
import com.tansci.common.task.RecordTask;
import com.tansci.domain.system.Record;
import com.tansci.domain.system.RecordData;
import com.tansci.enums.CollectTypeEnum;
import com.tansci.service.system.RecordDataService;
import com.tansci.service.system.RecordService;
import com.tansci.utils.ExportUtil;
import com.tansci.utils.HttpClientUtil;
import com.tansci.utils.ResourcesUtil;
import com.tansci.utils.SecurityUserUtils;
import com.tansci.utils.SystemUtil;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName： SysMenuController.java
 * @ClassPath： com.tansci.controller.SysMenuController.java
 * @Description： 菜单
 * @Author： tanyp
 * @Date： 2021/7/20 17:08
 **/
@Slf4j
@Controller
@RequestMapping("/collect")
@Api(value = "collect", tags = "采集")
public class CollectController {

  @Autowired
  private RecordDataService recordDataService;
  @Autowired
  private RecordService recordService;


  @Autowired
  private RecordTask recordTask;
  private Map<String, String> cacheMap = Maps.newHashMap();

  @ResponseBody
  @PostMapping("/getAzureToken")
  public String getAzureToken() {
    String region = "eastasia";
    //    F0
    //    String subscriptionKey = "de4e9ac5168843c2b74a9b1477376668";
    //    S0
    String subscriptionKey = "65e6b6a78c5d4326a57366de1c51c872";

    String tokenKey = "token";
    //    if (!cacheMap.containsKey(tokenKey)) {
    //    发送请求
    String url = "https://" + region + ".api.cognitive.microsoft.com/sts/v1.0/issueToken";
    Map<String, String> headerParams = Maps.newHashMap();
    headerParams.put("Content-Type", "application/json");
    headerParams.put("Ocp-Apim-Subscription-Key", subscriptionKey);
    Map<String, String> bodyParams = Maps.newHashMap();
    String result = HttpClientUtil.sendPostRequest(url, headerParams, bodyParams);
    cacheMap.put(tokenKey, result);
    log.info("==================getAzureToken={}", result);
    //    }
    return cacheMap.get(tokenKey);
  }

  @ResponseBody
  @PostMapping("/clearAzureToken")
  public String clearAzureToken() {
    String tokenKey = "token";
    cacheMap.remove(tokenKey);
    log.info("==================clearAzureToken=");
    //    }
    return cacheMap.get(tokenKey);
  }

  //  @ApiOperation(value = "采集列表", notes = "采集列表")
  //  @Log(modul = "采集-采集列表", type = Constants.SELECT, desc = "采集列表")
  //  @ResponseBody
  //  @GetMapping("/page")
  //  public Wrapper<IPage<Record>> page(Page page, Record record) {
  //    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, recordService.page(page, record));
  //  }
  @ApiOperation(value = "采集列表", notes = "采集列表")
  @Log(modul = "采集-采集列表", type = Constants.SELECT, desc = "采集列表")
  @ResponseBody
  @GetMapping("/page")
  public Wrapper<IPage<Record>> page(Page page, Record record) {
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, recordService.page(page, record));
  }

  @ApiOperation(value = "记录数据列表", notes = "记录数据列表")
  @Log(modul = "采集-记录数据列表", type = Constants.SELECT, desc = "记录数据列表")
  @ResponseBody
  @GetMapping("/dataPage")
  public Wrapper<IPage<RecordData>> dataPage(Page page, RecordData recordData) {
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, recordDataService.page(page, recordData));
  }

  @ApiOperation(value = "导出列表", notes = "导出列表")
  @Log(modul = "采集-导出列表", type = Constants.SELECT, desc = "导出列表")
  @GetMapping("/exportTxt")
  public void exportTxt(RecordData recordData, HttpServletResponse response) {
    List<RecordData> recordDataList = recordDataService.selectList(recordData);
    ExportUtil.exportSrt(response, recordDataList);
  }

  @ApiOperation(value = "导出音频文件", notes = "导出音频文件")
  @Log(modul = "导出音频文件", type = Constants.SELECT, desc = "导出音频文件")
  @GetMapping("/exportWAV")
  public void exportWAV(Record record, HttpServletResponse response) {
    Record record1 = recordService.selectOne(record);
    if (StringUtils.isNotBlank(record1.getFilePath())) {
      ExportUtil.exportWav(response, record1);
    }
  }

  @ApiOperation(value = "列表", notes = "采集列表")
  @Log(modul = "采集-采集列表", type = Constants.SELECT, desc = "采集列表")
  @GetMapping("/record")
  @ResponseBody
  public Wrapper<Boolean> record(@RequestParam(name = "docId", required = true) String docId,
      @RequestParam(name = "domain", required = false) String domain,
      @RequestParam(name = "remark", required = false) String remark) {
    //        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysMenuService.list(null));
    log.info("export===========,docId={},domain={}", docId, domain);
    //   新增或更新采集记录
    Record record = new Record();
    record.setDocId(docId);
    record.setUserId(SecurityUserUtils.getUser().getId());
    record.setRemark(remark);

    if (Objects.isNull(recordService.selectOne(record))) {
      record.setCreateTime(new Date());
      record.setUpdateTime(new Date());
      record.setType(CollectTypeEnum.COLLECT_TYPE_MICROSOFT_PPT.getType());
      recordService.save(record);
    } else {
      record.setUpdateTime(new Date());
      recordService.update(record);
    }

    //    添加采集数据到数据库中 netstat -ano|findstr "8005" tasklist|findstr "20516" taskkill /f /t /im java.exe
    //    recordTask.singleTask(docId, domain);
    recordTask.addTask(docId, domain);
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);

    //    return "success";
    //    Map<String, String> exportMap = new HashMap();
    //    LanguageEnum[] var6 = LanguageEnum.values();
    //    int var7 = var6.length;
    //
    //    for (int var8 = 0; var8 < var7; ++var8) {
    //      LanguageEnum value = var6[var8];
    //      String language = value.getLanguage();
    //      String key = docId + "_" + language;
    //      if (RecordTask.dbMap.containsKey(key)) {
    //            exportMap.put(key, (String) RecordTask.dbMap.get(key));
    //      }
    //    }
    //
    //    try {
    //      Thread.sleep(1000L);
    //    } catch (InterruptedException e) {
    //      e.printStackTrace();
    //    }
    //    if (!exportMap.isEmpty()) {
    //          ExportUtil.exportZip(httpServletResponse, exportMap);
    //      log.info("export===========success,docId={},time={}", docId, System.currentTimeMillis());
    //    }
  }

  @GetMapping("/createNote")
  @ResponseBody
  public Wrapper<Boolean> createNote(@RequestParam(name = "docId", required = true) String docId,
      @RequestParam(name = "remark", required = false) String remark) {
    log.info("export===========,docId={},remark={}", docId, remark);
    //   新增或更新采集记录
    Record record = new Record();
    record.setDocId(docId);
    record.setUserId(SecurityUserUtils.getUser().getId());
    record.setRemark(remark);
    if (Objects.isNull(recordService.selectOne(record))) {
      record.setType(CollectTypeEnum.COLLECT_TYPE_AZURE.getType());
      record.setCreateTime(new Date());
      record.setUpdateTime(new Date());
      recordService.save(record);
    }else {
      record.setUpdateTime(new Date());
      recordService.update(record);
    }
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
  }

  @ApiOperation(value = "列表", notes = "采集列表")
  @Log(modul = "采集-采集列表", type = Constants.SELECT, desc = "采集列表")
  @GetMapping("/sendNote")
  @ResponseBody
  public Wrapper<Boolean> sendNote(@RequestParam(name = "resultText", required = true) String resultText,
      @RequestParam(name = "docId", required = true) String docId,
      @RequestParam(name = "remark", required = false) String remark,
      @RequestParam(name = "offset", required = true) String offset,
      @RequestParam(name = "duration", required = true) String duration,
      @RequestParam(name = "language", required = true) String language) {
    //        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysMenuService.list(null));
    log.info("export===========,resultText={}", resultText);
    if (StringUtils.isBlank(resultText)) {
      //      如果数据为空，则不处理，直接返回
      return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
    }
    //   新增或更新采集记录
    //    Record record = new Record();
    //    //    String docId = UUID.randomUUID().toString().replace("-", "");
    //    record.setDocId(docId);
    //    record.setUserId(SecurityUserUtils.getUser().getId());
    //    record.setRemark(remark);
    //    if (Objects.isNull(recordService.selectOne(record))) {
    //
    //      record.setType(CollectTypeEnum.COLLECT_TYPE_AZURE.getType());
    //      record.setCreateTime(new Date());
    //      record.setUpdateTime(new Date());
    //      recordService.save(record);
    //    }
    //    else {
    //      record.setUpdateTime(new Date());
    //      recordService.update(record);
    //    }
    //    添加内容到数据库
    List<RecordData> recordDataList = Lists.newArrayList();
    RecordData recordData = new RecordData();
    recordData.setDocId(docId);
    recordData.setProperty(language);
    recordData.setSubtitle(resultText);
    recordData.setTimestamp(new Date());
    recordData.setOffset(offset);
    recordData.setDuration(duration);
    recordDataList.add(recordData);
    //           入库
    if (!recordDataList.isEmpty()) {
      recordDataService.saveBatch(recordDataList);
    }
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
  }

  @PostMapping("/deleteNote")
  @ResponseBody
  public Wrapper<Boolean> deleteNote(@RequestBody Record record) {
    log.info("deleteNote===========,resultText={}");
    if (StringUtils.isBlank(record.getDocId())) {
      //      如果数据为空，则不处理，直接返回
      return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
    }
    Record record1 = recordService.selectOne(record);
    //    删除文件
    String filePath = record1.getFilePath();
    if (StringUtils.isNotBlank(filePath)) {
      File file = new File(filePath);
      if (file.exists()) {
        file.delete();
      }
    }

    recordService.del(record1);
    return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
  }

  @GetMapping("/getMyData")
  @ResponseBody
  public Wrapper<List<MyData>> getMyData(RecordData recordData) {
    //        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, sysMenuService.list(null));
    log.info("getMyData======================================");
    //   新增或更新采集记录
    List<MyData> myDataList = Lists.newArrayList();
    List<RecordData> recordDataList = recordDataService.selectList(recordData);
    JSONArray recordDataJsonArray = JSONArray.parseArray(JSON.toJSONString(recordDataList));
    //    System.out.println(JSON.toJSONString(recordDataList));
    //    System.out.println("=========================");
    //    System.out.println(recordDataJsonArray.toJSONString());
    //    JSON.parseArray()
    //    String str = "[{\"docId\":\"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2\",\"subtitle\":\"Hello。 H22ello22。我爱你，时间\",\"property\":\"en\",\"userId\":\"bc3ac26e69731b617eb80274453f6dba\",\"timestamp\":\"2022-09-23 15:57:15\"},{\"docId\":\"rdvPragueDocId_ea41fe07-20b1-403e-afd8-183449a24bb0_r2\",\"subtitle\":\"Create an acquisition task.\",\"property\":\"en\",\"userId\":\"bc3ac26e69731b617eb80274453f6dba\",\"timestamp\":\"2022-09-23 15:57:27\"}]";
    //    PyList pyList = new PyList();
    //    JSONArray jsonArray = JSONArray.parseArray(str);
    //    System.out.println(jsonArray.toJSONString());
    Process proc;
    String result = null;
    String filePath = null;
    try {
        	/*
			附加：
			首先一定要设置好你所使用的python的位置，切记不要直接使用python，因为系统会默认使用自带的python，所以一定要设置好你所使用的python的位置，否则可能会出现意想不到的问题（比如说我使用的是anaconda中的python，而ubuntu系统会默认调用自带的python，而我自带的python中并没有numpy库，所以会造成相应的代码不会执行的问题，所以设置好python的位置是很重要的）。还有就是要设置好py文件的位置，使用绝对路径。在这里插入代码片
       还有就是可以看出，此方法可以满足我们python代码中调用第三方库的情况，简单实用。
       "E:\\tansci\\src\\main\\java\\com\\tansci\\controller/test_def.py"
			*/
      //      String jsonStr=jsonArray.toJSONString();
      String jsonStr = JSON.toJSONString(recordDataList);
      //      jsonStr = jsonStr.replace("\"", "\'");
      //      "C:\\Python310/python.exe"
      log.info("===============os.name={}", SystemUtil.getOsName());
      String fileName =
          recordData.getDocId() + "_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 100) + ".txt";

      //      jsonStr写入文件，由py文件读取
      String[] args1;
      if (SystemUtil.isWindows()) {
        filePath = "E:\\tansci\\py/" + fileName;
        FileUtils.write(new File(filePath), jsonStr, "utf-8");
        args1 = new String[] { "python", "E:\\tansci\\py/analyseRecord_windows.py", "--filePath=" + filePath };
      } else {
        filePath = "/temp/" + fileName;
        FileUtils.write(new File(filePath), jsonStr, "utf-8");
        args1 = new String[] { "python", "/analyseRecord_linux.py", "--filePath=" + filePath };
      }

      proc = Runtime.getRuntime().exec(args1);
      BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        //        {"hello": 1, "h": 1, "ello": 1, "create": 1, "an": 1, "acquisition": 1, "task": 1}
        System.out.println(line);
        result = line;
      }

      JSONObject jsonInfo = JSON.parseObject(result);
      Iterator iter = jsonInfo.entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry entry = (Map.Entry) iter.next();
        myDataList.add(new MyData(entry.getKey().toString(), Integer.parseInt(entry.getValue().toString())));
      }

      myDataList = myDataList.stream().sorted((e1, e2) -> {
        return Integer.compare(e2.getValue(), e1.getValue());
      }).collect(Collectors.toList()).subList(0,30);
      in.close();
      proc.waitFor();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      //      删除临时文件
      if (StringUtils.isNotBlank(filePath)) {
        FileUtils.deleteQuietly(new File(filePath));
      }
      return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, myDataList);
    }
  }

  @ResponseBody
  @PostMapping("/upload")
  public String upload(@RequestParam(name = "file") MultipartFile file, String fileName, String docId) {

    log.info("==================upload=fileName={},docId={}", fileName, docId);
    //    file.getBytes()
    Path path = Paths.get("").toAbsolutePath().resolve("tempAudio");
    String parentPath = path.toAbsolutePath().toString() + File.separator;
    log.info("====================parentPath={}", parentPath);
    //    new File(parentPath).mkdirs();
    //    FileOutputStream fileOutputStream = null;
    InputStream inputStream = null;
    String filePath = parentPath + fileName;
    try {
      inputStream = file.getInputStream();
      //      fileOutputStream = new FileOutputStream(new File(parentPath + fileName));
      FileUtils.copyInputStreamToFile(inputStream, new File(filePath));
      //      IoUtil.copy(new ByteArrayInputStream(tempStream.toByteArray()), fileOutputStream);
      //   入库，保存地址
      Record record = new Record();
      record.setDocId(docId);
      //      record.setUserId(SecurityUserUtils.getUser().getId());
      //      record.setRemark(remark);
      Record record1 = recordService.selectOne(record);
      //        record.setType(CollectTypeEnum.COLLECT_TYPE_AZURE.getType());
      //        record.setCreateTime(new Date());
      record1.setUpdateTime(new Date());
      record1.setFilePath(filePath);
      recordService.update(record1);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      ResourcesUtil.multiClose(inputStream);
    }
    return "upload success";
  }

  @Data
  class MyData implements Serializable {
    private static final long serialVersionUID = 6686838756389384302L;
    private String name;
    private Integer value;

    public MyData(String name, Integer value) {
      this.name = name;
      this.value = value;
    }
  }

}
